package club.cpacket.solaros.mixin.mixins.item;

import club.cpacket.solaros.Solaros;
import club.cpacket.solaros.impl.modules.misc.TrueDurability;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author zzurio
 */

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Shadow
    int itemDamage;

    @Inject(method = "<init>(Lnet/minecraft/item/Item;II)V", at = @At("RETURN"))
    private void initHook(Item itemIn, int amount, int meta, CallbackInfo ci) {
        Solaros.INSTANCE.moduleManager.getModuleByClass(TrueDurability.class);
        this.itemDamage = this.checkDurability(this.itemDamage,
                meta);
    }

    @Inject(method = "<init>(Lnet/minecraft/nbt/NBTTagCompound;)V", at = @At("RETURN"))
    private void initHook1(NBTTagCompound compound, CallbackInfo info) {
        Solaros.INSTANCE.moduleManager.getModuleByClass(TrueDurability.class);
        this.itemDamage = this.checkDurability(this.itemDamage,
                compound.getShort("Damage"));
    }

    private int checkDurability(int damage, int meta) {
        int durability = damage;

        if (Solaros.INSTANCE.moduleManager.getModuleByClass(TrueDurability.class).isEnabled() && meta < 0) {
            durability = meta;
        }
        return durability;
    }
}