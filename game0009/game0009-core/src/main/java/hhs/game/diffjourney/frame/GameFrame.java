package hhs.game.diffjourney.frame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.game.diffjourney.attacks.BasicAttack;
import hhs.game.diffjourney.entities.Character;
import hhs.game.diffjourney.item.Item;
import hhs.gdx.hsgame.entities.BasicEntity;
import hhs.gdx.hsgame.entities.Entity;
import hhs.gdx.hsgame.entities.EntityCenter;

public class GameFrame extends Entity{

  public static int ssize=20,msize=100,lsize=300;

  EntityCenter<Character> characters;
  EntityCenter<BasicAttack> attcks;
  EntityCenter<BasicEntity> scenes;
  EntityCenter<Item> items;

  public GameFrame() {
    characters=new EntityCenter<>(lsize);
    attcks=new EntityCenter<>(ssize);
    scenes=new EntityCenter<>(ssize);
    items=new EntityCenter<>(lsize);
  }

  public void addEntity(BasicEntity entity) {
    if(entity instanceof Character c) {
      characters.addEntity(c);
      return;
    }
    if(entity instanceof BasicAttack ba) {
      attcks.addEntity(ba);
      return;
    }
    if(entity instanceof Item i) {
      items.add(i);
      return;
    }
  }

  @Override
  public void dispose() {}

  @Override
  public void update(float delta) {}

  @Override
  public void render(SpriteBatch batch) {}
}
