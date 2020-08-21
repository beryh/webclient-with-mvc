package com.kakaobank.demo.services;

import com.kakaobank.demo.common.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class DemoService {
    private List<String> keyList = List.of("key1", "key2", "key3", "key4", "key5", "key6", "key7", "key8", "key9", "key0");

    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final Function<String, Supplier<Payload>> supplierTransform;

    public DemoService(RestTemplate restTemplate, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplate;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8008").build();

        this.supplierTransform = (key) -> () -> restTemplate.getForEntity(URI.create("http://localhost:8008/slow?key=" + key), Payload.class).getBody();
    }

    // 병렬 동기 요청 (CompletableFuture & RestTemplate)
    public List<Payload> asyncWithRestTemplate() {
        List<CompletableFuture<Payload>> futures =
                keyList.parallelStream()
                        .map(key -> CompletableFuture.supplyAsync(supplierTransform.apply(key)))
                        .collect(Collectors.toList());

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    // 병렬 비동기 요청 (For-Loop & WebClient)
    public List<Payload> requestReactiveWay() {
        return Flux.fromIterable(keyList)
                .parallel()
                .flatMap(key -> webClient.get().uri("slow?key=" + key).retrieve().bodyToMono(Payload.class))
                .sequential()
                .toStream()
                .collect(Collectors.toList());
    }

}
