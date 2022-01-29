package club.cpacket.solaros.mixin.mixins.entity;

import club.cpacket.solaros.Solaros;
import club.cpacket.solaros.api.event.events.render.AspectEvent;
import club.cpacket.solaros.api.event.events.render.RenderNametagEvent;
import club.cpacket.solaros.impl.modules.render.CameraClip;
import club.cpacket.solaros.mixin.transformer.IEntityRenderer;
import event.bus.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author zzurio
 */

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer implements IEntityRenderer {

    // Stupid.
    Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "drawNameplate", at = @At("HEAD"), cancellable = true)
    private static void drawNameplate(FontRenderer fontRendererIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking, CallbackInfo ci) {
        RenderNametagEvent event = new RenderNametagEvent();
        EventBus.post(event);
        if (event.getCancelled()) {
            ci.cancel();
        }
    }

    @Shadow
    private void setupCameraTransform(float partialTicks, int pass) {
    }

    @Override
    public void setupCamera(float partialTicks, int pass) {
        setupCameraTransform(partialTicks, pass);
    }

    @Redirect(method = "setupCameraTransform", at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onSetupCameraTransform(float fovy, float aspect, float zNear, float zFar) {
        AspectEvent event = new AspectEvent((float) this.mc.displayWidth / (float) this.mc.displayHeight);
        EventBus.post(event);
        Project.gluPerspective(fovy, event.getAspect(), zNear, zFar);
    }

    @Redirect(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onRenderWorldPass(float fovy, float aspect, float zNear, float zFar) {
        AspectEvent event = new AspectEvent((float) this.mc.displayWidth / (float) this.mc.displayHeight);
        EventBus.post(event);
        Project.gluPerspective(fovy, event.getAspect(), zNear, zFar);
    }

    @Redirect(method = "renderCloudsCheck", at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/glu/Project;gluPerspective(FFFF)V"))
    private void onRenderCloudsCheck(float fovy, float aspect, float zNear, float zFar) {
        AspectEvent event = new AspectEvent((float) this.mc.displayWidth / (float) this.mc.displayHeight);
        EventBus.post(event);
        Project.gluPerspective(fovy, event.getAspect(), zNear, zFar);
    }

    @ModifyVariable(method = "orientCamera", ordinal = 3, at = @At(value = "STORE", ordinal = 0), require = 1)
    public double changeCameraDistanceHook(double range) {
        return Solaros.INSTANCE.moduleManager.getModuleByClass(CameraClip.class).isEnabled() && Solaros.INSTANCE.moduleManager.getModuleByClass(CameraClip.class).extend.getValue()
                ? Solaros.INSTANCE.moduleManager.getModuleByClass(CameraClip.class).distance.getValue()
                : range;
    }

    @ModifyVariable(method = "orientCamera", ordinal = 7, at = @At(value = "STORE", ordinal = 0), require = 1)
    public double orientCameraHook(double range) {
        return Solaros.INSTANCE.moduleManager.getModuleByClass(CameraClip.class).isEnabled()
                ? Solaros.INSTANCE.moduleManager.getModuleByClass(CameraClip.class).extend.getValue()
                ? Solaros.INSTANCE.moduleManager.getModuleByClass(CameraClip.class).distance.getValue()
                : 4.0
                : range;
    }
}