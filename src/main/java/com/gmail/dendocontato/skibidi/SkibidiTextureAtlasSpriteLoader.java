package com.gmail.dendocontato.skibidi;

import org.jetbrains.annotations.NotNull;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraftforge.client.textures.ForgeTextureMetadata;
import net.minecraftforge.client.textures.ITextureAtlasSpriteLoader;

public class SkibidiTextureAtlasSpriteLoader implements ITextureAtlasSpriteLoader {

    @Override
    public SpriteContents loadContents(ResourceLocation name, Resource resource, FrameSize frameSize, NativeImage image,
            ResourceMetadata animationMeta, ForgeTextureMetadata forgeMeta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadContents'");
    }

    @Override
    public @NotNull TextureAtlasSprite makeSprite(ResourceLocation atlasName, SpriteContents contents, int atlasWidth,
            int atlasHeight, int spriteX, int spriteY, int mipmapLevel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeSprite'");
    }

}