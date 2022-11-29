package ru.pracricum.ewmservice.stats.client;

import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class BaseClient {
    protected final RestTemplate restTemplate;

    protected ResponseEntity<Object> get(Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET,
                "/stats?start={start}&end={end}&uris={uri}&unique={unique}", parameters);
    }

    protected <T> void post(T body) {
        makeAndSendRequest(HttpMethod.POST, "/hit", body);
    }

    <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, @Nullable T body) {
        HttpEntity<T> requestEntity = null;

        ResponseEntity<Object> statsServerResponse;
        try {
            statsServerResponse = restTemplate.exchange(path, method, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(statsServerResponse);
    }

    static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}
