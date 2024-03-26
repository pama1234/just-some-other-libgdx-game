package pama1234.game.app.net;

import io.jpower.kcp.example.echo.EchoServerHandler;
import io.jpower.kcp.netty.UkcpChannel;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:szhnet@gmail.com">szh</a>
 */
public class KcpServerHandler extends ChannelInboundHandlerAdapter{

  private static Logger log=LoggerFactory.getLogger(EchoServerHandler.class);

  @Override
  public void channelActive(ChannelHandlerContext context) throws Exception {
    UkcpChannel kcpCh=(UkcpChannel)context.channel();
    kcpCh.conv(KcpServer.CONV);
  }

  @Override
  public void channelRead(ChannelHandlerContext context,Object msg) {
    ByteBuf buf=(ByteBuf)msg;
    short curCount=buf.getShort(buf.readerIndex());
    context.writeAndFlush(msg);

    if(curCount==-1) {
      context.close();
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext context,Throwable cause) {
    // Close the connection when an exception is raised.
    log.error("exceptionCaught",cause);
    context.close();
  }

}
