package com.java.ssl.netty4;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import java.util.List;
import java.util.logging.Logger;
 
public class HttpDemoServerHandler extends SimpleChannelInboundHandler<HttpObject> {
 
    private static final Logger logger = Logger.getLogger(HttpDemoServerHandler.class.getName());
 
    private HttpRequest request;
 
 
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        
    }
 
    public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {}
 
    private String getParameter(QueryStringDecoder queryStringDecoder,
			String parameterName) {
		if (queryStringDecoder == null || parameterName == null) {
			return null;
		}
		List<String> values = (List<String>) queryStringDecoder.parameters()
				.get(parameterName);

		if ((values == null) || (values.isEmpty())) {
			return null;
		}
		return (String) values.get(0);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg)
			throws Exception {

        if (msg instanceof HttpRequest) {
            request = (HttpRequest) msg;

            String uri = request.getUri();
            System.out.println("Uri:" + uri);
            if (request.getMethod() == HttpMethod.GET) { // 处理get请求
            	 QueryStringDecoder queryDecoder = new QueryStringDecoder(request
     					.getUri());
                 System.out.println(queryDecoder.path());
                 String jsonpValue = getParameter(queryDecoder, "name");
                 System.out.println(jsonpValue);
            }else if (request.getMethod() == HttpMethod.POST) {
            	System.out.println("===3443========post");
            	HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                        new DefaultHttpDataFactory(false), request);
                InterfaceHttpData postData = decoder.getBodyHttpData("userNo"); // //
                System.out.println(postData+"==========");
                String question = "";
                if (postData.getHttpDataType() == HttpDataType.Attribute) {
                    Attribute attribute = (Attribute) postData;
                    question = attribute.getValue();
                    System.out.println("q:" + question);
     
                }
            }
           
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();

            String res = "I am OK";
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    OK, Unpooled.wrappedBuffer(res.getBytes("UTF-8")));
            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_LENGTH,
                    response.content().readableBytes());
            if (HttpHeaders.isKeepAlive(request)) {
                response.headers().set(CONNECTION, Values.KEEP_ALIVE);
            }
            ctx.write(response);
            ctx.flush();
        }
    
		
	}
}