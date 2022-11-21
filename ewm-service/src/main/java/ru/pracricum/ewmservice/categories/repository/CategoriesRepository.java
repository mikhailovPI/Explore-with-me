package ru.pracricum.ewmservice.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pracricum.ewmservice.categories.model.Categories;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    @Query("select c.name from Categories c")
    List<String> findByNameOrderByName();
}
