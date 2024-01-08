package com.gmail.dendocontato.skibidi;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import com.mojang.math.Axis;
import javax.annotation.Nullable;

import org.antlr.v4.parse.v4ParserException;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.phys.Vec3;


public class ToiletRenderer<T extends LivingEntity> {
    public ToiletModel<T> model;

    public ToiletRenderer(ToiletModel<T> model) {
        this.model = model;
    }

   public float getAttackAnim(T player, float f) {
        return 0f;
    }

   public boolean isEntityUpsideDown(T p_194454_) {
        if (p_194454_ instanceof Player || p_194454_.hasCustomName()) {
           String s = ChatFormatting.stripFormatting(p_194454_.getName().getString());
           if ("Dinnerbone".equals(s) || "Grumm".equals(s)) {
              return !(p_194454_ instanceof Player) || ((Player)p_194454_).isModelPartShown(PlayerModelPart.CAPE);
           }
        }
  
        return false;
    }

   protected float getBob(T p_115305_, float p_115306_) {
        return (float)p_115305_.tickCount + p_115306_;
     }
  
   protected float getFlipDegrees(T p_115337_) {
        return 90.0F;
     }
  
   protected float getWhiteOverlayProgress(T p_115334_, float p_115335_) {
        return 0.0F;
     }
  
   protected void scale(T p_115314_, PoseStack p_115315_, float p_115316_) {
     }

   protected boolean isShaking(T p_115304_) {
        return p_115304_.isFullyFrozen();
     }
  
