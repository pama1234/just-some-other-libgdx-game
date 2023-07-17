package pama1234.app.game.server.duel;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import pama1234.app.game.server.duel.NetUtil.GameServer;
import pama1234.app.game.server.duel.util.actor.ActorGroup;
import pama1234.app.game.server.duel.util.ai.neat.ServerFisheyeVision;
import pama1234.app.game.server.duel.util.input.ServerInputOutput;
import pama1234.util.UtilServer;
import pama1234.util.localization.Localization;
import pama1234.util.net.SocketData;
import pama1234.util.net.SocketWrapper;
import pama1234.util.protobuf.InputDataProto;
import pama1234.util.protobuf.InputDataProto.InputData;
import pama1234.util.protobuf.OutputDataProto;
import pama1234.util.protobuf.OutputDataProto.GroupData;
import pama1234.util.protobuf.OutputDataProto.OutputData;
import pama1234.util.protobuf.OutputDataProto.OutputDataElement;
import pama1234.util.wrapper.Center;

public class DuelServer extends UtilServer{
  public static final Localization localization=new Localization();
  public File mainDir=new File(System.getProperty("user.dir")+"/data/server/duel");
  public File configFile;
  //---
  public GameServer server;
  public Thread acceptThread;
  //---
  public OutputData.Builder outputDataBuilder;
  public GroupData.Builder groupBuilder;
  public OutputDataElement.Builder elementBuilder;
  public InputData.Builder inputDataBuilder;
  //---
  public boolean paused;
  public Center<ServerInputOutput> inputCenter;
  // @Deprecated
  public ServerInputOutput input_a,input_b;
  public ServerConfigData config;
  public ServerGameSystem core;
  public ServerFisheyeVision player_a,player_b;
  public int timeLimitConst=60*10;
  public int time,timeLimit=timeLimitConst;
  @Override
  public void init() {
    config=loadConfig();
    //---
    server=new GameServer(config.server);
    System.out.println("open server on "+config.server);
    acceptThread=new Thread(()-> {
      while(!stop) {
        SocketData socketData;
        try {
          socketData=new SocketData(new SocketWrapper(server.socketData.accept()));
          System.out.println("AcceptSocket.accept "+socketData.s.getRemoteAddress());
          if(input_a.socket==null) {
            input_a.socket=socketData;
          }else if(input_b.socket==null) {
            input_b.socket=socketData;
          }else {
            socketData.dispose();
          }
        }catch(SocketException ex) {
          ex.printStackTrace();
          stop=true;
        }catch(IOException ex) {
          ex.printStackTrace();
          stop=true;
        }
      }
    },"AcceptSocket");
    acceptThread.start();
    outputDataBuilder=OutputDataProto.OutputData.newBuilder();
    groupBuilder=OutputDataProto.GroupData.newBuilder();
    elementBuilder=OutputDataProto.OutputDataElement.newBuilder();
    inputDataBuilder=InputDataProto.InputData.newBuilder();
    //---
    inputCenter=new Center<>();
    input_a=new ServerInputOutput(inputDataBuilder);
    input_b=new ServerInputOutput(inputDataBuilder);
    inputCenter.add.add(input_a);
    inputCenter.add.add(input_b);
    //---
    newGame(true);
  }
  @Override
  public void update() {
    if(!paused) {
      inputCenter.refresh();
      for(var i:inputCenter.list) {
        if(i.socket!=null) {
          try {
            i.protoInput=InputData.parseDelimitedFrom(i.socket.i);
            i.inputData.copyFromProto(i.protoInput);
            // i.protoInput.writeDelimitedTo(i.socket.o);
            //---
            outputDataBuilder.clear();
            groupBuilder.clear();
            ActorGroup a=core.myGroup();
            ActorGroup b=core.otherGroup();
            copyGroupToProto(groupBuilder,a);
            outputDataBuilder.addElements(0,groupBuilder.build());
            groupBuilder.clear();
            copyGroupToProto(groupBuilder,b);
            outputDataBuilder.addElements(1,groupBuilder.build());
            groupBuilder.clear();
            // groupBuilder.addPlayer();
            i.protoOutput=outputDataBuilder.build();
            i.protoOutput.writeDelimitedTo(i.socket.o);
          }catch(IOException e) {
            // e.printStackTrace();
            System.out.println(e+" with "+i.socket.s.getRemoteAddress());
            i.socket.dispose();
            i.socket=null;
          }
        }
      }
      //---
      core.update();
      if(config.mode==ServerConfigData.neat) neatUpdate();
    }
    System.out.flush();
  }
  public void copyGroupToProto(GroupData.Builder proto,ActorGroup group) {
    for(var e:group.arrowCenter.list) {
      elementBuilder.clear();
      e.copyToProto(elementBuilder);
      if(e.isLethal()) proto.addLongArrow(elementBuilder.build());
      else proto.addShortArrow(elementBuilder.build());
    }
    elementBuilder.clear();
    if(group.playerCenter.list.size()>0) {
      group.playerCenter.list.getFirst().copyToProto(elementBuilder);
      proto.addPlayer(0,elementBuilder.build());
    }
  }
  public void neatUpdate() {
    if(core.stateIndex==ServerGameSystem.play) {
      time++;
      core.myGroup().playerCenter.list.getFirst().engine.setScore(1,core.currentState.getScore(core.myGroup().id));
      core.otherGroup().playerCenter.list.getFirst().engine.setScore(1,core.currentState.getScore(core.otherGroup().id));
      if(time>timeLimit) {
        timeLimit=timeLimitConst;
        newGame(true);
      }
    }
    player_a.update(core.myGroup().playerCenter.list.getFirst());
    player_b.update(core.otherGroup().playerCenter.list.getFirst());
  }
  @Override
  public void dispose() {}
  public void newGame(boolean demo) {
    core=new ServerGameSystem(this,demo,true,1);
  }
  public void stateChangeEvent(ServerGameSystem system,int stateIndex) {
    if(system.stateIndex==ServerGameSystem.play) time=0;
    else if(system.stateIndex==ServerGameSystem.result) {
      system.myGroup().playerCenter.list.getFirst().engine.setScore(0,system.currentState.getScore(system.myGroup().id));
      system.otherGroup().playerCenter.list.getFirst().engine.setScore(0,system.currentState.getScore(system.otherGroup().id));
    }
  }
  public void inGameStateChangeEvent(ServerGameSystem system,int stateIndex) {
    if(system.stateIndex==ServerGameSystem.play) {
      // if(config.mode==neat) neatE.time=0;
    }else if(system.stateIndex==ServerGameSystem.result) {
      for(var group:system.groupCenter.list) {
        for(var i:group.playerCenter.list) i.engine.setScore(0,system.currentState.getScore(group.id));
      }
    }
  }
  public ServerConfigData loadConfig() {
    configFile=new File(mainDir,"config.yaml");
    if(configFile.exists()) {
      return localization.yaml.loadAs(loadString(configFile),ServerConfigData.class);
    }else {
      mainDir.mkdirs();
      var out=new ServerConfigData();
      out.init();
      saveConfig(out);
      return out;
    }
  }
  public void saveConfig(ServerConfigData config) {
    saveString(configFile,localization.yaml.dumpAsMap(config));
  }
}
