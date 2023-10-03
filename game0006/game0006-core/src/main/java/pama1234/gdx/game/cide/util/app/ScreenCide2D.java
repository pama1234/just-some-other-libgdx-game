package pama1234.gdx.game.cide.util.app;

import java.time.Duration;
import java.util.ArrayList;

import pama1234.gdx.game.cide.State0003Util.StateCenter0003;
import pama1234.gdx.game.cide.State0003Util.StateEntity0003;
import pama1234.gdx.util.app.ScreenCoreState2D;

public abstract class ScreenCide2D extends ScreenCoreState2D<StateCenter0003,StateEntity0003>{
  public boolean laodDebugState;
  
  // public static String day="day",hour="hour",minute="minute",second="second";
  public static String dayText="天",hourText="小时",minuteText="分钟",secondText="秒";
  public static String formatDuration(Duration duration) {
    var parts=new ArrayList<String>();
    long days=duration.toDaysPart();
    if(days>0) {
      parts.add(plural(days,dayText));
    }
    int hours=duration.toHoursPart();
    if(hours>0||!parts.isEmpty()) {
      parts.add(plural(hours,hourText));
    }
    int minutes=duration.toMinutesPart();
    if(minutes>0||!parts.isEmpty()) {
      parts.add(plural(minutes,minuteText));
    }
    int seconds=duration.toSecondsPart();
    parts.add(plural(seconds,secondText));
    return String.join(", ",parts);
  }
  public static String plural(long num,String unit) {
    // return num+" "+unit+(num==1?"":"s");
    return num+unit;
  }
}
