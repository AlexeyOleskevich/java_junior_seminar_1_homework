package ru.geekbrains.junior.lesson1.task2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Корзина
 * @param <T> Еда
 */
public class Cart<T extends Food> {

    //region Поля

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    //endregion

    //region Конструкторы

    /**
     * Создание нового экземпляра корзины
     * @param market принадлежность к магазину
     */
    public Cart(Class<T> clazz, UMarket market)
    {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    //endregion

    /**
     * Балансировка корзины
     */
    public void cardBalancing()
    {
        final boolean[] proteins = {foodstuffs.stream().anyMatch(Food::getProteins)};
        final boolean[] fats = {foodstuffs.stream().anyMatch(Food::getFats)};
        final boolean[] carbohydrates = {foodstuffs.stream().anyMatch(Food::getCarbohydrates)};

        if (proteins[0] && fats[0] && carbohydrates[0])
        {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
            return;
        }

        Collection<T> balancedThings = market.getThings(clazz).stream()
                .filter(thing -> (!proteins[0] && thing.getProteins()) ||
                        (!fats[0] && thing.getFats()) ||
                        (!carbohydrates[0] && thing.getCarbohydrates()))
                .peek(thing -> {
                    if (!proteins[0] && thing.getProteins()) {
                        proteins[0] = true;
                    } else if (!fats[0] && thing.getFats()) {
                        fats[0] = true;
                    } else if (!carbohydrates[0] && thing.getCarbohydrates()) {
                        carbohydrates[0] = true;
                    }
                }).toList();

        foodstuffs.addAll(balancedThings);

        if (proteins[0] && fats[0] && carbohydrates[0])
            System.out.println("Корзина сбалансирована по БЖУ.");
        else
            System.out.println("Невозможно сбалансировать корзину по БЖУ.");
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs(){
        /*int index = 1;
        for (var food : foodstuffs)
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n", index++, food.getName(), food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет", food.getCarbohydrates() ? "Да" : "Нет");
         */
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                index.getAndIncrement(), food.getName(),
                food.getProteins() ? "Да" : "Нет",
                food.getFats() ? "Да" : "Нет",
                food.getCarbohydrates() ? "Да" : "Нет"));

    }

}
