package com.netty.httpclient;
import java.net.InetSocketAddress;                                                  
import java.net.URI;                                                                
import java.util.concurrent.Executors;                                              
import java.util.*;

import org.jboss.netty.bootstrap.ClientBootstrap;                                   
import org.jboss.netty.channel.Channel;   
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelFutureListener;                                           
import org.jboss.netty.channel.ChannelFuture;  
import org.jboss.netty.channel.ChannelPipeline;   
import org.jboss.netty.channel.group.ChannelGroup;    
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;            
import org.jboss.netty.handler.codec.http.CookieEncoder;                            
import org.jboss.netty.handler.codec.http.DefaultHttpRequest;                       
import org.jboss.netty.handler.codec.http.HttpHeaders;                              
import org.jboss.netty.handler.codec.http.HttpMethod;                               
import org.jboss.netty.handler.codec.http.HttpRequest;                              
import org.jboss.netty.handler.codec.http.HttpVersion;  
import org.jboss.netty.handler.codec.http.HttpResponse;                            
import org.jboss.netty.channel.group.DefaultChannelGroup;                                                                                    
/**                                                                                 
 * A  asynchronous HTTP client implements with Netty  
 *                                   
 *                                                                                  
 * @author fengshihao (fengshihao@sohu-inc.com)                          
 *                                                                                  
 * @version 0.1     
 */                 

public class HttpClient{
                           
    private ClientBootstrap bootstrap = null;
    
    ChannelGroup allChannels = null;
    HttpClient(){ 
        bootstrap = new ClientBootstrap(                            
        new NioClientSocketChannelFactory(                                  
                Executors.newCachedThreadPool(),                            
                Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new HttpClientPipelineFactory()); 
        allChannels = new DefaultChannelGroup();
        }
 
 
    public void setOption(String key, Object value) {
        bootstrap.setOption(key, value);
    }
    
    public ChannelPipeline get(String url) throws Exception{
        return retrieve("GET", url);
        }
        
    public ChannelPipeline delete(String url) throws Exception{
        return retrieve("DELETE", url);
        }
        
    public ChannelPipeline post(String url, Map<String, Object> data) throws Exception{
        return retrieve("POST", url, data);
        }       
        
    public ChannelPipeline retrieve(String method, String url) throws Exception{
        return retrieve(method, url, null, null);
        }
        
        public ChannelPipeline retrieve(String method, String url, Map<String, Object> data ) throws Exception{
                return retrieve(method, url, data, null);
        }     
                  
    public ChannelPipeline retrieve(String method, String url, Map<String, Object>data, Map<String, String> cookie) throws Exception{
        if(url == null) throw new Exception("url is null") ;
        URI uri = new URI(url);                                                 
        String scheme = uri.getScheme() == null? "http" : uri.getScheme();          
        String host = uri.getHost() == null? "localhost" : uri.getHost();                         
                                                                                    
        if (!scheme.equals("http")) {                                               
            throw new Exception("just support http protocol") ;                        
        }
                                                
        HttpRequest request = new DefaultHttpRequest(                               
                HttpVersion.HTTP_1_1, HttpMethod.valueOf(method), uri.toASCIIString()); 
                        
        request.setHeader(HttpHeaders.Names.HOST, host);                            
        request.setHeader(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        if(cookie != null){
                CookieEncoder httpCookieEncoder = new CookieEncoder(false);  
                for (Map.Entry<String, String> m : cookie.entrySet()) {
                        httpCookieEncoder.addCookie(m.getKey(), m.getValue());
                        request.setHeader(HttpHeaders.Names.COOKIE, httpCookieEncoder.encode());    
                }
        }
        return retrieve(request);
         
    }
        
    public ChannelPipeline retrieve(HttpRequest request)throws Exception{
                URI uri = new URI(request.getUri());          
        int port = uri.getPort() == -1? 80 : uri.getPort(); 
                ChannelFuture future = bootstrap.connect(new InetSocketAddress(request.getHeader(HttpHeaders.Names.HOST) , port));
        future.addListener(new ConnectOk(request));
        allChannels.add(future.getChannel());
        return   future.getChannel().getPipeline();
        } 
   
    public void close(){
        allChannels.close().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
    }
    
    
    public static void main(String[] args){                       
                String url = "http://127.0.0.1:8844/login?name=aa54";                                                      
                                                                                    
        HttpClient hc = new HttpClient();
        
        try {
                //ChannelPipeline line = hc.get(url);
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("name", "post");
        	ChannelPipeline line = hc.post("http://127.0.0.1:8844/login", map);
                line.addLast("handler", new HttpResponseHandler());
                //Thread.sleep(4000);
                } catch(Exception ex) {
                        ex.printStackTrace();
                }
        //hc.close();              
    }                                                                               
}                                                                                   
                                        

class ConnectOk implements ChannelFutureListener {     
        private HttpRequest request=null;
        
        ConnectOk(HttpRequest req){
                this.request = req;
        }
                                                      
        public void operationComplete(ChannelFuture future){
        if (!future.isSuccess()) {                                                  
            future.getCause().printStackTrace();                                 
            return;                                                                 
        }                                                                           
        Channel channel = future.getChannel();                                                                            
        channel.write(request);                                                  
    }
}             