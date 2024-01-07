package com.gmail.dendocontato.skibidi.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class SkibidiPlayerDataProvider implements ICapabilitySerializable<CompoundTag> {

    public static final Capability<ISkibidiPlayerData> SKIBIDY_TYPE = CapabilityManager.get(new CapabilityToken<ISkibidiPlayerData>() {});

    private SkibidiPlayerDataHandler skibidiPlayerDataHandler = null;
    private LazyOptional<ISkibidiPlayerData> optional = LazyOptional.of(this::getSkibidiPlayerDataHandler);

    private SkibidiPlayerDataHandler getSkibidiPlayerDataHandler() {
        if (skibidiPlayerDataHandler == null) {
            this.skibidiPlayerDataHandler = new SkibidiPlayerDataHandler();
        }
        return skibidiPlayerDataHandler;
    }


    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == SKIBIDY_TYPE) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        return getSkibidiPlayerDataHandler().serializeNBT();
    }


    @Override
    public void deserializeNBT(CompoundTag nbt) {
        getSkibidiPlayerDataHandler().deserializeNBT(nbt);
    }
    
}
