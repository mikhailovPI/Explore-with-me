package ru.pracricum.ewmservice.compilation.mapper;

import ru.pracricum.ewmservice.compilation.dto.CompilationDto;
import ru.pracricum.ewmservice.compilation.dto.NewCompilationDto;
import ru.pracricum.ewmservice.compilation.model.Compilation;

import java.util.ArrayList;

public class CompilationMapper {

    public static Compilation toCompilation(CompilationDto compilationDto) {
        return new Compilation(
                compilationDto.getId(),
                compilationDto.getPinned(),
                compilationDto.getTitle());
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                null,
                compilation.getPinned(),
                compilation.getTitle());
    }

    public static Compilation toCompilationNew (NewCompilationDto newCompilationDto) {
        return new Compilation(
                null,
                newCompilationDto.getPinned(),
                newCompilationDto.getTitle());
    }

    public static NewCompilationDto toNewCompilationDto (Compilation compilation) {
        return new NewCompilationDto(
                null,
                compilation.getPinned(),
                compilation.getTitle());
    }
}
