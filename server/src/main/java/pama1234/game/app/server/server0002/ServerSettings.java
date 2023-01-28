package pama1234.game.app.server.server0002;

import com.esotericsoftware.kryo.serializers.TaggedFieldSerializer.Tag;

import pama1234.util.net.ServerInfo;

public class ServerSettings{
  @Tag(0)
  public ServerInfo serverInfo;
}