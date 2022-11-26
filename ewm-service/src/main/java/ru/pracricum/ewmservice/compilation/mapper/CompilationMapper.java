package ru.pracricum.ewmservice.compilation.mapper;

import ru.pracricum.ewmservice.compilation.dto.CompilationDto;
import ru.pracricum.ewmservice.compilation.dto.NewCompilationDto;
import ru.pracricum.ewmservice.compilation.model.Compilation;
import ru.pracricum.ewmservice.event.mapper.EventMapper;
import ru.pracricum.ewmservice.event.model.Event;

import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                null);
    }

    public static Compilation toCompilationNew(NewCompilationDto newCompilationDto) {
        return new Compilation(
                null,
                newCompilationDto.getPinned(),
                newCompilationDto.getTitle(),
                null);
    }
}
