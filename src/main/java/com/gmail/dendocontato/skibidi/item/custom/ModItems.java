package com.gmail.dendocontato.skibidi.item.custom;

import org.antlr.v4.parse.ANTLRParser.finallyClause_return;

import com.gmail.dendocontato.skibidi.SkibidiMod;
import com.gmail.dendocontato.skibidi.sound.ModSounds;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SkibidiMod.MODID);

    public static final RegistryObject<Item> SKIBIDI_FORTNITE_DISC = ITEMS.register("skibidi_fortnite_disc", 
        () -> new RecordItem(1, ModSounds.SKIBIDI_FORTNITE, new Item.Properties()
            .food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build()), 1760));

    public static final RegistryObject<Item> SKIBIDI_TOILET_FULL_SONG_DISC = ITEMS.register("skibidi_toilet_full_song_disc", 
        () -> new RecordItem(1, ModSounds.SKIBIDI_TOILET_FULL_SONG, new Item.Properties()
            .food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build()), 2520));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
