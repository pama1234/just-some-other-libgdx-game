package pama1234.app.game.server.duel.net;

import java.util.LinkedList;

public class OutputData{
  public LinkedList<GroupData> elements;

  public static class GroupData{
    public LinkedList<OutputDataElement> player;
    public LinkedList<OutputDataElement> shortArrow;
    public LinkedList<OutputDataElement> longArrowHead;
    public LinkedList<OutputDataElement> longArrowShaft;
  }
  public static class OutputDataElement{
    public float x;
    public float y;
    public float angle;
    public int id;
    public int type;
  }
}
