package ru.practicum.statsserver.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EventViews {
    private List<ViewStats> views;
}