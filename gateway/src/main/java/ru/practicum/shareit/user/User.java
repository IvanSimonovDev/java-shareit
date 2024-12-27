package ru.practicum.shareit.user;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;

    private String name;

    private String email;
}
