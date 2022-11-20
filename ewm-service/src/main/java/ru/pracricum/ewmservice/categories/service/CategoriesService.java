package ru.pracricum.ewmservice.categories.service;

import org.springframework.stereotype.Service;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;

import java.util.List;

@Service
public interface CategoriesService {
    List<CategoriesDto> getCategoryList();

    CategoriesDto getCategoryById(Long catId);

    CategoriesDto createCategory(CategoriesDto categoriesDto);

    CategoriesDto patchCategory();

    void deleteCategory(Long catId);
}
