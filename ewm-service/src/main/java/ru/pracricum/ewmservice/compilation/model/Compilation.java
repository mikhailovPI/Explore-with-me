package ru.pracricum.ewmservice.compilation.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.pracricum.ewmservice.event.model.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static ru.pracricum.ewmservice.compilation.model.Compilation.SCHEMA_TABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = Compilation.TABLE_COMPILATION, schema = SCHEMA_TABLE)
public class Compilation {

    public static final String TABLE_COMPILATION = "compilation";
    public static final String SCHEMA_TABLE = "public";
    public static final String COMPILATION_ID = "compilation_id";
    public static final String COMPILATION_PINNED = "pinned";
    public static final String COMPILATION_TITLE = "title";
    public static final String COMPILATION_COMPILATION_OF_EVENTS = "compilation_of_events";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COMPILATION_ID)
    Long id;

    @Column(name = COMPILATION_PINNED)
    Boolean pinned;

    @Column(name = COMPILATION_TITLE)
    String title;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = COMPILATION_COMPILATION_OF_EVENTS,
            joinColumns = @JoinColumn(name = "comp_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events = new ArrayList<>();
}
