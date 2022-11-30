package ru.practicum.statsserver.stats.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.statsserver.stats.dto.EndpointHit;
import ru.practicum.statsserver.stats.dto.ViewStats;
import ru.practicum.statsserver.stats.model.Stats;
import ru.practicum.statsserver.stats.repository.StatsRepository;

import java.util.List;

@Component
public class StatsMapper {

    private static StatsRepository statsRepository;

    public StatsMapper(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public static EndpointHit toEndpointHit(Stats stats) {
        return new EndpointHit(
                stats.getId(),
                stats.getApp(),
                stats.getUri(),
                stats.getIp(),
                stats.getTimestamp());
    }

    public static Stats toStatsEndpoint(EndpointHit endpointHit) {
        return new Stats(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp());
    }

   // public static ViewStats toViewStats(Stats stats) {

        public static ViewStats toViewStats(List<Stats> statHit) {
            if (statHit.isEmpty()) {
                return new ViewStats();
            }
            return new ViewStats(
                    statHit.get(0).getApp(),
                    statHit.get(0).getUri(),
                    (long) statHit.size()
            );


/*        return ViewStats
                .builder()
                .app(stats.getApp())
                .uri(stats.getUri())
                .hits(statsRepository.findAllByAppAndUri(stats.getApp(), stats.getUri()).size())
                .build();*/

/*        return new ViewStats(
                stats.getApp(),
                stats.getUri(),
                null);*/
                //statsRepository.findAllByAppAndUri(Stats.getApp(), Stats.getUri()).size()));
    }
}