package com.gmail.dendocontato.skibidi.networking.packet;

import java.util.function.Supplier;

import com.gmail.dendocontato.skibidi.SkibidiMod;
import com.gmail.dendocontato.skibidi.capabilities.SkibidiPlayerDataProvider;
import com.gmail.dendocontato.skibidi.networking.ModMessages;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

//Package troll
public class SkibidiDataAskC2SPacket {
    private final int entityId;

    public SkibidiDataAskC2SPacket(int entityId) {
        this.entityId = entityId;
    }

    public SkibidiDataAskC2SPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            SkibidiMod.LOGGER.info("servidor");
            Entity targetPlayer = Minecraft.getInstance().level.getEntity(entityId);
            targetPlayer.getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE).ifPresent(cap -> {
                SkibidiMod.LOGGER.info(cap.getSkibidiType().name());
                ModMessages.sendToPlayer(new SkibidiDataSyncS2CPacket(cap.getSkibidiType(), targetPlayer), supplier.get().getSender());
            });
        }
        );

        return true;
    }
}