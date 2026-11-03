package dev.thebjoredcraft.safeBlockPlace.mixin.client;

import dev.thebjoredcraft.safeBlockPlace.client.cache.PlacementCache;
import dev.thebjoredcraft.safeBlockPlace.config.loader.SafeBlockPlacementConfig;
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
    if(!SafeBlockPlacementConfig.get().enabled) {
      return;
    }

    if(!context.getLevel().isClientSide()) {
      return;
    }

    ItemStack self = (ItemStack) (Object) this;

    if(self.getItem() instanceof BlockItem) {
      PlacementCache.INSTANCE.cache(context.getClickedPos());
      callback.setReturnValue(InteractionResult.SUCCESS);
    }
  }
}
