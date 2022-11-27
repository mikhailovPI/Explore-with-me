package ru.pracricum.ewmservice.categories.mapper;

import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.model.Categories;

public class CategoriesMapper {

    public static Categories toCategory(CategoriesDto categoryDto) {
        return new Categories(
                categoryDto.getId(),
                categoryDto.getName());
    }

    public static CategoriesDto toCategoryDto(Categories categories) {
        return new CategoriesDto(
                categories.getId(),
                categories.getName());
    }
}
