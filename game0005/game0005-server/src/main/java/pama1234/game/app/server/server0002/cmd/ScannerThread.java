package pama1234.game.app.server.server0002.cmd;

import java.util.HashMap;
import java.util.Scanner;

import pama1234.game.app.server.server0002.Server0002;

public class ScannerThread extends Thread{
  public Server0002 p;
  public Scanner scanner;
  public CommandList commandList;
  public ScannerThread(Server0002 p) {
    super("ScannerThread");
    this.p=p;
    scanner=new Scanner(System.in);
    // commandList=new CommandList(null,(p1,p2,d,pt,s)-> {});
    commandList=CommandGenerator.getCommand_0001(p);
  }
  @Override
  public void run() {
    System.out.println("test of pama1234 v0.1 server");
    while(!p.stop) {
      String in=scanner.nextLine();
      String[] data=in.replaceAll("^\"","").split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");//TODO 更换为非正则的更快的方案
      // System.out.println(in);
      // System.out.println(Arrays.toString(data));
      commandList.executeSub(p,commandList,data,0,-1);
      switch(data[0]) {
        case "ip": {
          if(data.length>2) {
            switch(data[1]) {
              case "set":
                p.serverInfo.setFromString(data[2],12347);
                break;
            }
          }else System.out.println(p.serverInfo.toString());
        }
          break;
      }
    }
  }
  @FunctionalInterface
  public interface Command{
    public void execute(Server0002 p,CommandList parent,String[] data,int pointer,int maxStep);
  }
  public static class CommandList implements Command{
    public HashMap<String,CommandList> subCommand=new HashMap<>();
    public CommandList parent;
    public String key;
    public Command command,contrary;
    public CommandList() {}
    public CommandList(CommandList parent,String key,Command command) {
      this.parent=parent;
      this.key=key;
      this.command=command;
    }
    public void put(String string,Command command) {
      subCommand.put(string,new CommandList(parent,string,command));
    }
    public void put(String string,CommandList in) {
      subCommand.put(string,in);
    }
    public void execute(Server0002 p,CommandList parent,String[] data,int pointer,int maxStep) {
      if(maxStep==0) return;
      command.execute(p,parent,data,pointer,maxStep-=1);
      if((pointer+=1)>=data.length) return;
      executeSub(p,parent,data,pointer,maxStep);
    }
    public void executeSub(Server0002 p,CommandList parent,String[] data,int pointer,int maxStep) {
      // System.out.println(data[pointer]);
      CommandList e=subCommand.get(data[pointer]);
      // System.out.println(e);
      if(e!=null) e.execute(p,parent,data,pointer,maxStep);
      else contrary.execute(p,parent,data,pointer,maxStep);
    }
  }
}