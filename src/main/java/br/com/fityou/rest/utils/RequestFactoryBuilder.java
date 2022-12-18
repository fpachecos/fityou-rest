package br.com.fityou.rest.utils;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public abstract class RequestFactoryBuilder {

    public static HttpComponentsClientHttpRequestFactory createSSLRequestFactory()
            throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {

        SSLContextBuilder sslcontext = new SSLContextBuilder();
        sslcontext.loadTrustMaterial(null, new TrustSelfSignedStrategy());

        CloseableHttpClient httpClient = HttpClientBuilder.create().setSslcontext(sslcontext.build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(httpClient);

        return factory;
    }

}
