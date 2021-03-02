package me.marcusslover.sloversurvivalreborn.shop;

import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;
import me.marcusslover.sloversurvivalreborn.bank.accounts.PlayerBankAccount;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;

public class Barrel {
    public Block block;

    public class BarrelShopItem implements ShopItem {
        Container block;
        ItemStack target;
        boolean canBeBought;
        boolean canBeSold;
        PlayerBankAccount account;

        @Override
        public long stockCount() {
            long count = 0;
            for (ItemStack item : block.getInventory().getStorageContents()) {
                if (item.getType() == target.getType()) {
                    count++;
                }
            }
            return count;
        }

        @Override
        public boolean canBeBought() {
            return false;
        }

        @Override
        public BigDecimal buyPrice() {
            return null;
        }

        @Override
        public boolean canBeSold() {
            return false;
        }

        @Override
        public BigDecimal sellPrice() {
            return null;
        }

        @Override
        public boolean canAccessStock(Entity entity) {
            if (entity instanceof Player && entity.getUniqueId() == account.getAccountId()) {
                return true;
            }
            return false;
        }

        @Override
        public PlayerBankAccount getHolderAccount() {
            return null;
        }
    }
}
