package com.gmail.dendocontato.skibidi.capabilities;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;



@AutoRegisterCapability
public interface ISkibidiPlayerData {

    public SkibidiType getSkibidiType();

    public void setSkibidiType(SkibidiType arg);
}