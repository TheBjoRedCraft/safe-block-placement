package dev.thebjoredcraft.safeBlockPlace.config;

import dev.thebjoredcraft.safeBlockPlace.config.loader.SafeBlockPlacementConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ConfigScreen extends Screen {
  private final Screen parent;

  public ConfigScreen(Screen parent) {
    super(Component.translatable("config.safeblockplace.title"));
    this.parent = parent;
  }

  private static Component label(SafeBlockPlacementConfig cfg) {
    return CommonComponents.optionStatus(Component.translatable("config.safeblockplace.enabled"), cfg.enabled);
  }

  @Override
  protected void init() {
    SafeBlockPlacementConfig cfg = SafeBlockPlacementConfig.get();

    addRenderableWidget(Button.builder(label(cfg), b -> {
      cfg.enabled = !cfg.enabled;
      cfg.save();
      b.setMessage(label(cfg));
    }).bounds(width / 2 - 100, height / 2 - 24, 200, 20).build());

    addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> onClose())
        .bounds(width / 2 - 100, height / 2 + 4, 200, 20).build());
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
    super.extractRenderState(graphics, mouseX, mouseY, delta);
    graphics.centeredText(font, title, width / 2, height / 2 - 60, 0xFFFFFFFF);
  }

  @Override
  public void onClose() {
    minecraft.setScreenAndShow(parent);
  }
}