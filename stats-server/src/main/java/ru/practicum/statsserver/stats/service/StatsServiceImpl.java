package ru.practicum.statsserver.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statsserver.stats.dto.EndpointHit;
import ru.practicum.statsserver.stats.dto.ViewStats;
import ru.practicum.statsserver.stats.mapper.StatsMapper;
import ru.practicum.statsserver.stats.model.Stats;
import ru.practicum.statsserver.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<ViewStats> viewStats = new ArrayList<>();
        for (String uri : uris) {
            if (!unique && start.equals(end)) {
                viewStats.add(StatsMapper.toViewStats(
                        statsRepository.findAllByUri(uri)));
            } else if (unique && start.equals(end)) {
                viewStats.add(StatsMapper.toViewStats(
                        statsRepository.findDistinctByUriAndIpAndApp(uri)));
            } else if (!unique && !start.equals(end)) {
                viewStats.add(StatsMapper.toViewStats(
                        statsRepository.findAllByUriAndTimestampBetween(uri, start, end)));
            } else if (unique && !start.equals(end)) {
                viewStats.add(StatsMapper.toViewStats(
                        statsRepository.findDistinctByUriAndTimestampBetween(uri, start, end)));
            }
        }
        return viewStats;
    }

    @Override
    @Transactional
    public void createStat(EndpointHit endpointHit) {
        statsRepository.save(StatsMapper.toStatsEndpoint(endpointHit));
    }
}
