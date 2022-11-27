package ru.pracricum.ewmservice.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pracricum.ewmservice.categories.dto.CategoriesDto;
import ru.pracricum.ewmservice.categories.mapper.CategoriesMapper;
import ru.pracricum.ewmservice.categories.model.Categories;
import ru.pracricum.ewmservice.categories.repository.CategoriesRepository;
import ru.pracricum.ewmservice.exception.ConflictingRequestException;
import ru.pracricum.ewmservice.exception.NotExistObjectException;
import ru.pracricum.ewmservice.exception.NotFoundException;
import ru.pracricum.ewmservice.util.PageRequestOverride;

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
        Categories categories = validationCategories(catId);
        return CategoriesMapper.toCategoryDto(categories);
    }

    @Override
    @Transactional
    public CategoriesDto createCategory(CategoriesDto categoriesDto) {
        validationBodyCategories(categoriesDto);
        categoriesRepository.findByNameOrderByName()
                .stream()
                .filter(name -> name.equals(categoriesDto.getName())).forEachOrdered(name -> {
                    throw new ConflictingRequestException(
                            String.format("Категрия с названием %s - уже существует", name));
                });
        Categories categories = CategoriesMapper.toCategory(categoriesDto);
        Categories categoriesSave = categoriesRepository.save(categories);
        return CategoriesMapper.toCategoryDto(categoriesSave);
    }

    @Override
    @Transactional
    public CategoriesDto patchCategory(CategoriesDto categoriesDto) {
        validationBodyCategories(categoriesDto);
        categoriesRepository.findByNameOrderByName()
                .stream()
                .filter(name -> name.equals(categoriesDto.getName())).forEachOrdered(name -> {
                    throw new ConflictingRequestException(
                            String.format("Категрия с названием %s - уже существует", name));
                });
        Categories categories = CategoriesMapper.toCategory(categoriesDto);
        Categories categoriesUpdate = validationCategories(categories.getId());
        categoriesUpdate.setName(categories.getName());

        return CategoriesMapper.toCategoryDto(categoriesUpdate);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        categoriesRepository.deleteById(catId);
    }

    private Categories validationCategories(Long catId) {
        return categoriesRepository.findById(catId)
                .orElseThrow(() -> new NotExistObjectException(
                        String.format("Категория %s не существует.", catId)));
    }

    private void validationBodyCategories(CategoriesDto categoriesDto) {
        if (categoriesDto.getName() == null) {
            throw new NotFoundException("Название категории не может быть пустым");
        }
    }
}