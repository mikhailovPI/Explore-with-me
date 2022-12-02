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

    public final static String TABLE_COMPILATION = "compilation";
    public final static String SCHEMA_TABLE = "public";
    public final static String COMPILATION_ID = "compilation_id";
    public final static String COMPILATION_PINNED = "pinned";
    public final static String COMPILATION_TITLE = "title";
    public final static String COMPILATION_COMPILATION_OF_EVENTS = "compilation_of_events";

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
