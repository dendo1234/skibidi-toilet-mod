package com.gmail.dendocontato.skibidi.sound;

import com.gmail.dendocontato.skibidi.SkibidiMod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
        public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SkibidiMod.MODID);
        
        public static final RegistryObject<SoundEvent> SKIBIDI_FORTNITE = registerSoundEvent("skibidi_fortnite");

        public static final RegistryObject<SoundEvent> SKIBIDI_TOILET_FULL_SONG = registerSoundEvent("skibidi_toilet_full_song");
        
        private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
            ResourceLocation id = new ResourceLocation(SkibidiMod.MODID, name);
            return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
        }

        public static void register(IEventBus eventBus) {
            SOUND_EVENTS.register(eventBus);
        }
}
