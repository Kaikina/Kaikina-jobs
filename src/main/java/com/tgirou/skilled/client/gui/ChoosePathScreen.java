package com.tgirou.skilled.client.gui;

import com.tgirou.skilled.SkilledMod;
import com.tgirou.skilled.networking.ChoosePathButtonMessage;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.ImageButton;


import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.NotNull;

public class ChoosePathScreen extends AbstractContainerScreen<ChoosePath> {
    private static final ResourceLocation texture = new ResourceLocation("skilled:textures/screens/choose_miner_path.png");
    private static final ResourceLocation BUTTONS = new ResourceLocation("skilled:textures/screens/test_button.png");
    private final static HashMap<String, Object> guistate = ChoosePath.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    public ChoosePathScreen(ChoosePath container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 125;
        this.imageHeight = 100;
    }

    @Override
    public void render(@NotNull PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderTooltip(ms, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull PoseStack ms, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, texture);
        GuiComponent.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            assert this.minecraft != null;
            assert this.minecraft.player != null;
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
    }

    @Override
    protected void renderLabels(@NotNull PoseStack poseStack, int mouseX, int mouseY) {
        this.font.draw(poseStack, "Choose a path", 26, 10, -12829636);
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init() {
        super.init();
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.addRenderableWidget(new ImageButton(this.leftPos + 17, this.topPos + 46, 32, 32, 0, 0, 32, BUTTONS, e -> {
            ChoosePathButtonMessage.handleButtonAction(entity, 0, x, y, z);
            ((ImageButton)e).setPosition(this.leftPos + 17, this.topPos + 46);
        }));
        this.addRenderableWidget(new ImageButton(this.leftPos + 80, this.topPos + 46, 32, 32, 32, 0, 32, BUTTONS, e -> {
            ChoosePathButtonMessage.handleButtonAction(entity, 1, x, y, z);
            ((ImageButton)e).setPosition(this.leftPos + 80, this.topPos + 46);
        }));
    }
}

