package ru.pracricum.ewmservice.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pracricum.ewmservice.categories.model.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
