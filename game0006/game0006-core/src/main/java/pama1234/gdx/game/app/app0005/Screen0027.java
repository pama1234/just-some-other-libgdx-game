package pama1234.gdx.game.app.app0005;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import pama1234.gdx.util.app.ScreenCore2D;

public class Screen0027 extends ScreenCore2D{
  static {}
  public String PATH_TO_GRADLE_PROJECT="./";
  public String GRADLEW_EXECUTABLE="gradlew.bat";
  public String BLANK=" ";
  public String GRADLE_TASK="clean";
  
  public Process process;
  public Scanner s;
  // public StringBuilder stringBuilder;
  public ArrayList<String> data;
  {}
  @Override
  public void setup() {
    if(isAndroid) {
      cam2d.minScale=1/8f;
      
    }
    String command=PATH_TO_GRADLE_PROJECT+GRADLEW_EXECUTABLE+BLANK+GRADLE_TASK;
    try {
      Process process=Runtime.getRuntime().exec(command);
      s=new Scanner(process.getInputStream()).useDelimiter("\\A");
      data=new ArrayList<>();
      // stringBuilder=new StringBuilder();
      // String result=s.hasNext()?s.next():"";
    }catch(IOException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void update() {
    if(s.hasNextLine()) {
      // stringBuilder.append(s.nextLine());
      // data=stringBuilder.toString();
      data.add(s.nextLine());
    }
  }
  @Override
  public void display() {}
  @Override
  public void displayWithCam() {
    for(int i=0;i<data.size();i++) text(data.get(i),0,i*20);
  }
  @Override
  public void frameResized() {}
}
