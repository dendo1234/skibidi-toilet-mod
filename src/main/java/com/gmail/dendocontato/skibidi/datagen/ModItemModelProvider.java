package com.gmail.dendocontato.skibidi.datagen;

import com.gmail.dendocontato.skibidi.SkibidiMod;
import com.gmail.dendocontato.skibidi.item.custom.ModItems;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SkibidiMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.SKIBIDI_FORTNITE_DISC);
        simpleItem(ModItems.SKIBIDI_TOILET_FULL_SONG_DISC);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(), 
            new ResourceLocation("item/generated")).texture("layer0",
            new ResourceLocation(SkibidiMod.MODID, "item/" + item.getId().getPath()));
    }
}
