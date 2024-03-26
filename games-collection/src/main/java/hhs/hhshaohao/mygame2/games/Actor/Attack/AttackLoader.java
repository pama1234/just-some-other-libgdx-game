package hhs.hhshaohao.mygame2.games.Actor.Attack;

import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import hhs.hhshaohao.mygame2.games.Actor.PlayerActor;
import com.badlogic.gdx.graphics.Texture;
import hhs.hhshaohao.mygame2.games.MyGame;

public class AttackLoader{

  XmlReader xml;

  public AttackLoader() {
    xml=new XmlReader();
  }

  public BasicAttack newAttack(FileHandle file,PlayerActor pa) throws GdxRuntimeException {
    XmlReader.Element root=xml.parse(file);
    BasicAttack a=null;
    if(root.getName().equals("Attack")) {
      switch(root.getInt("type")) {
        case 0:
          Texture t=MyGame.res.getTexture(root.get("texture","attack.png"));
          //a = new AutoShotAttack(pa,t,root.getFloat("width",10),root.getFloat("height",10));
          a=new Sword(pa,t,root.getFloat("width",10),root.getFloat("height",10));
          break;
        case 1:
          Texture te=MyGame.res.getTexture(root.get("texture","attack.png"));
          //a = new AutoShotAttack(pa,t,root.getFloat("width",10),root.getFloat("height",10));
          a=new AutoShotAttack(pa,te,root.getFloat("width",10),root.getFloat("height",10));
          break;
        case 2:

          break;
      }
      a.hurt=root.getFloat("hurt",10);
      a.wait=root.getFloat("wait",0.1f);
      a.repel=root.getFloat("repel",10);
      return a;
    }else {
      throw new GdxRuntimeException("The file format is incorrect");
    }
  }

}
