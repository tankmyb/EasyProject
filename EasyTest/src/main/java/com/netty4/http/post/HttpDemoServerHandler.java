package com.netty4.http.post;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.EndOfDataDecoderException;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder.ErrorDataDecoderException;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;
import io.netty.util.CharsetUtil;
 
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
 
import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
 
public class HttpDemoServerHandler extends SimpleChannelInboundHandler<HttpObject> {
 
    private HttpRequest request;
 
    private boolean readingChunks;
 
    private final StringBuilder responseContent = new StringBuilder();
 
    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); //Disk
 
    private HttpPostRequestDecoder decoder;
 
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (decoder != null) {
            decoder.cleanFiles();
        }
    }
 
    public void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
 
        System.err.println(msg.getClass().getName());
        /**
         * msg的类型
         * {@link DefaultHttpRequest}
         * {@link LastHttpContent}
         */
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            URI uri = new URI(request.getUri());
            System.err.println("request uri==" + uri.getPath());
 
            if (uri.getPath().equals("/favicon.ico")) {
                return;
            }
            if (uri.getPath().equals("/")) {
                writeMenu(ctx);
                return;
            }
            responseContent.setLength(0);
            responseContent.append("WELCOME TO THE WILD WILD WEB SERVER\r\n");
            responseContent.append("===================================\r\n");
 
            responseContent.append("VERSION: " + request.getProtocolVersion().text() + "\r\n");
 
            responseContent.append("REQUEST_URI: " + request.getUri() + "\r\n\r\n");
            responseContent.append("\r\n\r\n");
 
            // new getMethod
            for (Entry<String, String> entry : request.headers()) {
                responseContent.append("HEADER: " + entry.getKey() + '=' + entry.getValue() + "\r\n");
            }
            responseContent.append("\r\n\r\n");
 
            // new getMethod
            Set<Cookie> cookies;
            String value = request.headers().get(COOKIE);
            if (value == null) {
                /**
                 * Returns an empty set (immutable).
                 */
                cookies = Collections.emptySet();
            } else {
                cookies = CookieDecoder.decode(value);
            }
            for (Cookie cookie : cookies) {
                responseContent.append("COOKIE: " + cookie.toString() + "\r\n");
            }
            responseContent.append("\r\n\r\n");
 
            /**
             * List<String>表示当参数相同时，把相同的参数的值放在list中
             */
            QueryStringDecoder decoderQuery = new QueryStringDecoder(request.getUri());
            Map<String, List<String>> uriAttributes = decoderQuery.parameters();
            for (Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                for (String attrVal : attr.getValue()) {
                    responseContent.append("URI: " + attr.getKey() + '=' + attrVal + "\r\n");
                }
            }
            responseContent.append("\r\n\r\n");
 
            // if GET Method: should not try to create a HttpPostRequestDecoder
            if (request.getMethod().equals(HttpMethod.GET)) {
            	System.out.println("=============get");
                // GET Method: should not try to create a HttpPostRequestDecoder
                // So stop here
                responseContent.append("\r\n\r\nEND OF GET CONTENT\r\n");
                writeResponse(ctx.channel());
                return;
            }
 
 
            //判断request请求是否是post请求
            if (request.getMethod().equals(HttpMethod.POST)) {
                System.err.println("===this is http post===");
                try {
                    /**
                     * 通过HttpDataFactory和request构造解码器
                     */
                    decoder = new HttpPostRequestDecoder(factory, request);
                } catch (ErrorDataDecoderException e1) {
                    e1.printStackTrace();
                    responseContent.append(e1.getMessage());
                    writeResponse(ctx.channel());
                    ctx.channel().close();
                    return;
                }
 
                readingChunks = HttpHeaders.isTransferEncodingChunked(request);
                responseContent.append("Is Chunked: " + readingChunks + "\r\n");
                responseContent.append("IsMultipart: " + decoder.isMultipart() + "\r\n");
                if (readingChunks) {
                    // Chunk version
                    responseContent.append("Chunks: ");
                    readingChunks = true;
                }
            }
        }
 
        if (decoder != null) {
            if (msg instanceof HttpContent) {
                // New chunk is received
                HttpContent chunk = (HttpContent) msg;
                try {
                    decoder.offer(chunk);
                } catch (ErrorDataDecoderException e1) {
                    e1.printStackTrace();
                    responseContent.append(e1.getMessage());
                    writeResponse(ctx.channel());
                    ctx.channel().close();
                    return;
                }
                responseContent.append('o');
                try {
                    while (decoder.hasNext()) {
                        InterfaceHttpData data = decoder.next();
                        if (data != null) {
                            try {
                                writeHttpData(data);
                            } finally {
                                data.release();
                            }
                        }
                    }
                } catch (EndOfDataDecoderException e1) {
                    responseContent.append("\r\n\r\nEND OF CONTENT CHUNK BY CHUNK\r\n\r\n");
                }
 
                // example of reading only if at the end
                if (chunk instanceof LastHttpContent) {
                    writeResponse(ctx.channel());
                    readingChunks = false;
                    reset();
                }
            }
        }
    }
 
    private void reset() {
        request = null;
        // destroy the decoder to release all resources
        decoder.destroy();
        decoder = null;
    }
 
    private void writeHttpData(InterfaceHttpData data) {
        /**
         * HttpDataType有三种类型
         * Attribute, FileUpload, InternalAttribute
         */
        if (data.getHttpDataType() == HttpDataType.Attribute) {
            Attribute attribute = (Attribute) data;
            String value;
            try {
                value = attribute.getValue();
            } catch (IOException e1) {
                e1.printStackTrace();
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType().name() + ":"
                        + attribute.getName() + " Error while reading value: " + e1.getMessage() + "\r\n");
                return;
            }
            if (value.length() > 100) {
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType().name() + ":"
                        + attribute.getName() + " data too long\r\n");
            } else {
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType().name() + ":"
                        + attribute.toString() + "\r\n");
            }
        }
    }
 
    private void writeResponse(Channel channel) {
        // Convert the response content to a ChannelBuffer.
        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
        responseContent.setLength(0);
 
        // Decide whether to close the connection or not.
        boolean close = request.headers().contains(CONNECTION, HttpHeaders.Values.CLOSE, true)
                || request.getProtocolVersion().equals(HttpVersion.HTTP_1_0)
                && !request.headers().contains(CONNECTION, HttpHeaders.Values.KEEP_ALIVE, true);
 
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
 
        if (!close) {
            // There's no need to add 'Content-Length' header
            // if this is the last response.
            response.headers().set(CONTENT_LENGTH, buf.readableBytes());
        }
 
        Set<Cookie> cookies;
        String value = request.headers().get(COOKIE);
        if (value == null) {
            cookies = Collections.emptySet();
        } else {
            cookies = CookieDecoder.decode(value);
        }
        if (!cookies.isEmpty()) {
            // Reset the cookies if necessary.
            for (Cookie cookie : cookies) {
                response.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
            }
        }
        // Write the response.
        ChannelFuture future = channel.writeAndFlush(response);
        // Close the connection after the write operation is done if necessary.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
 
    private void writeMenu(ChannelHandlerContext ctx) {
        // print several HTML forms
        // Convert the response content to a ChannelBuffer.
        responseContent.setLength(0);
 
        // create Pseudo Menu
        responseContent.append("<html>");
        responseContent.append("<head>");
        responseContent.append("<title>Netty Test Form</title>\r\n");
        responseContent.append("</head>\r\n");
        responseContent.append("<body bgcolor=white><style>td{font-size: 12pt;}</style>");
 
        responseContent.append("<table border=\"0\">");
        responseContent.append("<tr>");
        responseContent.append("<td>");
        responseContent.append("<h1>Netty Test Form</h1>");
        responseContent.append("Choose one FORM");
        responseContent.append("</td>");
        responseContent.append("</tr>");
        responseContent.append("</table>\r\n");
 
        // GET
        responseContent.append("<CENTER>GET FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
        responseContent.append("<FORM ACTION=\"/from-get\" METHOD=\"GET\">");
        responseContent.append("<input type=hidden name=getform value=\"GET\">");
        responseContent.append("<table border=\"0\">");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"secondinfo\" size=20>");
        responseContent
                .append("<tr><td>Fill with value: <br> <textarea name=\"thirdinfo\" cols=40 rows=10></textarea>");
        responseContent.append("</td></tr>");
        responseContent.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
        responseContent.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
        responseContent.append("</table></FORM>\r\n");
        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
 
        // POST
        responseContent.append("<CENTER>POST FORM<HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
        responseContent.append("<FORM ACTION=\"/from-post\" METHOD=\"POST\">");
        responseContent.append("<input type=hidden name=getform value=\"POST\">");
        responseContent.append("<table border=\"0\">");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"info\" size=10></td></tr>");
        responseContent.append("<tr><td>Fill with value: <br> <input type=text name=\"secondinfo\" size=20>");
        responseContent
                .append("<tr><td>Fill with value: <br> <textarea name=\"thirdinfo\" cols=40 rows=10></textarea>");
        responseContent.append("<tr><td>Fill with file (only file name will be transmitted): <br> "
                + "<input type=file name=\"myfile\">");
        responseContent.append("</td></tr>");
        responseContent.append("<tr><td><INPUT TYPE=\"submit\" NAME=\"Send\" VALUE=\"Send\"></INPUT></td>");
        responseContent.append("<td><INPUT TYPE=\"reset\" NAME=\"Clear\" VALUE=\"Clear\" ></INPUT></td></tr>");
        responseContent.append("</table></FORM>\r\n");
        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
        responseContent.append("<CENTER><HR WIDTH=\"75%\" NOSHADE color=\"blue\"></CENTER>");
        responseContent.append("</body>");
        responseContent.append("</html>");
 
        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
 
        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(CONTENT_LENGTH, buf.readableBytes());
 
        // Write the response.
        ctx.channel().writeAndFlush(response);
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
 
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        messageReceived(ctx, msg);
    }
}