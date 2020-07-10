package com.kuroha.algorithm.http;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLContext;
import java.net.Authenticator;
import java.net.CookieManager;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Chenyudeng
 */
@Slf4j
public class HttpTest {

    public static void main(String[] args) throws Exception {
        Authenticator authenticator = new Authenticator() {

        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 1, TimeUnit.HOURS, new LinkedBlockingQueue<>(), runnable -> new Thread(runnable,"HttpTest"));
        HttpClient httpClient = HttpClient.newBuilder()
                .authenticator(authenticator)
                .connectTimeout(Duration.ofSeconds(10))
                .sslContext(SSLContext.getDefault())
                .proxy(ProxySelector.getDefault())
                .executor(executor)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .cookieHandler(new CookieManager())
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://test-hej.rightknights.com/account/status"))
                .GET().version(HttpClient.Version.HTTP_2)
                .build();
        new HttpResponse.BodyHandler<String>() {
            @Override
            public HttpResponse.BodySubscriber<String> apply(HttpResponse.ResponseInfo responseInfo) {
                return null;
            }
        };
        long start = System.currentTimeMillis();
//        for (int i = 0; i < 1000; i++) {
//            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
////            System.out.println(httpResponse.body());
//        }
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        start = System.currentTimeMillis();
        List<CompletableFuture<HttpResponse<String>>> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            CompletableFuture<HttpResponse<String>> completableFuture = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString());
//            System.out.println(httpResponse.body());
            list.add(completableFuture);
//            String body = completableFuture.get().body();
//            System.out.println(body);
        }
        for (CompletableFuture<HttpResponse<String>> completableFuture : list) {
            try {
                String body = completableFuture.get().body();
            }catch (Exception e) {
                log.error(e.getMessage(),e);
            }

//            System.out.println(body);
        }
        end = System.currentTimeMillis();
        System.out.println(end-start);
    }

}
