package me.marcusslover.sloversurvivalreborn.code.data;

import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.IHandler;
import me.marcusslover.sloversurvivalreborn.code.Init;

import java.util.List;

public class FileDataHandler implements ICodeInitializer, IHandler<IFileData<?>> {
    @Init
    private List<IFileData<?>> dataList;

    @Override
    public void initialize() {
        add(new PlayerFileData());
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
