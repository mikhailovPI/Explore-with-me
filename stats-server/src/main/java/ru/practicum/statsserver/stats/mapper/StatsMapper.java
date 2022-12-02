package ru.practicum.statsserver.stats.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.statsserver.stats.dto.EndpointHit;
import ru.practicum.statsserver.stats.dto.ViewStats;
import ru.practicum.statsserver.stats.model.Stats;

import java.util.List;

@Component
public class StatsMapper {

    public static Stats toStatsEndpoint(EndpointHit endpointHit) {
        return new Stats(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp());
    }

        public static ViewStats toViewStats(List<Stats> statHit) {
            if (statHit.isEmpty()) {
                return new ViewStats();
            }
            return new ViewStats(
                    statHit.get(0).getApp(),
                    statHit.get(0).getUri(),
                    (long) statHit.size()
            );
    }
}