   protected void setupRotations(T p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
      if (this.isShaking(p_115317_)) {
         p_115320_ += (float)(Math.cos((double)p_115317_.tickCount * 3.25D) * Math.PI * (double)0.4F);
      }

      if (!p_115317_.hasPose(Pose.SLEEPING)) {
         p_115318_.mulPose(Axis.YP.rotationDegrees(180.0F - p_115320_));
      }

      if (p_115317_.deathTime > 0) {
         float f = ((float)p_115317_.deathTime + p_115321_ - 1.0F) / 20.0F * 1.6F;
         f = Mth.sqrt(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         p_115318_.mulPose(Axis.ZP.rotationDegrees(f * this.getFlipDegrees(p_115317_)));
      } else if (p_115317_.isAutoSpinAttack()) {
         p_115318_.mulPose(Axis.XP.rotationDegrees(-90.0F - p_115317_.getXRot()));
         p_115318_.mulPose(Axis.YP.rotationDegrees(((float)p_115317_.tickCount + p_115321_) * -75.0F));
      } else if (p_115317_.hasPose(Pose.SLEEPING)) {
         Direction direction = p_115317_.getBedOrientation();
         float f1 = direction != null ? sleepDirectionToRotation(direction) : p_115320_;
         p_115318_.mulPose(Axis.YP.rotationDegrees(f1));
         p_115318_.mulPose(Axis.ZP.rotationDegrees(this.getFlipDegrees(p_115317_)));
         p_115318_.mulPose(Axis.YP.rotationDegrees(270.0F));
      } else if (isEntityUpsideDown(p_115317_)) {
         p_115318_.translate(0.0F, p_115317_.getBbHeight() + 0.1F, 0.0F);
         p_115318_.mulPose(Axis.ZP.rotationDegrees(180.0F));
      }


      float f = p_115317_.getSwimAmount(p_115321_);
      if (p_115317_.isFallFlying()) {
         float f1 = (float)p_115317_.getFallFlyingTicks() + p_115321_;
         float f2 = Mth.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
         if (!p_115317_.isAutoSpinAttack()) {
            p_115318_.mulPose(Axis.XP.rotationDegrees(f2 * (-90.0F - p_115317_.getXRot())));
         }

         Vec3 vec3 = p_115317_.getViewVector(p_115321_);
         Vec3 vec31 = new Vec3(1,1,1); //p_115317_.getDeltaMovementLerped(p_115321_);
         double d0 = vec31.horizontalDistanceSqr();
         double d1 = vec3.horizontalDistanceSqr();
         if (d0 > 0.0D && d1 > 0.0D) {
            double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
            double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
            p_115318_.mulPose(Axis.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
         }
      } else if (f > 0.0F) {
         float f3 = p_115317_.isInWater() || p_115317_.isInFluidType((fluidType, height) -> p_115317_.canSwimInFluidType(fluidType)) ? -90.0F - p_115317_.getXRot() : -90.0F;
         float f4 = Mth.lerp(f, 0.0F, f3);
         p_115318_.mulPose(Axis.XP.rotationDegrees(f4));
         if (p_115317_.isVisuallySwimming()) {
            p_115318_.translate(0.0F, -1.0F, 0.3F);
         }
      } else {


      }
     }

/*     public ToiletRenderer(Context p_174289_) {
        super(p_174289_, new ToiletModel<>(p_174289_.bakeLayer(ToiletModel.LAYER_LOCATION)), 1);
    }

    public ToiletRenderer(Context p_174289_, ToiletModel<Player> p_174290_, float p_174291_) {
        super(p_174289_, new ToiletModel<>(p_174289_.bakeLayer(ToiletModel.LAYER_LOCATION)), p_174291_);
    } */

    private final static ResourceLocation toiletTexture = new ResourceLocation(SkibidiMod.MODID, "textures/toilet_texture.png");

    public void render(T p_115308_, float p_115309_, float p_115310_, PoseStack p_115311_, MultiBufferSource p_115312_, int p_115313_) {
        p_115311_.pushPose();
        this.model.attackTime = this.getAttackAnim(p_115308_, p_115310_);

        boolean shouldSit = p_115308_.isPassenger() && (p_115308_.getVehicle() != null && p_115308_.getVehicle().shouldRiderSit());
        this.model.riding = shouldSit;
        this.model.young = p_115308_.isBaby();
        float f = Mth.rotLerp(p_115310_, p_115308_.yBodyRotO, p_115308_.yBodyRot);
        float f1 = Mth.rotLerp(p_115310_, p_115308_.yHeadRotO, p_115308_.yHeadRot);
        float f2 = f1 - f;
        if (shouldSit && p_115308_.getVehicle() instanceof LivingEntity) {
            Entity $$14 = p_115308_.getVehicle();
            if ($$14 instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)$$14;
                f = Mth.rotLerp(p_115310_, livingentity.yBodyRotO, livingentity.yBodyRot);
                f2 = f1 - f;
                float f6 = Mth.wrapDegrees(f2);
                if (f6 < -85.0F) {
                f6 = -85.0F;
                }

                if (f6 >= 85.0F) {
                f6 = 85.0F;
                }

                f = f1 - f6;
                if (f6 * f6 > 2500.0F) {
                f += f6 * 0.2F;
                }

                f2 = f1 - f;
            }
        }

        float f5 = Mth.lerp(p_115310_, p_115308_.xRotO, p_115308_.getXRot());
        if (isEntityUpsideDown(p_115308_)) {
            f5 *= -1.0F;
            f2 *= -1.0F;
        }

        if (p_115308_.hasPose(Pose.SLEEPING)) {
            Direction direction = p_115308_.getBedOrientation();
            if (direction != null) {
                float f3 = p_115308_.getEyeHeight(Pose.STANDING) - 0.1F;
                p_115311_.translate((float)(-direction.getStepX()) * f3, 0.0F, (float)(-direction.getStepZ()) * f3);
            }
        }

        float f7 = this.getBob(p_115308_, p_115310_);
        this.setupRotations(p_115308_, p_115311_, f7, f, p_115310_);
        p_115311_.scale(-1.0F, -1.0F, 1.0F);
        this.scale(p_115308_, p_115311_, p_115310_);
        p_115311_.translate(0.0F, -1.501F, 0.0F);
        float f8 = 0.0F;
        float f4 = 0.0F;
        if (!shouldSit && p_115308_.isAlive()) {
            f8 = p_115308_.walkAnimation.speed(p_115310_);
            f4 = p_115308_.walkAnimation.position(p_115310_);
            if (p_115308_.isBaby()) {
                f4 *= 3.0F;
            }

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.model.prepareMobModel(p_115308_, f4, f8, p_115310_);
        this.model.setupAnim(p_115308_, f4, f8, f7, f2, f5);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = this.isBodyVisible(p_115308_);
        boolean flag1 = !flag && !p_115308_.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(p_115308_);
        RenderType rendertype = this.getRenderType(p_115308_, flag, flag1, flag2);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = p_115312_.getBuffer(rendertype);
            int i = getOverlayCoords(p_115308_, this.getWhiteOverlayProgress(p_115308_, p_115310_));
            this.model.renderToBuffer(p_115311_, vertexconsumer, p_115313_, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
        }

/*         if (!p_115308_.isSpectator()) {
            for(RenderLayer<T, M> renderlayer : this.layers) {
                renderlayer.render(p_115311_, p_115312_, p_115313_, p_115308_, f4, f8, p_115310_, f7, f2, f5);
            }
        } */

        p_115311_.popPose();
    }


    
    public ResourceLocation getTextureLocation(T p_115812_) {
        return toiletTexture;
    }

    @Nullable
    protected RenderType getRenderType(T p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
       ResourceLocation resourcelocation = this.getTextureLocation(p_115322_);
       if (p_115324_) {
          return RenderType.itemEntityTranslucentCull(resourcelocation);
       } else if (p_115323_) {
          return this.model.renderType(resourcelocation);
       } else {
          return p_115325_ ? RenderType.outline(resourcelocation) : null;
       }
    }
 
    public static int getOverlayCoords(LivingEntity p_115339_, float p_115340_) {
       return OverlayTexture.pack(OverlayTexture.u(p_115340_), OverlayTexture.v(p_115339_.hurtTime > 0 || p_115339_.deathTime > 0));
    }
 
    protected boolean isBodyVisible(T p_115341_) {
       return !p_115341_.isInvisible();
    }
 
    private static float sleepDirectionToRotation(Direction p_115329_) {
       switch (p_115329_) {
          case SOUTH:
             return 90.0F;
          case WEST:
             return 0.0F;
          case NORTH:
             return 270.0F;
          case EAST:
             return 180.0F;
          default:
             return 0.0F;
       }
    }


}


