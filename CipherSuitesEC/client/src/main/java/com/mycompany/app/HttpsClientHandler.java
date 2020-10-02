package com.mycompany.app;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import javax.net.ssl.SSLEngine;

/**
 * Listing 2.3 ChannelHandler for the client
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */

@Sharable
public class HttpsClientHandler
    extends SimpleChannelInboundHandler <HttpObject>{

	private SSLEngine engine;

    public HttpsClientHandler(SSLEngine engine) {
        super();
        this.engine = engine;
	}

	@Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

          if (msg instanceof HttpResponse) {
              HttpResponse response = (HttpResponse) msg;
            
            
              System.err.println("STATUS: " + response.status());
              System.err.println("VERSION: " + response.protocolVersion());
              //String cipherSuite = engine.getSSLParameters().getEndpointIdentificationAlgorithm();
              String cipherSuite = engine.getSession().getCipherSuite();
              System.out.println(cipherSuite);
              System.err.println();
  
              if (!response.headers().isEmpty()) {
                  for (CharSequence name: response.headers().names()) {
                      for (CharSequence value: response.headers().getAll(name)) {
                          System.err.println("HEADER: " + name + " = " + value);
                      }
                  }
                  System.err.println();
              }
  
              if (HttpUtil.isTransferEncodingChunked(response)) {
                  System.err.println("CHUNKED CONTENT {");
              } else {
                  System.err.println("CONTENT {");
              }
          }
          
          if (msg instanceof HttpContent) {
              HttpContent content = (HttpContent) msg;
  
              System.err.print(content.content().toString(CharsetUtil.UTF_8));
              System.err.flush();
  
              if (content instanceof LastHttpContent) {
                  System.err.println("} END OF CONTENT");
                  ctx.close();
              }
          }
      
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}