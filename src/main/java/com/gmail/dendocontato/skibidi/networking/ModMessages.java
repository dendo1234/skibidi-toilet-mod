package com.gmail.dendocontato.skibidi.networking;

import com.gmail.dendocontato.skibidi.SkibidiMod;
import com.gmail.dendocontato.skibidi.networking.packet.SkibidiDataAskC2SPacket;
import com.gmail.dendocontato.skibidi.networking.packet.SkibidiDataSyncS2CPacket;
import com.gmail.dendocontato.skibidi.networking.packet.TestS2CPacket;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
           .named(new ResourceLocation(SkibidiMod.MODID, "messages"))
           .networkProtocolVersion(() -> "1.0")
           .clientAcceptedVersions(s -> true)
           .serverAcceptedVersions(s -> true)
           .simpleChannel();

           INSTANCE = net;

           net.messageBuilder(SkibidiDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
           .decoder(SkibidiDataSyncS2CPacket::new)
           .encoder(SkibidiDataSyncS2CPacket::toBytes)
           .consumerMainThread(SkibidiDataSyncS2CPacket::handle)
           .add();

           net.messageBuilder(SkibidiDataAskC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
           .decoder(SkibidiDataAskC2SPacket::new)
           .encoder(SkibidiDataAskC2SPacket::toBytes)
           .consumerMainThread(SkibidiDataAskC2SPacket::handle)
           .add();

           net.messageBuilder(TestS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
           .decoder(TestS2CPacket::new)
           .encoder(TestS2CPacket::toBytes)
           .consumerMainThread(TestS2CPacket::handle)
           .add();
    }
    
    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayersTracking(MSG message, Entity entity) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAllPlayers(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
