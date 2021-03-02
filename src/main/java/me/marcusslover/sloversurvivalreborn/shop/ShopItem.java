package me.marcusslover.sloversurvivalreborn.shop;

import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;
import org.bukkit.entity.Entity;

import java.math.BigDecimal;

public interface ShopItem {
    long stockCount();

    boolean canBeBought();
    BigDecimal buyPrice();
    boolean canBeSold();

    BigDecimal sellPrice();

    boolean canAccessStock(Entity entity);
    BankAccount<?> getHolderAccount();
}
