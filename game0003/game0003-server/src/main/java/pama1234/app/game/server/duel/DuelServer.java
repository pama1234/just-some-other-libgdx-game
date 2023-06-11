package pama1234.app.game.server.duel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import pama1234.app.game.server.duel.util.ai.neat.ServerFisheyeVision;
import pama1234.app.game.server.duel.util.input.ServerInputData;
import pama1234.util.UtilServer;
import pama1234.util.localization.Localization;
import pama1234.util.protobuf.PointUpdateProto.PointUpdate;
import pama1234.util.protobuf.PointUpdateProto.PointUpdateList;

public class DuelServer extends UtilServer{
  public static String loadString(File path) {
    try {
      InputStream inputStream=new FileInputStream(path);
      String text=new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8))
        .lines()
        .collect(Collectors.joining("\n"));
      inputStream.close();
      return text;
    }catch(IOException e) {
      e.printStackTrace();
    }
    return null;
  }
  public static final Localization localization=new Localization();
  public File mainDir=new File(System.getProperty("user.dir")+"/data/server/duel");
  public File configFile;
  public ServerConfigData loadConfig() {
    configFile=new File(mainDir,"config.yaml");
    if(configFile.exists()) {
      return localization.yaml.loadAs(loadString(configFile),ServerConfigData.class);
    }else {
      mainDir.mkdirs();
      var out=new ServerConfigData();
      out.init();
      return out;
    }
  }
  public PointUpdate.Builder pointUpdateBuilder;
  public PointUpdateList.Builder pointUpdateListBuilder;
  public boolean paused;
  public ServerInputData input_a,input_b;
  public ServerConfigData config;
  public ServerGameSystem system;
  public ServerFisheyeVision player_a,player_b;
  public int timeLimitConst=60*10;
  public int time,timeLimit=timeLimitConst;
  @Override
  public void init() {
    config=loadConfig();
    input_a=new ServerInputData();
    newGame(true);
  }
  @Override
  public void update() {
    if(!paused) {
      // InputDataProto.InputData.Builder inputBuilder=InputDataProto.InputData.newBuilder();
      //---
      system.update();
      //---
      if(config.mode==ServerConfigData.neat) {
        if(system.stateIndex==ServerGameSystem.play) {
          time++;
          system.myGroup.player.engine.setScore(1,system.currentState.getScore(system.myGroup.id));
          system.otherGroup.player.engine.setScore(1,system.currentState.getScore(system.otherGroup.id));
          if(time>timeLimit) {
            timeLimit=timeLimitConst;
            newGame(true);
          }
        }
        player_a.update(system.myGroup.player);
        player_b.update(system.otherGroup.player);
      }
    }
  }
  @Override
  public void dispose() {}
  public void newGame(boolean demo) {
    system=new ServerGameSystem(this,demo,true);
  }
  public void stateChangeEvent(ServerGameSystem system,int stateIndex) {
    if(system.stateIndex==ServerGameSystem.play) time=0;
    else if(system.stateIndex==ServerGameSystem.result) {
      system.myGroup.player.engine.setScore(0,system.currentState.getScore(system.myGroup.id));
      system.otherGroup.player.engine.setScore(0,system.currentState.getScore(system.otherGroup.id));
    }
  }
}
