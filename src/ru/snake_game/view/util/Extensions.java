package ru.snake_game.view.util;

import java.util.Collection;
import java.util.Random;

public class Extensions {
    private static Random random = new Random();

    private Extensions() {
    }

    public static <T> T getRandomItem(Collection<T> collection) {
        int size = collection.size();
        int item = random.nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for (T obj : collection) {
            if (i == item)
                return obj;
            i++;
        }
        return null;
    }
}
