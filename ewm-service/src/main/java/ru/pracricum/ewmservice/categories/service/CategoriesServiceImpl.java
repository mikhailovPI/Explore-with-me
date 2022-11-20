package ru.pracricum.ewmservice.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriesServiceImpl implements CategoriesService {

    @Override
    public List<CategoriesDto> getCategoryList() {
        return null;
    }

    @Override
    public CategoriesDto getCategoryById(Long catId) {
        return null;
    }

    @Override
    public CategoriesDto createCategory(CategoriesDto categoriesDto) {
        return null;
    }

    @Override
    public CategoriesDto patchCategory() {
        return null;
    }

    @Override
    public void deleteCategory(Long catId) {

    }
}
