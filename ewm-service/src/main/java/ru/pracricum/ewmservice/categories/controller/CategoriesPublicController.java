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
    public List<CategoriesDto> getCategoryList(
            @RequestParam(name = "from", defaultValue = "0") int from,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        log.info("URL: /categories. GetMapping/Получение всех категорий/getCategoryList");
        return categoriesService.getCategoryList(from, size);
    }

    @GetMapping(path = "/{categoryId}")
    public CategoriesDto getCategoryById(@PathVariable Long categoryId) {
        log.info("URL: /categories/{categoryId}. GetMapping/Получение категории по id " + categoryId
                + "/getCategoryById");
        return categoriesService.getCategoryById(categoryId);
    }
}
