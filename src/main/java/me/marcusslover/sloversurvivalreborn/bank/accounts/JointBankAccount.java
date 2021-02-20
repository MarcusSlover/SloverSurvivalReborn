package me.marcusslover.sloversurvivalreborn.bank.accounts;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JointBankAccount extends BankAccount<JointBankAccount> {
    public static final String TYPE = "joint";
    private UUID accountId;
    private List<UUID> parties;

    public JointBankAccount(UUID id) {
        super(id);
        this.accountId = id;
        this.parties = new ArrayList<>();
    }

    public void addParty(UUID uuid) {
        parties.add(uuid);
    }

    public void removeParty(UUID uuid) {
        parties.remove(uuid);
    }

    public List<UUID> getParties() {
        return parties;
    }

    @Override
    public UUID getAccountId() {
        return accountId;
    }

    @Override
    public void load(JsonObject object) {
        super.load(object);
        this.accountId = UUID.fromString(object.get("id").getAsString());
        JsonArray parties = object.get("parties").getAsJsonArray();
        for (int i = 0; i < parties.size(); i++) {
            this.parties.add(UUID.fromString(parties.get(i).getAsString()));
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        JsonArray parties = new JsonArray();
        for (UUID party : this.parties) {
            parties.set(parties.size(), new JsonPrimitive(party.toString()));
        }
        obj.add("parties", parties);
        return obj;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
