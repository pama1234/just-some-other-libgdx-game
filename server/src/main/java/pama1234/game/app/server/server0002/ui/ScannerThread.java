package pama1234.game.app.server.server0002.ui;

import java.util.Scanner;

import pama1234.game.app.server.server0002.Server0002;

public class ScannerThread extends Thread{
  public Server0002 p;
  public Scanner scanner;
  // public ScannerThread(String name,Server3D p) {
  public ScannerThread(Server0002 p) {
    super("ScannerThread");
    this.p=p;
    scanner=new Scanner(System.in);
  }
  @Override
  public void run() {
    System.out.println("test of pama1234 v0.1 server");
    while(!p.stop) {
      String in=scanner.nextLine();
      String[] data=in.replaceAll("^\"","").split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");//TODO faster
      // System.out.println(in);
      // System.out.println(Arrays.toString(data));
      switch(data[0]) {
        case "help": {
          // System.out.println("切换中文提示信息请输入：lang cn"); //TODO
          System.out.println("available commands ↓");
          // System.out.println("version       show version info");
          System.out.println("stop          stop  server update");
          System.out.println("start         start server update");
          System.out.println("exit          stop  server and exit");
          System.out.println("ip            view serverInfo addr and port");
          // System.out.println("lang          change prompt language");
        }
          break;
        case "stop": {
          p.doUpdate=false;
          System.out.println("server update stopped.");
        }
          break;
        case "start": {
          p.doUpdate=true;
          System.out.println("server update started.");
        }
          break;
        case "exit": {
          p.doUpdate=false;
          p.stop=true;
          System.out.println("exit now.");
        }
          break;
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
        default:
          // System.out.println("["+data[0]+"]");
          System.out.println("type help to display help menu.");
          System.out.println("输入 help 显示帮助文档");
          break;
      }
    }
  }
  @FunctionalInterface
  public interface Command{
    public void execute(Server0002 p,String[] data);
  }
}