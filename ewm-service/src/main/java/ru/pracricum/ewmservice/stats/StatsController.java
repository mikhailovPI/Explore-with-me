package ru.pracricum.ewmservice.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.stats.client.StatsClient;
import ru.pracricum.ewmservice.stats.dto.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatsController {
    private final StatsClient statsClient;

    @GetMapping(path = "/stats")
    public ResponseEntity<Object> getStats(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(name = "uris") List<String> uris,
            @RequestParam(name = "unique") Boolean unique) {
        return statsClient.getStats(
                start,
                end,
                uris,
                unique);
    }

    @PostMapping(path = "/hit")
    public void createStat(@RequestBody EndpointHit endpointHit) {
        statsClient.createStat(endpointHit);
    }
}