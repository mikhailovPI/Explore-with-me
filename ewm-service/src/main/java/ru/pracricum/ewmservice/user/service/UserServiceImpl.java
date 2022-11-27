package ru.pracricum.ewmservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pracricum.ewmservice.user.dto.NewUserRequest;
import ru.pracricum.ewmservice.util.PageRequestOverride;
import ru.pracricum.ewmservice.exception.ConflictingRequestException;
import ru.pracricum.ewmservice.exception.NotFoundException;
import ru.pracricum.ewmservice.exception.ValidationException;
import ru.pracricum.ewmservice.user.dto.UserDto;
import ru.pracricum.ewmservice.user.mapper.UserMapper;
import ru.pracricum.ewmservice.user.model.User;
import ru.pracricum.ewmservice.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getUsersList(List<Long> ids, int from, int size) {
        PageRequestOverride pageRequest = PageRequestOverride.of(from, size);
        if (!ids.isEmpty()) {
            return userRepository.getByIdOrderByIdAsc(ids, pageRequest)
                    .stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAll(pageRequest)
                    .stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest userDto) {
        if (userDto.getEmail() == null) {
            throw new ValidationException("E-mail не должен быть пустым.");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new ValidationException("Введен некорректный e-mail.");
        }
        if (userDto.getName() == null) {
            throw new ValidationException("Name не должен быть пустым.");
        }
        userRepository.findByNameOrderByName()
                .stream()
                .filter(name -> name.equals(userDto.getName()))
                .forEachOrdered(name -> {
                    throw new ConflictingRequestException(
                            String.format("Пользователь с именем %s - уже существует", name));
                });
        User userSave = userRepository.save(UserMapper.toUserNew(userDto));
        return UserMapper.toUserDto(userSave);
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Пользователь %s не существует.", userId)));
        userRepository.deleteById(userId);
    }
}
