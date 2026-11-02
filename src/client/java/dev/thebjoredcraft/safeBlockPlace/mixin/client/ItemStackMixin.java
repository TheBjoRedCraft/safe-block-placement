package dev.thebjoredcraft.safeBlockPlace.mixin.client;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

  @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
  private void skipFakeBlock(UseOnContext context, CallbackInfoReturnable<InteractionResult> callback) {
    ItemStack self = (ItemStack) (Object) this;

    if(context.getLevel().isClientSide()) {
      if(self.getItem() instanceof BlockItem) {
        callback.setReturnValue(InteractionResult.SUCCESS);
      }
    }
  }
}
