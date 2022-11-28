package ru.practicum.statsserver.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.statsserver.stats.dto.EndpointHit;
import ru.practicum.statsserver.stats.dto.ViewStats;
import ru.practicum.statsserver.stats.mapper.StatsMapper;
import ru.practicum.statsserver.stats.repository.StatsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;

    @Override
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
/*        List<Stats> hits = statsRepository.findAll(
                formatter(start),
                formatter(end),
                uris == null ? Collections.emptyList() : uris);
        if (unique) {
            List<Stats> uniqueHits = new ArrayList<>();
            List<String> uniqueIps = hits.stream().map(Stats::getIp).distinct().collect(Collectors.toList());
            for (String ip : uniqueIps) {
                uniqueHits.add(hits.stream().filter(x -> x.getIp().equals(ip)).findFirst().get());
            }
            hits = uniqueHits;
        }
        return hits.stream().map(StatsMapper::toViewStats).collect(Collectors.toList());*/
        if (!unique) {
            return statsRepository.getAll(start, end, uris);
        } else return statsRepository.getAllUnique(start, end, uris, unique);
    }

    @Override
    @Transactional
    public void createStat(EndpointHit endpointHit) {
        statsRepository.save(StatsMapper.toStatsEndpoint(endpointHit));
    }

    private String formatter(LocalDateTime start) {
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
