package pama1234.game.app.server.server0001.game;

import java.util.Scanner;

public class ScannerThread extends Thread{
  public Server0001 p;
  public Scanner scanner;
  // public ScannerThread(String name,Server3D p) {
  public ScannerThread(Server0001 p) {
    super("ScannerThread");
    this.p=p;
    scanner=new Scanner(System.in);
  }
  @Override
  public void run() {
    System.out.println("just-some-other-libgdx-game v0.1 server");
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
          System.out.println("stop          stop CellGroup3D update");
          System.out.println("start         start CellGroup3D update");
          // System.out.println("lang          change prompt language");
        }
          break;
        case "stop": {
          p.doUpdate=false;
          System.out.println("CellGroup3D update stopped.");
        }
          break;
        case "start": {
          p.doUpdate=true;
          System.out.println("CellGroup3D update started.");
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
}