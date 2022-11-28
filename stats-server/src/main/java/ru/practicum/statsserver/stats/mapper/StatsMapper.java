package ru.practicum.statsserver.stats.mapper;

import ru.practicum.statsserver.stats.dto.EndpointHit;
import ru.practicum.statsserver.stats.dto.ViewStats;
import ru.practicum.statsserver.stats.model.Stats;

public class StatsMapper {

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

    public static ViewStats toViewStats(Stats stats) {
        return new ViewStats(
                stats.getApp(),
                stats.getUri(),
                stats.getId());
    }
}
