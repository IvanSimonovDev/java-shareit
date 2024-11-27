package ru.practicum.shareit.item.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
public class Item {
    private @With long id;
    private long ownerId;
    private String name;
    private String description;
    private Boolean available;
}
