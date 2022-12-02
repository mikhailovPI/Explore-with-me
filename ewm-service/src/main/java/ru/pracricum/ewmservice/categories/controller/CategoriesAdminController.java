package ru.pracricum.ewmservice.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.service.CategoriesService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
@Slf4j
public class CategoriesAdminController {

    private final CategoriesService categoriesService;

    @PostMapping
    public CategoriesDto createCategory(@RequestBody CategoriesDto categoriesDto) {
        log.info("URL: /admin/categories. PostMapping/Создание категории/createCategory");
        return categoriesService.createCategory(categoriesDto);
    }

    @PatchMapping
    public CategoriesDto patchCategory(@RequestBody CategoriesDto categoriesDto) {
        log.info("URL: /admin/categories. PatchMapping/Изменение категории/patchCategory");
        return categoriesService.patchCategory(categoriesDto);
    }

    @DeleteMapping(path = "/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        log.info("URL: /admin/categories/{categoryId}. DeleteMapping/Удаление категории/deleteCategory");
        categoriesService.deleteCategoryById(categoryId);
    }
}