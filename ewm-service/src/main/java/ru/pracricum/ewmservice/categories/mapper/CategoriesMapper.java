package ru.pracricum.ewmservice.categories.mapper;

import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.dto.NewCategoriesDto;
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

    public static NewCategoriesDto toNewCategoryDto (Categories categories) {
        return new NewCategoriesDto(
          categories.getName());
    }

    public static Categories toNewCategory (NewCategoriesDto newCategoriesDto) {
        return new Categories(
                null,
                newCategoriesDto.getName());
    }
}
