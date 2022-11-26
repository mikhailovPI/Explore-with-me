package ru.pracricum.ewmservice.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pracricum.ewmservice.compilation.dto.CompilationDto;
import ru.pracricum.ewmservice.compilation.model.Compilation;
import ru.pracricum.ewmservice.util.PageRequestOverride;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    List<Compilation> findByPinnedOrderByPinnedAsc(Boolean pinned, PageRequestOverride pageRequest);
}
