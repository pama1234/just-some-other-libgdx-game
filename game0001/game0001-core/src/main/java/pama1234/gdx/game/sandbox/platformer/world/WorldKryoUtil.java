package pama1234.gdx.game.sandbox.platformer.world;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer;

import pama1234.gdx.game.sandbox.platformer.entity.GamePointEntity;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.DroppedItem;
import pama1234.gdx.game.sandbox.platformer.entity.entity0001.Fly;
import pama1234.gdx.game.sandbox.platformer.item.Inventory;
import pama1234.gdx.game.sandbox.platformer.item.Item;
import pama1234.gdx.game.sandbox.platformer.item.Item.ItemSlot;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock.MetaBlockAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaBlock.MetaBlockStoredAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature.MetaCreatureAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaCreature.MetaCreatureStoredAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem.MetaItemAttribute;
import pama1234.gdx.game.sandbox.platformer.metainfo.MetaItem.MetaItemStoredAttribute;
import pama1234.gdx.game.sandbox.platformer.player.GameMode;
import pama1234.gdx.game.sandbox.platformer.player.MainPlayer;
import pama1234.gdx.game.sandbox.platformer.region.Chunk;
import pama1234.gdx.game.sandbox.platformer.region.Chunk.BlockData;
import pama1234.gdx.game.sandbox.platformer.region.Region;
import pama1234.gdx.game.sandbox.platformer.region.block.Block;
import pama1234.math.physics.MassPoint;
import pama1234.math.physics.PathPoint;
import pama1234.math.physics.PathVar;
import pama1234.math.vec.Vec2f;

@SuppressWarnings("deprecation")
public class WorldKryoUtil{
  public static Region regionInstance;
  public static MainPlayer playerInstance;
  public static final Kryo kryo=new Kryo();
  static {
    kryo.setDefaultSerializer(TaggedFieldSerializer.class);
    Registration registration=kryo.register(Region.class);
    registration.setInstantiator(()->regionInstance==null?new Region():regionInstance);
    kryo.register(Chunk[][].class);
    kryo.register(Chunk[].class);
    kryo.register(Chunk.class);
    kryo.register(BlockData[][].class);
    kryo.register(BlockData[].class);
    kryo.register(BlockData.class);
    kryo.register(Block.class);
    kryo.register(Inventory.class);
    kryo.register(ItemSlot[].class);
    kryo.register(ItemSlot.class);
    kryo.register(Item.class);
    kryo.register(MassPoint.class,new FieldSerializer<MassPoint>(kryo,MassPoint.class));
    kryo.register(PathPoint.class,new FieldSerializer<PathPoint>(kryo,PathPoint.class));
    kryo.register(PathVar.class,new FieldSerializer<PathVar>(kryo,PathVar.class));
    kryo.register(int[].class);
    kryo.register(WorldData.class,new FieldSerializer<WorldData>(kryo,WorldData.class));
    kryo.register(Vec2f.class,new FieldSerializer<PathPoint>(kryo,Vec2f.class));
    registration=kryo.register(MainPlayer.class);
    registration.setInstantiator(()->playerInstance==null?new MainPlayer():playerInstance);
    kryo.register(GameMode.class);
    kryo.register(GamePointEntity[].class);
    kryo.register(DroppedItem.class);
    kryo.register(Fly.class);

    kryo.register(MetaItem.class);
    kryo.register(MetaBlock.class);
    kryo.register(MetaCreature.class);
    kryo.register(MetaBlockAttribute.class);
    kryo.register(MetaBlockStoredAttribute.class);
    kryo.register(MetaItemAttribute.class);
    kryo.register(MetaItemStoredAttribute.class);
    kryo.register(MetaCreatureAttribute.class);
    kryo.register(MetaCreatureStoredAttribute.class);
  }
}