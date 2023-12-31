package com.gmail.dendocontato.skibidi;

import java.util.function.Function;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceMetadata;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class PlayerModelReplace {

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        PlayerRenderer renderer = event.getRenderer();
        PlayerModel<AbstractClientPlayer> model = renderer.getModel();

/*         model.leftLeg.render(event.getPoseStack(), event.getMultiBufferSource().getBuffer(RenderType.solid()), 20, 20); */
        
        ToiletModel<Player> toilet = new ToiletModel<>(ToiletModel.createBodyLayer().bakeRoot());
        /* ToiletRenderer toiletRenderer = (ToiletRenderer) Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(ExampleMod.TOILET); */
        
        ToiletRenderer<Player> toiletRenderer = new ToiletRenderer<Player>(toilet);


        Player player = event.getEntity();
        PoseStack pose = event.getPoseStack();
        pose.pushPose();
        /* Quaternionf quaternion = new Quaternionf(0,0,2,0);
        quaternion.normalize();
        pose.mulPose(quaternion); */


        VertexConsumer vertex = event.getMultiBufferSource().getBuffer(RenderType.solid());

        ResourceLocation toiletTexture = new ResourceLocation(ExampleMod.MODID, "textures/toilet_texture.png");

        ResourceLocation teste = new ResourceLocation("minecraft", "textures/atlas/blocks.png");
        ResourceLocation teste2 = new ResourceLocation("minecraft", "textures/blocks/stone.png");

        TextureAtlasSprite text = Minecraft.getInstance().getTextureAtlas(teste).apply(teste2);

        vertex.putBulkData(pose.last(), new BakedQuad(new int[16], 0, Direction.NORTH, text, false), 0, 0, 0, 1, 1);

        toiletRenderer.render(player, 0, 0, pose, event.getMultiBufferSource(), event.getPackedLight());
/*         toilet.renderToBuffer(pose, vertex, event.getPackedLight(), 0, 0, 0, 0, 1); */

        pose.popPose();
        
        // Check if the player's model should be replaced
        // You can add conditions here based on your logic


        model.setAllVisible(false);
        model.head.visible = true;
        model.body.visible = true;
        model.head.offsetRotation(new Vector3f(0,0,0.1f));

        
    }
}
