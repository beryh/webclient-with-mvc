# Webclient vs RestTemplate (with CompletableFuture)
- Spring WebMVC로 구성된 두 (Interface / Core) Server간 통신에서, 통신 구간 구현을 Reactor 방식과 CompletableFuture를 이용한 Async 방식으로 각각 구현
- Interface Server는 요청을 받으면 10개의 Key List를 돌면서, 각각의 구현방법에 따라 통신하여 서버로부터 Key에 해당하는 Payload를 받아온다.
    - Payload 데이터 연산을 가정하여, 10초의 Thread Sleep을 줌

- ${interface_server_host}:8800/async
    - KeyList를 돌며 CompletableFuture를 통해 비동기 요청을 생성
    - 각 비동기 요청은 RestTemplate을 통해 서버에 요청
    - Future로 반환받은 응답 데이터를 List로 저장하여 반환

- ${interface_server_host}:8800/webflux
    - KeyList를 Flux로 변환
    - WebClient를 통해 서버에 요청
    - 반환받은 응답 데이터를 stream으로 변환한 후 List에 저장하여 반환

---
- TODO
    - Executor, Thread Pool 설정
    - Backpressure (for webflux)
    - Metric 수집 및 부하 발생 시나리오