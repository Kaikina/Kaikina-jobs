package com.tgirou.skilled.blocks.entities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.tgirou.skilled.blocks.customs.CrystallizerBlock;
import com.tgirou.skilled.blocks.entities.CrystallizerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class CrystallizerBlockEntityRenderer implements BlockEntityRenderer<CrystallizerBlockEntity> {
    public CrystallizerBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(CrystallizerBlockEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource
                       bufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack itemStack = blockEntity.getResultStack();
        ItemStack itemStack2 = blockEntity.getCrystallizingStack();
        stack.pushPose();
        stack.translate(0.5f, 1.25f, 0.82f);
        stack.scale(0.25f, 0.25f, 0.25f);
        stack.mulPose(Vector3f.XP.rotationDegrees(180));

        switch (blockEntity.getBlockState().getValue(CrystallizerBlock.FACING)) {
            case NORTH -> stack.mulPose(Vector3f.ZP.rotationDegrees(0));
            case EAST -> stack.mulPose(Vector3f.ZP.rotationDegrees(90));
            case SOUTH -> stack.mulPose(Vector3f.ZP.rotationDegrees(180));
            case WEST -> stack.mulPose(Vector3f.ZP.rotationDegrees(270));
        }

        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(blockEntity.getLevel(),
                        blockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, stack, bufferSource, 1);
        stack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
