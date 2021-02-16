package me.marcusslover.sloversurvivalreborn.utils;

import java.util.List;

public interface IHandler<T> {
    void add(T object);
    List<T> getRegistered();
}
