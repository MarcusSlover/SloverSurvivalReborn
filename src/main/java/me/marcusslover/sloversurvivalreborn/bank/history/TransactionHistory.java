package me.marcusslover.sloversurvivalreborn.bank.history;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.Bank;
import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;
import me.marcusslover.sloversurvivalreborn.code.data.IJsonable;
import me.marcusslover.sloversurvivalreborn.utils.HashUtil;
import me.marcusslover.sloversurvivalreborn.utils.StringUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionHistory implements IJsonable<JsonArray> {
    protected List<ITransaction> transactions = new ArrayList<>();

    public TransactionHistory() {
    }

    public void addTransaction(ITransaction transaction) {
        String content;
        switch (transaction.getType()) {
            case TransferTransaction.TYPE:
                TransferTransaction transfer = (TransferTransaction) transaction;
                BankAccount<?> from = Bank.loadBankAccount(transfer.getFrom());
                BankAccount<?> to = Bank.loadBankAccount(transfer.getTo());
                String fromStr = from.getAccountId().toString() + from.getHistory().getTransactionCount();
                String toStr = to.getAccountId().toString() + to.getHistory().getTransactionCount();
                content = fromStr + "->" + toStr + ":" + transfer.getAmount().toString();
                break;

            case TaxTransaction.TYPE:
                TaxTransaction tax = (TaxTransaction) transaction;
                BankAccount<?> account = Bank.loadBankAccount(tax.getAccount());
                String accountStr = account.getAccountId().toString() + account.getHistory().getTransactionCount();
                content = accountStr + "%" + tax.getAmount();
                break;

            default:
                throw new RuntimeException("Invalid transaction type; must be able to parse transaction ID.");
        }
        transaction.setTransactionId(StringUtil.hexadecimal(HashUtil.sha256(content.getBytes())));
        transactions.add(transaction);
    }

    private int getTransactionCount() {
        return transactions.size();
    }

    @Override
    public JsonArray toJson() {
        JsonArray arr = new JsonArray();
        for (ITransaction transaction : transactions) {
            JsonObject obj = transaction.toJson();
            obj.addProperty("type", transaction.getType());
            obj.addProperty("id", transaction.getTransactionId());
            arr.add(obj);
        }
        return arr;
    }

    @Override
    public void load(JsonArray arr) {
        for (JsonElement el : arr) {
            JsonObject obj = el.getAsJsonObject();
            ITransaction transaction = null;
            switch (obj.get("type").getAsString()) {
                case TransferTransaction.TYPE:
                    transaction = new TransferTransaction(null, null, null);
            }
            if (transaction != null) {
                transaction.load(obj);
                transaction.setTransactionId(obj.get("id").getAsString());
            }
            transactions.add(transaction);
        }
    }

    public static TransactionHistory compileHistory(Predicate<ITransaction> filter, TransactionHistory... histories) {
        TransactionHistory filteredHistory = new TransactionHistory();
        for (TransactionHistory history : histories) {
            List<ITransaction> transactions = history.transactions.stream()
                    .filter(filter)
                    .collect(Collectors.toList());
            filteredHistory.transactions.addAll(transactions);
        }
        filteredHistory.transactions.sort(Comparator.comparingLong(ITransaction::getTimestamp));
        return filteredHistory;
    }
}
