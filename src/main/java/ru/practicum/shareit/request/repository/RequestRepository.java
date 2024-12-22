package ru.practicum.shareit.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.model.ItemRequestShort;

import java.util.List;

public interface RequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequestShort> findByCreatorIdOrderByCreatedDesc(long creatorId);

    List<ItemRequestShort> findByCreatorIdNotOrderByCreatedDesc(long creatorId);

    List<ItemRequestShort> findByIdIn(List<Long> ids);
}
