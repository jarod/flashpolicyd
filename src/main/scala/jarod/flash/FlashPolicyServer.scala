package jarod.flash

import java.util.concurrent.Executors
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory
import org.jboss.netty.channel.socket.ServerSocketChannelFactory
import org.jboss.netty.handler.codec.frame.FixedLengthFrameDecoder
import java.net.InetSocketAddress
import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel._
import org.jboss.netty.buffer._
import java.nio.ByteOrder

class FlashPolicyServer {

    def startup {
    	val factory = new  NioServerSocketChannelFactory(
    			Executors.newFixedThreadPool(1), Executors.newFixedThreadPool(2))
        val bootstrap = new ServerBootstrap(factory)
        bootstrap.setPipelineFactory(new ChannelPipelineFactory {
            override def getPipeline =
                Channels.pipeline(new FlashPolicyDecoder, new FlashPolicyHandler)
        })
        bootstrap.setOption("reuseAddress", true)
        bootstrap.setOption("child.keepAlive", false)
        bootstrap.setOption("child.tcpNoDelay", true)
        bootstrap.bind(new InetSocketAddress(843))
        println("FlashPolicyServer started listen on port 843")
    }
}

object FlashPolicy {
    val RequestContent = "<policy-file-request/>\0".getBytes
    val ResponseContent = ChannelBuffers.wrappedBuffer(
        "<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>\0".getBytes)
}

class FlashPolicyHandler extends SimpleChannelHandler {

    override def exceptionCaught(ctx: ChannelHandlerContext, evt: ExceptionEvent) {
        evt.getCause match {
            case ex => ex.printStackTrace(System.err)
        }
    }

    override def messageReceived(ctx: ChannelHandlerContext, evt: MessageEvent) {
        ctx.getChannel.write(FlashPolicy.ResponseContent)
            .addListener(ChannelFutureListener.CLOSE)
    }
}

class FlashPolicyDecoder extends FixedLengthFrameDecoder(FlashPolicy.RequestContent.length)