package com.gmail.dendocontato.skibidi.networking.packet;

import java.util.function.Supplier;

import com.gmail.dendocontato.skibidi.capabilities.SkibidiPlayerDataProvider;
import com.gmail.dendocontato.skibidi.capabilities.SkibidiType;

import ca.weblite.objc.Client;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class SkibidiDataSyncS2CPacket {
    private final SkibidiType skibidiType;
    private final Entity player;

    public SkibidiDataSyncS2CPacket(SkibidiType skibidiType, Entity entity) {
        this.skibidiType = skibidiType;
        this.player = entity;

    }
    
    public SkibidiDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.skibidiType = buf.readEnum(SkibidiType.class);
        this.player = Minecraft.getInstance().level.getEntity(buf.readInt());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeEnum(skibidiType);
        buf.writeInt(player.getId());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            player.getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE).ifPresent(cap -> {
                cap.setSkibidiType(skibidiType);
            });
        });
        return true;
    }
}
