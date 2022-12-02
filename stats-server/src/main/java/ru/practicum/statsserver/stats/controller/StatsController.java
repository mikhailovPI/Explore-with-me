package ru.practicum.statsserver.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statsserver.stats.dto.EndpointHit;
import ru.practicum.statsserver.stats.dto.ViewStats;
import ru.practicum.statsserver.stats.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService statsService;

    @GetMapping(path = "/stats")
    public List<ViewStats> getStats(
            @RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(name = "uris") List<String> uris,
            @RequestParam(name = "unique") Boolean unique) {
        return statsService.getStats(
                start,
                end,
                uris,
                unique);
    }

    @PostMapping(path = "/hit")
    public void createStat(@RequestBody EndpointHit endpointHit) {
        statsService.createStat(endpointHit);
    }
}
