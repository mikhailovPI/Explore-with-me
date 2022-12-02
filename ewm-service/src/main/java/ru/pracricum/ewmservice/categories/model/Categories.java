package ru.pracricum.ewmservice.categories.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static ru.pracricum.ewmservice.categories.model.Categories.SCHEMA_TABLE;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = Categories.TABLE_CATEGORIES, schema = SCHEMA_TABLE)
public class Categories {

    public final static String TABLE_CATEGORIES = "categories";
    public final static String SCHEMA_TABLE = "public";
    public final static String CATEGORIES_ID = "category_id";
    public final static String CATEGORIES_NAME = "category_name";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CATEGORIES_ID)
    Long id;

    @Column(name = CATEGORIES_NAME)
    String name;

}
