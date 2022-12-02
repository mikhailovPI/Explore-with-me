package ru.pracricum.ewmservice.categories.service;

import org.springframework.stereotype.Service;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;

import java.util.List;

@Service
public interface CategoriesService {
    List<CategoriesDto> getCategoryList(int from, int size);

    CategoriesDto getCategoryById(Long categoryId);

    CategoriesDto createCategory(CategoriesDto categoriesDto);

    CategoriesDto patchCategory(CategoriesDto categoriesDto);

    void deleteCategoryById(Long categoryId);
}
