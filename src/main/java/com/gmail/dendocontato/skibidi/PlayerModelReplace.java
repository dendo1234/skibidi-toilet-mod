package com.gmail.dendocontato.skibidi;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class PlayerModelReplace {

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
        
        ToiletModel<Player> toilet = new ToiletModel<>(ToiletModel.createBodyLayer().bakeRoot());
        ToiletRenderer<Player> toiletRenderer = new ToiletRenderer<Player>(toilet);

        Player player = event.getEntity();
        PoseStack pose = event.getPoseStack();
        pose.pushPose();

        /* Quaternionf quaternion = new Quaternionf(0,0,2,0);
        quaternion.normalize();
        pose.mulPose(quaternion); */

        toiletRenderer.render(player, 0, 0, pose, event.getMultiBufferSource(), event.getPackedLight());
        
        // Check if the player's model should be replaced
        // You can add conditions here based on your logic

        model.setAllVisible(false);
        model.head.visible = true;
        model.body.visible = true;
        model.head.offsetRotation(new Vector3f(0,0,0.1f));

        pose.popPose();

        
    }
}
