package ru.practicum.statsserver.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.statsserver.stats.dto.ViewStats;
import ru.practicum.statsserver.stats.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Stats, Long> {

    @Query("SELECT new ru.practicum.statsserver.stats.dto.ViewStats(e.app, e.uri, COUNT (e.ip)) " +
            "from Stats e WHERE e.timestamp> ?1 AND e.timestamp< ?2 GROUP BY e.app, e.uri")
    List<ViewStats> getAll(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.statsserver.stats.dto.ViewStats(e.app, e.uri, COUNT (DISTINCT e.ip)) from " +
            "Stats e WHERE e.timestamp> ?1 AND e.timestamp< ?2 GROUP BY e.app, e.uri")
    List<ViewStats> getAllUnique(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
