package ru.practicum.shareit.item;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private long id;

    private long authorId;

    private long itemId;

    private String text;

    private LocalDateTime created;
}