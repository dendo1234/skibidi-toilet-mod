package com.gmail.dendocontato.skibidi.events;

import com.gmail.dendocontato.skibidi.SkibidiMod;
import com.gmail.dendocontato.skibidi.capabilities.SkibidiPlayerDataProvider;
import com.gmail.dendocontato.skibidi.capabilities.SkibidiType;
import com.gmail.dendocontato.skibidi.commands.SkibidiCommand;
import com.gmail.dendocontato.skibidi.networking.ModMessages;
import com.gmail.dendocontato.skibidi.networking.packet.SkibidiDataAskC2SPacket;
import com.gmail.dendocontato.skibidi.networking.packet.SkibidiDataSyncS2CPacket;
import com.gmail.dendocontato.skibidi.networking.packet.TestS2CPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = SkibidiMod.MODID)
public class ClientModEvents {
    @SubscribeEvent
    public static void AttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) {
            return;
        }

        if(!event.getObject().getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE).isPresent()) {
            event.addCapability(new ResourceLocation(SkibidiMod.MODID, "skibidi_player"), new SkibidiPlayerDataProvider());
        }
        
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE).ifPresent(oldCap -> {
                event.getEntity().getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE).ifPresent(newCap -> {
                    newCap.setSkibidiType(oldCap.getSkibidiType());
                });
            });
        }
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SkibidiCommand(event.getDispatcher());

        
        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerTracking(PlayerEvent.StartTracking event) {
        if (Minecraft.getInstance().getConnection() == null || !(event.getTarget() instanceof Player)) {
            return;
        }
        SkibidiMod.LOGGER.info("evento comecou");
        SkibidiMod.LOGGER.info("entity: " + event.getEntity().getName().getString());
        SkibidiMod.LOGGER.info("target: " + event.getTarget().getName().getString());
        event.getTarget().getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE).ifPresent(cap -> {
            ServerPlayer teoria = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayerByName(event.getEntity().getName().getString());


            ModMessages.sendToPlayersTracking(new SkibidiDataSyncS2CPacket(SkibidiType.TOILET, event.getTarget()), event.getEntity());
        });

    }

    
}