package com.gmail.dendocontato.skibidi;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.gmail.dendocontato.skibidi.capabilities.SkibidiPlayerDataProvider;
import com.gmail.dendocontato.skibidi.capabilities.SkibidiType;
import com.gmail.dendocontato.skibidi.model.CameraModel;
import com.gmail.dendocontato.skibidi.model.ToiletModel;
import com.gmail.dendocontato.skibidi.renderer.CameraRenderer;
import com.gmail.dendocontato.skibidi.renderer.ToiletRenderer;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class PlayerModelReplace {

    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();


        player.getCapability(SkibidiPlayerDataProvider.SKIBIDY_TYPE, null).ifPresent(obj -> {
/*             if (player.getRandom().nextFloat() < 0.01f) {
                SkibidiMod.LOGGER.info(player.getName().toString() + " é " + obj.getSkibidiType().getName());
            } */

            SkibidiType skibidiType = obj.getSkibidiType();
            PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();

            switch (skibidiType) {
                case NONE:
                    model.head.xScale = 1.0f;
                    model.head.yScale = 1.0f;
                    model.head.zScale = 1.0f;

                    break;
                case TOILET: {
                    ToiletModel<Player> toilet = new ToiletModel<>(ToiletModel.createBodyLayer().bakeRoot());
                    ToiletRenderer<Player> toiletRenderer = new ToiletRenderer<Player>(toilet);
                    
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
                    //model.head.offsetRotation(new Vector3f(0,0,0.1f));
        
                    pose.popPose();
        
                    model.head.xScale = 1.3f;
                    model.head.yScale = 1.3f;
                    model.head.zScale = 1.3f;
        
                    pose.translate(0, -0.8f, 0);
                
                    break;
                }
                case CAMERA:
                    CameraModel<Player> camera = new CameraModel<>(CameraModel.createBodyLayer().bakeRoot());
                    CameraRenderer<Player> cameraRenderer = new CameraRenderer<Player>(camera);
                    
                    PoseStack pose = event.getPoseStack();
                    pose.pushPose();
        
                    pose.translate(0, 1.4f, 0);

                    cameraRenderer.render(player, 0, 0, pose, event.getMultiBufferSource(), event.getPackedLight());
                    
        
                    model.head.visible = false;
                    //model.head.offsetRotation(new Vector3f(0,0,0.1f));
        
                    pose.popPose();
        
                
                    break;

                default:
                    break;
            }
        
        });
    }
}
