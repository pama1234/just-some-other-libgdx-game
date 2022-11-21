package pama1234.gdx.game.app;

// import io.netty.channel.ChannelHandlerContext;
// import pama1234.gdx.game.app.server.with2d.net.NetClient2D;
import pama1234.gdx.game.app.server.with2d.net.PlayerInfo2D;
import pama1234.gdx.game.app.server.with2d.net.WorldInfo2D;
import pama1234.gdx.util.app.UtilScreen2D;

/*
 * particle game 2D
 */
public class Screen0001 extends UtilScreen2D{
	// public NettyClient2DImpl client;
	//---
	public PlayerInfo2D infoOut;
	public WorldInfo2D infoIn;
	@Override
	public void setup() {
		// cam.pixelPerfect=true;
		// client=new NettyClient2DImpl("127.0.0.1",12347);
		// new Thread(client).start();
		// serverCenter.add.add(client);
		//---
		// infoIn=new WorldInfo2D();
		infoOut=new PlayerInfo2D();
	}
	@Override
	public void update() {
		if(infoIn!=null) println(infoIn.posX[1]);
	}
	@Override
	public void display() {}
	@Override
	public void frameResized() {}
	// public class NettyClient2DImpl extends NetClient2D<WorldInfo2D,PlayerInfo2D>{
	// 	public NettyClient2DImpl(String addr,int port) {
	// 		// super(addr,port,WorldInfo2D.class,PlayerInfo2D.class);
	// 		super(addr,port,PlayerInfo2D.class);
	// 	}
	// 	@Override
	// 	public void display() {}
	// 	@Override
	// 	public void update() {
	// 		if(channel!=null) channel.read();
	// 	}
	// 	@Override
	// 	public void pause() {}
	// 	@Override
	// 	public void resume() {}
	// 	@Override
	// 	public void read(ChannelHandlerContext context,WorldInfo2D in) {
	// 		System.out.println("Screen0001.NettyClient2DImpl.read()");
	// 		System.out.println(context);
	// 		System.out.println(in);
	// 		infoIn=in;
	// 		if(channel!=null) write(infoOut);
	// 	}
	// }
}