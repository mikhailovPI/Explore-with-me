package ru.pracricum.ewmservice.compilation.mapper;

import ru.pracricum.ewmservice.compilation.dto.CompilationDto;
import ru.pracricum.ewmservice.compilation.dto.NewCompilationDto;
import ru.pracricum.ewmservice.compilation.model.Compilation;
import ru.pracricum.ewmservice.event.dto.EventShortDto;
import ru.pracricum.ewmservice.event.mapper.EventMapper;
import ru.pracricum.ewmservice.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation, List<Event> events) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                createEventShort(events));
    }

    public static Compilation toCompilationNew(NewCompilationDto newCompilationDto) {
        return new Compilation(
                null,
                newCompilationDto.getPinned(),
                newCompilationDto.getTitle(),
                null);
    }

    private static List<EventShortDto> createEventShort(List<Event> eventList) {
        return eventList
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }
}
