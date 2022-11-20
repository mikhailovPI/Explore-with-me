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

    @PostMapping(path = "/admin/categories")
    public CategoriesDto createCategory (CategoriesDto categoriesDto) {
        return categoriesService.createCategory(categoriesDto);
    }

    @PatchMapping(path = "/admin/categories")
    public CategoriesDto patchCategory () {
        return categoriesService.patchCategory();
    }

    @DeleteMapping(path = "/admin/categories/{catId}")
    public void deleteCategory (@PathVariable Long catId) {
        categoriesService.deleteCategory(catId);
    }
}
