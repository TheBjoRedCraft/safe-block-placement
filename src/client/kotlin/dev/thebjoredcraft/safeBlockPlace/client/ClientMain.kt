package dev.thebjoredcraft.safeBlockPlace.client

import com.mojang.authlib.minecraft.client.MinecraftClient
import com.mojang.blaze3d.platform.InputConstants
import dev.thebjoredcraft.safeBlockPlace.config.loader.SafeBlockPlacementConfig
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier

class ClientMain : ClientModInitializer {
    override fun onInitializeClient() {
        SafeBlockPlacementConfig.get()

        val category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("safeblockplace", "main"))
        val toggleKey = KeyMappingHelper.registerKeyMapping(
            KeyMapping(
                "key.safeblockplace.toggle",
                106,
                category
            )
        )

        ClientMain.toggleKey = toggleKey

        ClientTickEvents.END_CLIENT_TICK.register {
            while (toggleKey.consumeClick()) {
                val config = SafeBlockPlacementConfig.get()

                config.enabled = !config.enabled
                config.save()

                val player = it.player ?: return@register

                player.sendOverlayMessage(CommonComponents.optionStatus(
                    Component.translatable("config.safeblockplace.enabled"),
                    config.enabled
                ))
            }
        }
    }


    companion object {
        lateinit var toggleKey: KeyMapping
    }
}