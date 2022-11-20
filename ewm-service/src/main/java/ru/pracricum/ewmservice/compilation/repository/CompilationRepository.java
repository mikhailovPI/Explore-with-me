package ru.pracricum.ewmservice.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pracricum.ewmservice.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
