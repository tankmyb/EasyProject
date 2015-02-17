package com.java.ssl.httpclient;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
 
import javax.net.ssl.SSLContext;
 
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
 
/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {
 
    private static String CLIENT_KEY_STORE = "d:\\ssl\\sslclientkeys";
    private static String CLIENT_TRUST_KEY_STORE = "d:\\ssl\\sslclienttrust";
    private static String CLIENT_KEY_STORE_PASSWORD = "client";
    private static String CLIENT_TRUST_KEY_STORE_PASSWORD = "client";
    private static String CLIENT_KEY_PASS = "client";
 
 
    public final static void main(String[] args) throws Exception {
 
 
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream instream = new FileInputStream(new File(CLIENT_TRUST_KEY_STORE));
        try {
            trustStore.load(instream, CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
        } finally {
            instream.close();
        }
 
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        FileInputStream keyStoreInput = new FileInputStream(new File(CLIENT_KEY_STORE));
        try {
            keyStore.load(keyStoreInput, CLIENT_KEY_STORE_PASSWORD.toCharArray());
        } finally {
            keyStoreInput.close();
        }
 
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
                .loadKeyMaterial(keyStore, CLIENT_KEY_PASS.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"SSLv3"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
 
            HttpPost httpPost = new HttpPost("https://127.0.0.1:8888/aa?name=aaaaa");
            System.out.println("executing request" + httpPost.getRequestLine());
 
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
 
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    System.out.println("Response content length: " + entity.getContentLength());
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}