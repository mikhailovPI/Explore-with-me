package ru.pracricum.ewmservice.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.service.CategoriesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@Slf4j
public class CategoriesPublicController {

    private final CategoriesService categoriesService;

    @GetMapping(path = "/categories")
    public List<CategoriesDto> getCategoryList () {
        return categoriesService.getCategoryList();
    }

    @GetMapping(path = "/categories/{catId}")
    public CategoriesDto getCategoryById (@PathVariable Long catId) {
        return categoriesService.getCategoryById(catId);
    }
}
