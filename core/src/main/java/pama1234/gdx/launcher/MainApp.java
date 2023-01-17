package pama1234.gdx.launcher;

import com.badlogic.gdx.Game;

import pama1234.gdx.game.app.*;
import pama1234.gdx.game.app.app0001.*;
import pama1234.gdx.game.app.app0002.*;
import pama1234.gdx.game.app.app0003.*;
import pama1234.gdx.game.app.app0004.*;

public class MainApp extends Game{
  public int screenType=11;
  @Override
  public void create() {
    switch(screenType) {
      case 1: {
        setScreen(new Screen0001());// 3D 粒子系统 单机模式
      }
        break;
      case 2: {
        setScreen(new Screen0002());
      }
        break;
      case 3: {
        setScreen(new Screen0003());
      }
        break;
      case 4: {
        setScreen(new Screen0004());
      }
        break;
      case 5: {
        setScreen(new Screen0005());
      }
        break;
      case 6: {
        setScreen(new Screen0006());
      }
        break;
      case 7: {
        setScreen(new Screen0007());
      }
        break;
      case 8: {
        setScreen(new Screen0008());
      }
        break;
      case 9: {
        setScreen(new Screen0009());
      }
        break;
      case 10: {
        setScreen(new Screen0010());
      }
        break;
      case 11: {
        setScreen(new Screen0011());
      }
        break;
      case 12: {
        setScreen(new Screen0012());
      }
        break;
      case 13: {
        setScreen(new Screen0013());
      }
        break;
      default:
        System.err.println("Screen App not found screenType="+screenType);
        break;
    }
  }
  @Override
  public void dispose() {
    super.dispose();
    screen.dispose();
  }
}