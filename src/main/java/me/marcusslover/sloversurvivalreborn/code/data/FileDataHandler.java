package me.marcusslover.sloversurvivalreborn.code.data;

import me.marcusslover.sloversurvivalreborn.bank.BankAccountData;
import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.IHandler;
import me.marcusslover.sloversurvivalreborn.code.Init;
import me.marcusslover.sloversurvivalreborn.code.PatchVersion;
import me.marcusslover.sloversurvivalreborn.user.UserFileData;
import me.marcusslover.sloversurvivalreborn.warp.WarpFileData;

import java.util.List;

@PatchVersion(version = "2.0.0")
public class FileDataHandler implements ICodeInitializer, IHandler<IFileData<?>> {
    @Init
    private List<IFileData<?>> dataList;

    @Override
    public void initialize() {
        add(new UserFileData());
        add(new WarpFileData());
        add(BankAccountData.instance);
    }

    @Override
    public void add(IFileData<?> object) {
        dataList.add(object);
    }

    @Override
    public List<IFileData<?>> getRegistered() {
        return dataList;
    }
}
