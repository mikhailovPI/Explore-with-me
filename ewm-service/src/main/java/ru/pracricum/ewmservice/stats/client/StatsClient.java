package ru.pracricum.ewmservice.stats.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.pracricum.ewmservice.stats.dto.EndpointHit;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends BaseClient {

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build());
    }

    public ResponseEntity<Object> getStats(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", URLEncoder.encode(formatter(start), StandardCharsets.UTF_8),
                "end", URLEncoder.encode(formatter(end), StandardCharsets.UTF_8),
                "uris", uris,
                "unique", unique);
        return get("/stats?start={start}&end={end}&uris={uri}&unique={unique}", parameters);
    }

    public void createStat(
            EndpointHit endpointHit) {
        post("/hit", endpointHit);
    }

    private String formatter(LocalDateTime start) {
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
