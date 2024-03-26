package pama1234.server.game.app.server0002.cmd;

import pama1234.server.game.app.server0002.Server0002;
import pama1234.server.game.app.server0002.cmd.ScannerThread.CommandList;

public class CommandGenerator{
  public static CommandList getCommand_0001(Server0002 p) {
    CommandList commandList=new CommandList();
    commandList.put("help",(p1,p2,d,pt,s)-> {
      // System.out.println("切换中文提示信息请输入：lang cn"); //TODO
      System.out.println("available commands ↓");
      // System.out.println("version       show version info");
      System.out.println("stop          stop  server update");
      System.out.println("start         start server update");
      System.out.println("exit          stop  server and exit");
      System.out.println("ip            view serverInfo addr and port");
      // System.out.println("lang          change prompt language");
    });
    commandList.put("stop",(p1,p2,d,pt,s)-> {
      p.doUpdate=false;
      System.out.println("server update stopped.");
    });
    commandList.put("start",(p1,p2,d,pt,s)-> {
      p.doUpdate=true;
      System.out.println("server update started.");
    });
    commandList.put("exit",(p1,p2,d,pt,s)-> {
      p.doUpdate=false;
      p.stop=true;
      System.out.println("exit now.");
    });
    commandList.contrary=(p1,p2,d,pt,s)-> {
      // System.out.println("["+data[0]+"]");
      System.out.println("type help to display help menu.");
      System.out.println("输入 help 显示帮助文档");
    };
    return commandList;
  }
}