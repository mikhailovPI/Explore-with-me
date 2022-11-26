package ru.pracricum.ewmservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pracricum.ewmservice.compilation.dto.CompilationDto;
import ru.pracricum.ewmservice.compilation.dto.NewCompilationDto;
import ru.pracricum.ewmservice.compilation.mapper.CompilationMapper;
import ru.pracricum.ewmservice.compilation.model.Compilation;
import ru.pracricum.ewmservice.compilation.repository.CompilationRepository;
import ru.pracricum.ewmservice.event.dto.EventShortDto;
import ru.pracricum.ewmservice.event.mapper.EventMapper;
import ru.pracricum.ewmservice.event.model.Event;
import ru.pracricum.ewmservice.event.repository.EventRepository;
import ru.pracricum.ewmservice.event.service.EventService;
import ru.pracricum.ewmservice.exception.NotFoundException;
import ru.pracricum.ewmservice.util.PageRequestOverride;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final EventService eventService;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        PageRequestOverride pageRequest = PageRequestOverride.of(from, size);
        List<Compilation> compilations = compilationRepository.findByPinned(pinned, pageRequest);
        List<CompilationDto> compilationDtos = new ArrayList<>();
        CompilationDto compilationDto;
        for (Compilation compilation : compilations) {
            compilationDto = CompilationMapper.toCompilationDto(compilation);
            compilationDtos.add(compilationDto);
        }
        return compilationDtos;
        }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Подборки %s не существует.", compId)));

        CompilationDto compilationDto = CompilationMapper.toCompilationDto(compilation);

        return compilationDto;
    }

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        List<Event> eventList = eventService.getEventsByIds(compilationDto.getEvents());

        Compilation compilation = CompilationMapper.toCompilationNew(compilationDto);
        compilation.setEvents(eventList);
        Compilation compilationSave = compilationRepository.save(compilation);

        CompilationDto compilationDtoSave = CompilationMapper.toCompilationDto(compilationSave/*, eventList*/);
        compilationDtoSave.setEvents(compilation.getEvents());

        return compilationDtoSave;
    }

    @Override
    @Transactional
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Подборки %s не существует.", compId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
        List<Event> events = compilation.getEvents();
        if (events.contains(event)) {
            throw new NotFoundException(String.format("Событие %s уже находится в подборке %d.", eventId, compId));
        }
        events.add(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void fixCompilationOnMainPage(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Подборки %s не существует.", compId)));
        if (compilation.getPinned()) {
            throw new NotFoundException(String.format("Подборка %s уже находится на главной странице", compId));
        }
        compilation.setPinned(true);
    }

    @Override
    @Transactional
    public void deleteCompilationById(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public void deleteEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Подборки %s не существует.", compId)));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Событие %s не существует.", eventId)));
        List<Event> events = compilation.getEvents();
        if (!events.contains(event)) {
            throw new NotFoundException(String.format("Событие %s отсутствует в подборке %d.", eventId, compId));
        }

        events.remove(event);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilationOnMainPage(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Подборки %s не существует.", compId)));
        if (!compilation.getPinned()) {
            throw new NotFoundException(String.format("Подборка %s откреплена от главной страницы", compId));
        }
        compilation.setPinned(false);
    }
}
