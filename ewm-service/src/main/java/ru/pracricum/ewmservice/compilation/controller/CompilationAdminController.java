package ru.pracricum.ewmservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.compilation.dto.CompilationDto;
import ru.pracricum.ewmservice.compilation.dto.NewCompilationDto;
import ru.pracricum.ewmservice.compilation.service.CompilationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
@Slf4j
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto createCompilation(@RequestBody NewCompilationDto compilationDto) {
        log.info("URL: /admin/compilations. PostMapping/Создание подборки событий " + compilationDto);
        return compilationService.createCompilation(compilationDto);
    }

    @PatchMapping(path = "/{compId}/events/{eventId}")
    public void addEventToCompilation(
            @PathVariable Long compId,
            @PathVariable Long eventId) {
        log.info("URL: /admin/compilations/{compId}/events/{eventId}. PatchMapping/Добавление события " + eventId
                + " в  подборку " + compId + "/addEventToCompilation");
        compilationService.addEventToCompilation(compId, eventId);
    }

    @PatchMapping(path = "/{compId}/pin")
    public void fixCompilationOnMainPage(@PathVariable Long compId) {
        log.info("URL: /admin/compilations/{compId}/events/{eventId}. PatchMapping/Закрепить подборку " + compId
                + " на главной странице/fixCompilationOnMainPage");
        compilationService.fixCompilationOnMainPage(compId);
    }

    @DeleteMapping(path = "/{compId}")
    public void deleteCompilationById(@PathVariable Long compId) {
        log.info("URL: /admin/compilations/{compId}/events/{eventId}. DeleteMapping/Удаление подборки " + compId +
                "/deleteCompilationById");
        compilationService.deleteCompilationById(compId);
    }

    @DeleteMapping(path = "/{compId}/events/{eventId}")
    public void deleteEventToCompilation(
            @PathVariable Long compId,
            @PathVariable Long eventId) {
        log.info("URL: /admin/compilations/{compId}/events/{eventId}. DeleteMapping/Удаление события " + eventId
                + " из подборки " + compId + "/deleteEventToCompilation");
        compilationService.deleteEventToCompilation(compId, eventId);
    }

    @DeleteMapping(path = "/{compId}/pin")
    public void deleteCompilationOnMainPage(@PathVariable Long compId) {
        log.info("URL: /admin/compilations/{compId}/events/{eventId}. DeleteMapping/Открепить подборку " + compId
                + " с главной страницы/deleteCompilationOnMainPage");
        compilationService.deleteCompilationOnMainPage(compId);
    }
}
