package ru.pracricum.ewmservice.event.mapper;

import ru.pracricum.ewmservice.event.dto.LocationDto;
import ru.pracricum.ewmservice.event.model.Location;

public class LocationMapper {

    public static Location toLocation (LocationDto locationDto) {
        return new Location(
                locationDto.getLap(),
                locationDto.getLon());
    }

    public static LocationDto toLocation (Location location) {
        return new LocationDto(
                location.getLap(),
                location.getLon());
    }
}