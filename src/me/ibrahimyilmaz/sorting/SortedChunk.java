package me.ibrahimyilmaz.sorting;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

class SortedChunk {

    private List<Integer> items;

    private String uniqueId;

    private SortedChunk(List<Integer> items) {
        this.uniqueId = UUID.randomUUID().toString();
        this.items = items;
        Collections.sort(this.items);
    }

    public static SortedChunk from(List<Integer> integers) {
        return new SortedChunk(integers);
    }

    public List<Integer> getItems() {
        return items;
    }

    public String getId() {
        return uniqueId;
    }
}
