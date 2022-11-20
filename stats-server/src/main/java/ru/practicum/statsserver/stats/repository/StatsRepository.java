package ru.practicum.statsserver.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.statsserver.stats.model.Stats;

public interface StatsRepository extends JpaRepository<Stats, Long> {
}
