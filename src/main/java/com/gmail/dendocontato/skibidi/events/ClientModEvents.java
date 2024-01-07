package com.gmail.dendocontato.skibidi.events;

import com.gmail.dendocontato.skibidi.ExampleMod;
import com.gmail.dendocontato.skibidi.capabilities.SkibidiPlayerDataProvider;
import com.gmail.dendocontato.skibidi.commands.SkibidiCommand;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID)
public class ClientModEvents {
    @SubscribeEvent
    public static void AttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player)) {
            return;
        }

        if(!event.getObject().getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE).isPresent()) {
            event.addCapability(new ResourceLocation(ExampleMod.MODID, "skibidi_player"), new SkibidiPlayerDataProvider());
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
    
}