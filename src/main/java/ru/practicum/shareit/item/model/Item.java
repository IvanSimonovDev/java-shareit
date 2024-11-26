package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
public class Item {
    private long id;
    private long ownerId;
    private String name;
    private String description;
}
