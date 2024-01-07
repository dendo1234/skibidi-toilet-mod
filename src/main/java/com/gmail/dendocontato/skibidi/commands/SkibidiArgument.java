package com.gmail.dendocontato.skibidi.commands;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import com.gmail.dendocontato.skibidi.capabilities.SkibidiType;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;

public class SkibidiArgument implements ArgumentType<SkibidiType> {
    private static final SkibidiType[] VALUES = SkibidiType.values();

    @Override
    public SkibidiType parse(StringReader reader) throws CommandSyntaxException {
        return SkibidiType.valueOf(reader.getString());
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> p_259767_, SuggestionsBuilder p_259515_) {
      return p_259767_.getSource() instanceof SharedSuggestionProvider ? SharedSuggestionProvider.suggest(Arrays.stream(VALUES).map(SkibidiType::getName), p_259515_) : Suggestions.empty();
    }

    public static SkibidiType getSkibidiType(CommandContext<CommandSourceStack> p_259927_, String p_260246_) throws CommandSyntaxException {
      return p_259927_.getArgument(p_260246_, SkibidiType.class);
    }


}
