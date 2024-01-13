package com.gmail.dendocontato.skibidi.networking.packet;

import java.util.function.Supplier;

import com.gmail.dendocontato.skibidi.SkibidiMod;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class TestS2CPacket {
    private final int inteiro;

    public TestS2CPacket(int inteiro) {
        this.inteiro = inteiro;
    }

    public TestS2CPacket(FriendlyByteBuf buf) {
        inteiro = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(inteiro);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        SkibidiMod.LOGGER.info("bobobobo" + inteiro);
        return true;
    }
}
