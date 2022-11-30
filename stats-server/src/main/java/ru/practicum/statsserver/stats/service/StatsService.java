package ru.practicum.statsserver.stats.service;

import org.springframework.stereotype.Service;
import ru.practicum.statsserver.stats.dto.EndpointHit;
import ru.practicum.statsserver.stats.dto.EventViews;
import ru.practicum.statsserver.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface StatsService {
    List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    void createStat(EndpointHit endpointHit);

    //EventViews getEventViews(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
