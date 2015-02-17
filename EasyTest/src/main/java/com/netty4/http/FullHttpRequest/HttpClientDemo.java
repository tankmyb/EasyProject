package com.netty4.http.FullHttpRequest;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.protobuf.testProtobuf.HeaderBuf;
  
public class HttpClientDemo {  
    public static void main(String[] args) throws ClientProtocolException,  
            IOException {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
        HttpHost proxy = new HttpHost("127.0.0.1",  
                8844);  
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,  
                proxy);  
        HttpPost httppost = new HttpPost("");  
        HeaderBuf.Header.Builder address = HeaderBuf.Header  
                .newBuilder();  
        address.setId(1);
        address.setName("aaaaa");
        ByteArrayEntity entity = new ByteArrayEntity(address.build().toByteArray());  
        httppost.setEntity(entity);  
        HttpResponse response = httpclient.execute(httppost);  
        HttpEntity receiveEntity = response.getEntity();  
        System.out.println("----------------------------------------");  
        System.out.println(response.getStatusLine());  
        if (receiveEntity != null) {  
            System.out.println("Response content length: " + receiveEntity.getContentLength());  
        }  
        System.out.println("success");  
    }  
}  