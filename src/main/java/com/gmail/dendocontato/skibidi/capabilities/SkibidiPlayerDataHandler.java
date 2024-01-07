package com.gmail.dendocontato.skibidi.capabilities;

import net.minecraft.nbt.CompoundTag;


public class SkibidiPlayerDataHandler implements ISkibidiPlayerData {

    protected SkibidiType skibidiPlayerData;

    public SkibidiPlayerDataHandler() {
        this(SkibidiType.NONE);
    }

    public SkibidiPlayerDataHandler(SkibidiType arg) {
        skibidiPlayerData = arg;
    }

    @Override
    public SkibidiType getSkibidiType() {
        return skibidiPlayerData;
    }

    @Override
    public void setSkibidiType(SkibidiType arg) {
        skibidiPlayerData = arg;
    }


    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("skibidiType", skibidiPlayerData.name());
        return nbt;
    }


    public void deserializeNBT(CompoundTag nbt) {
        skibidiPlayerData = SkibidiType.valueOf(nbt.getString("skibidiType"));
    }

    
}
