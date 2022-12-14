package ru.pracricum.ewmservice.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.pracricum.ewmservice.util.PageRequestOverride;
import ru.pracricum.ewmservice.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query ("select u from User u where u.id = ?1 order by u.id")
    List<User> getByIdOrderByIdAsc (List<Long> id, PageRequestOverride page);

    @Query("select c.name from User c")
    List<String> findByNameOrderByName();

}
