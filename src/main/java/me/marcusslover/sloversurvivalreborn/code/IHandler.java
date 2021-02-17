package me.marcusslover.sloversurvivalreborn.code;

import java.util.List;

public interface IHandler<T> {
    void add(T object);

    List<T> getRegistered();
}
