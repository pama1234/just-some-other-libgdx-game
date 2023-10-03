package pama1234.app.game.server.duel.util.input;

import pama1234.util.protobuf.InputDataProto;

public class ServerInputData{
  public float dx,dy;
  public boolean isUpPressed=false;
  public boolean isDownPressed=false;
  public boolean isLeftPressed=false;
  public boolean isRightPressed=false;
  public boolean isZPressed=false;
  public boolean isXPressed=false;
  public boolean isCPressed=false;
  public void copyToProto(InputDataProto.InputData.Builder in) {
    in.setDx(dx);
    in.setDy(dy);
    in.setIsUpPressed(isUpPressed);
    in.setIsDownPressed(isDownPressed);
    in.setIsLeftPressed(isLeftPressed);
    in.setIsRightPressed(isRightPressed);
    in.setIsZPressed(isZPressed);
    in.setIsXPressed(isXPressed);
    // in.setIsCPressed(isXPressed);
  }
  public void copyFromProto(InputDataProto.InputData in) {
    dx=in.getDx();
    dy=in.getDy();
    isUpPressed=in.getIsUpPressed();
    isDownPressed=in.getIsDownPressed();
    isLeftPressed=in.getIsLeftPressed();
    isRightPressed=in.getIsRightPressed();
    isZPressed=in.getIsZPressed();
    isXPressed=in.getIsXPressed();
    // isXPressed=in.getIsCPressed();
  }
}
