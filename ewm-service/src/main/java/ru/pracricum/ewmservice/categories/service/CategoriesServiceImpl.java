package ru.pracricum.ewmservice.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pracricum.ewmservice.PageRequestOverride;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.mapper.CategoriesMapper;
import ru.pracricum.ewmservice.categories.model.Categories;
import ru.pracricum.ewmservice.categories.repository.CategoriesRepository;
import ru.pracricum.ewmservice.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Override
    public List<CategoriesDto> getCategoryList(int from, int size) {
        PageRequestOverride pageRequest = PageRequestOverride.of(from, size);
        return categoriesRepository.findAll(pageRequest)
                .stream()
                .map(CategoriesMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriesDto getCategoryById(Long catId) {
        Categories categories = categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория %s не существует.", catId)));
        return CategoriesMapper.toCategoryDto(categories);
    }

    @Override
    @Transactional
    public CategoriesDto createCategory(CategoriesDto categoriesDto) {
        Categories categories = CategoriesMapper.toCategory(categoriesDto);
        Categories categoriesSave = categoriesRepository.save(categories);
        return CategoriesMapper.toCategoryDto(categoriesSave);
    }

    @Override
    @Transactional
    public CategoriesDto patchCategory(CategoriesDto categoriesDto) {
        Categories categories = CategoriesMapper.toCategory(categoriesDto);
        Categories categoriesUpdate = categoriesRepository.findById(categories.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория %s не существует.", categoriesDto.getId())));
        categoriesUpdate.setName(categories.getName());
        return CategoriesMapper.toCategoryDto(categoriesUpdate);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория %s не существует.", catId)));
        categoriesRepository.deleteById(catId);
    }
}