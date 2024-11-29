package ru.practicum.shareit.item.repository;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class InMemoryItemRepository implements ItemRepository {
    private Map<Long, Item> storage = new HashMap<>();
    private long currentMaxId = 0;

    public Item create(Item item) {
        storage.put(++currentMaxId, item.withId(currentMaxId));
        return storage.get(currentMaxId);
    }

    public Item update(Item item) {
        long itemId = item.getId();
        storage.put(itemId, item);
        return get(itemId);
    }

    public Item get(long id) {
        return storage.get(id);
    }

    public List<Item> getAll() {
        return new ArrayList<>(storage.values());
    }

    public List<Item> getAll(long userId) {
        return filterItems(item -> (item.getOwnerId() != userId), getAll());
    }

    public List<Item> getAllAvailable() {
        return filterItems(item -> !item.getAvailable(), getAll());
    }

    public List<Item> searchAvailableItems(String text) {
        String textLowerCase = text.toLowerCase();
        Function<Item, Item> itemNameAndDescToLowerCase = item -> {
            item.setName(item.getName().toLowerCase());
            item.setDescription(item.getDescription().toLowerCase());
            return item;
        };
        List<Item> allAvailableItemsWithNameAndDescToLowerCase = getAllAvailable()
                .stream()
                .map(itemNameAndDescToLowerCase)
                .toList();

        Predicate<Item> filterStatement =
                item -> !(item.getName().contains(textLowerCase) || item.getDescription().contains(textLowerCase));
        return filterItems(filterStatement, allAvailableItemsWithNameAndDescToLowerCase);
    }

    private List<Item> filterItems(Predicate<Item> predicate, List<Item> items) {
        List<Item> copyToProcess = new ArrayList<>(items);
        copyToProcess.removeIf(predicate);
        return copyToProcess;
    }

    public void remove(long id) {
        storage.remove(id);
    }
}
