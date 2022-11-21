package ru.pracricum.ewmservice.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.service.CategoriesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@Slf4j
public class CategoriesPublicController {

    private final CategoriesService categoriesService;

    @GetMapping
    public List<CategoriesDto> getCategoryList (
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        return categoriesService.getCategoryList(from, size);
    }

    @GetMapping(path = "/{catId}")
    public CategoriesDto getCategoryById (@PathVariable Long catId) {
        return categoriesService.getCategoryById(catId);
    }
}
