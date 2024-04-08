package pama1234.server.game.net;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import pama1234.math.physics.MassPoint;
import pama1234.math.vec.Vec2f;
import pama1234.server.game.life.particle.core.MetaInfo;
import pama1234.server.game.life.particle.core.MetaInfoUnit;
import pama1234.server.game.life.particle.net.message.CellCache;
import pama1234.server.game.life.particle.net.message.GameCache;
import pama1234.server.game.life.particle.net.message.GameCache.CellCenterCache;
import pama1234.server.game.life.particle.net.message.GameCtrlCache;
import pama1234.server.game.life.particle.net.message.RoomInfo;

public class TransferManager{
  // public static Lz4FrameEncoder lz4Encoder;
  // public static Lz4FrameDecoder lz4Decoder;

  // static {
  //   lz4Encoder=new Lz4FrameEncoder();
  //   lz4Decoder=new Lz4FrameDecoder();
  // }

  public NetConst netConst;
  public Kryo kryo;

  // public LZ4Factory lz4;
  // public LZ4Compressor compressor;
  // public LZ4FastDecompressor decompressor;

  public TransferManager() {
    init();
  }

  public TransferManager init() {
    kryo=new Kryo();

    // java的基本类型
    kryo.register(int[].class);
    kryo.register(float[].class);
    kryo.register(float[][].class);

    // 游戏的类型
    kryo.register(NetConst.class);
    kryo.register(MetaInfo.class);
    kryo.register(MetaInfo[].class);
    kryo.register(MetaInfoUnit.class);
    kryo.register(MetaInfoUnit[].class);
    kryo.register(MetaInfoUnit[][].class);
    kryo.register(RoomInfo.class);

    kryo.register(CellCenterCache.class);

    kryo.register(GameCache.class);
    kryo.register(GameCtrlCache.class);
    kryo.register(CellCache.class);

    // 框架组的基本类型
    kryo.register(ArrayList.class);
    kryo.register(MassPoint.class);
    kryo.register(Vec2f.class);

    // lz4=LZ4Factory.fastestInstance();
    // compressor=lz4.fastCompressor();
    // compressor.maxCompressedLength(1024);
    // decompressor=lz4.fastDecompressor();

    return this;
  }

  public synchronized <T> ByteBuf toByteBuf(T in) {
    // buf.clear();
    ByteBuf out=Unpooled.buffer();
    Output output=new Output(new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        out.writeByte(b);
      }
    });
    synchronized(kryo) {
      kryo.writeClassAndObject(output,in);
    }
    output.flush();
    return out;
  }
  public <T> T fromByteBuf(ByteBuf in) {
    synchronized(kryo) {
      byte[] inByteArray=new byte[in.readableBytes()];
      in.readBytes(inByteArray);
      Input input=new Input(inByteArray);
      return (T)kryo.readClassAndObject(input);
    }
  }
}
