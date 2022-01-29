package club.cpacket.solaros.impl.modules.movement;

import club.cpacket.solaros.api.module.Category;
import club.cpacket.solaros.api.module.Module;
import club.cpacket.solaros.api.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovementInput;
import net.minecraft.world.chunk.EmptyChunk;

/**
 * @author zzurio
 */

public class EntitySpeed extends Module {

    public Setting<Boolean> antiStuck = register(new Setting<>("Anti-Stuck", "Checks empty chunks and stops entity speed.", false));
    public Setting<Double> entitySpeed = register(new Setting<>("Speed", "The speed of the entity.", 1.0, 0.0, 10.0));

    public EntitySpeed() {
        super("EntitySpeed", "Allows you to make entities go faster.", Category.MOVEMENT);
    }

    @Override
    public void onUpdate() {
        if (!nullSafe()) {
            return;
        }
        if (mc.player.getRidingEntity() != null) {
            MovementInput movementInput = mc.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            float yaw = mc.player.rotationYaw;
            if ((forward == 0.0D) && (strafe == 0.0D)) {
                mc.player.getRidingEntity().motionX = 0.0D;
                mc.player.getRidingEntity().motionZ = 0.0D;
            } else {
                if (forward != 0.0D) {
                    if (strafe > 0.0D) {
                        yaw += (forward > 0.0D ? -45 : 45);
                    } else if (strafe < 0.0D) {
                        yaw += (forward > 0.0D ? 45 : -45);
                    }
                    strafe = 0.0D;
                    if (forward > 0.0D) {
                        forward = 1.0D;
                    } else if (forward < 0.0D) {
                        forward = -1.0D;
                    }
                }
                double sin = Math.sin(Math.toRadians(yaw + 90.0F));
                double cos = Math.cos(Math.toRadians(yaw + 90.0F));

                if (isBorderingChunk(mc.player.getRidingEntity(), mc.player.getRidingEntity().motionX, mc.player.getRidingEntity().motionZ)) {
                    mc.player.getRidingEntity().motionX = mc.player.getRidingEntity().motionX = 0;
                    // TODO: Figure out if this redundant actually effects anything or is it just minecraft or my game being bullshit...
                }

                mc.player.getRidingEntity().motionX = (forward * entitySpeed.getValue() * cos + strafe * entitySpeed.getValue() * sin);
                mc.player.getRidingEntity().motionZ = (forward * entitySpeed.getValue() * sin - strafe * entitySpeed.getValue() * cos);
            }
        }
    }

    private boolean isBorderingChunk(Entity entity, double motX, double motZ) {
        return antiStuck.getValue() && mc.world.getChunk((int) (entity.posX + motX) >> 4, (int) (entity.posZ + motZ) >> 4) instanceof EmptyChunk;
    }
}
