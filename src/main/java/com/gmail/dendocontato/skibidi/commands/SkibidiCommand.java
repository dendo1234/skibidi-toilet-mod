package com.gmail.dendocontato.skibidi.commands;

import java.util.Collection;

import com.gmail.dendocontato.skibidi.capabilities.SkibidiPlayerDataProvider;
import com.gmail.dendocontato.skibidi.capabilities.SkibidiType;
import com.gmail.dendocontato.skibidi.networking.ModMessages;
import com.gmail.dendocontato.skibidi.networking.packet.SkibidiDataSyncS2CPacket;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

public class SkibidiCommand {
    public SkibidiCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skibidi")
        .then(Commands.literal("none")
            .then(Commands.argument("target", EntityArgument.players())
                .executes((command) -> {
                    return skibidiSet(command, EntityArgument.getPlayers(command, "target"), "none");
                }
                )
            )
        )
        .then(Commands.literal("toilet")
            .then(Commands.argument("target", EntityArgument.players())
                .executes((command) -> {
                    return skibidiSet(command, EntityArgument.getPlayers(command, "target"), "toilet");
                }
                )
            )
        )
        .then(Commands.literal("camera")
            .then(Commands.argument("target", EntityArgument.players())
                .executes((command) -> {
                    return skibidiSet(command, EntityArgument.getPlayers(command, "target"), "camera");
                }
                )
            )
        )
        );
    }

    private int skibidiSet(CommandContext<CommandSourceStack> source, Collection<ServerPlayer> teste, String skibidi) throws CommandSyntaxException {

        for(ServerPlayer serverplayer : teste) {
            serverplayer.getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE).ifPresent(cap -> {
                cap.setSkibidiType(SkibidiType.byName(skibidi));
            });
            ModMessages.sendToAllPlayers(new SkibidiDataSyncS2CPacket(SkibidiType.byName(skibidi), serverplayer));
        }

        


        return 1;
    }

    
}
