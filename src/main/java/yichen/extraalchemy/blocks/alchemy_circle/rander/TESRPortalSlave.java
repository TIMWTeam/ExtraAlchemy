package yichen.extraalchemy.blocks.alchemy_circle.rander;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.blocks.alchemy_circle.tile.TileAlchemyCircleSlave;

public class TESRPortalSlave extends TileEntitySpecialRenderer<TileAlchemyCircleSlave> {

    private final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ExtraAlchemy.MODID, "textures/magic/magic_circle.png");

    @Override
    public void render(TileAlchemyCircleSlave te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        boolean isPlayerInRange = getWorld().isAnyPlayerWithinRangeAt(te.getPos().getX() + 0.5, te.getPos().getY() + 0.75, te.getPos().getZ() + 0.5, 0.5);

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        if (te.isActive()) {
            if (isPlayerInRange) {
                renderTeleportCircleTop(te, partialTicks);
                if (te.isAtPosTopMiddleCircle) {
                    renderTeleportCircleBottom(te, partialTicks);
                }

                renderTeleportCircleSide(1, 1, te, partialTicks);
                renderTeleportCircleSide(1, -1, te, partialTicks);
                renderTeleportCircleSide(-1, 1, te, partialTicks);
                renderTeleportCircleSide(-1, -1, te, partialTicks);

                if (te.isAtPosSideCircles) {
                    renderParticles(te);
                }

            } else {
                renderActivatedCircle(te, partialTicks);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderTeleportCircleTop(TileAlchemyCircleSlave te, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();

        float counterTeleport = te.counterTeleport.value();
        boolean isAtPosTopMiddleCircle = te.isAtPosTopMiddleCircle;

        GlStateManager.blendFunc(770, 1);

        GlStateManager.translate(0.5F, 0.6F, 0.5F);
        if (isAtPosTopMiddleCircle) {
            GlStateManager.translate(0, 2F, 0);
            float translate = te.counterTeleport.oldValue() + (counterTeleport - te.counterTeleport.oldValue()) * partialTicks;
            GlStateManager.translate(0, -translate / 50, 0);
        } else {
            float translate = te.counterTeleport.oldValue() + (counterTeleport - te.counterTeleport.oldValue()) * partialTicks;
            GlStateManager.translate(0, translate / 50, 0);
        }

        GlStateManager.scale(0.75F, 0, 0.75F);
        if (isAtPosTopMiddleCircle) {
            float scale = (100F - (te.counterTeleport.oldValue() + (counterTeleport - te.counterTeleport.oldValue()) * partialTicks)) / 37.5F;
            GlStateManager.scale(scale, 0, scale);
        } else {
            float scale = (te.counterTeleport.oldValue() + (counterTeleport - te.counterTeleport.oldValue()) * partialTicks) / 37.5F;
            GlStateManager.scale(scale, 0, scale);
        }

        float rotation = te.counterRotation.oldValue() + (te.counterRotation.value() - te.counterRotation.oldValue()) * partialTicks;
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.rotate(rotation * 2F, 0, 0, 1);

        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_LOCATION);
        shadeModel();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

        buffer.pos(1, 1, 0.0D).tex(1.0D, 1).endVertex();
        buffer.pos(1, -1, 0.0D).tex(1.0D, 0).endVertex();
        buffer.pos(-1, -1, 0.0D).tex(0, 0).endVertex();
        buffer.pos(-1, 1, 0.0D).tex(0, 1).endVertex();

        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
    private void renderTeleportCircleBottom(TileAlchemyCircleSlave te, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();

        float counterTeleport = te.counterTeleport.value();

        GlStateManager.blendFunc(770, 1);

        float translate = te.counterTeleport.oldValue() + (counterTeleport - te.counterTeleport.oldValue()) * partialTicks;
        GlStateManager.translate(0.5F, 0.6F, 0.5F);
        GlStateManager.translate(0, translate / 50, 0);

        float scale = (te.counterTeleport.oldValue() + (counterTeleport - te.counterTeleport.oldValue()) * partialTicks) / 37.5F;
        GlStateManager.scale(0.75F, 0, 0.75F);
        GlStateManager.scale(scale, 0, scale);

        float rotation = te.counterRotation.oldValue() + (te.counterRotation.value() - te.counterRotation.oldValue()) * partialTicks;
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.rotate(rotation * 2F, 0, 0, 1);


        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_LOCATION);
        shadeModel();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

        buffer.pos(1, 1, 0.0D).tex(1.0D, 1).endVertex();
        buffer.pos(1, -1, 0.0D).tex(1.0D, 0).endVertex();
        buffer.pos(-1, -1, 0.0D).tex(0, 0).endVertex();
        buffer.pos(-1, 1, 0.0D).tex(0, 1).endVertex();

        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
    private void renderTeleportCircleSide(double posX, double posZ, TileAlchemyCircleSlave te, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        float counterCircle = te.counterCircle.value();

        if (te.isAtPosSideCircles) {
            GlStateManager.translate(0.5F + posX, /*2*/1.5F, 0.5F + posZ);
            GlStateManager.scale(0.25F, 0, 0.25F);
        } else {
            float translate = 0.65F + (te.counterCircle.oldValue() + (counterCircle - te.counterCircle.oldValue()) * partialTicks) / 90F;
            if (translate >= 1.5F) te.isAtPosSideCircles = true;
            GlStateManager.translate(0.5F + posX, translate, 0.5F + posZ);

            float scale = ((te.counterCircle.oldValue() + (counterCircle - te.counterCircle.oldValue()) * partialTicks)) / 300F;
            if (scale >= 0.25F) GlStateManager.scale(0.25F, 0, 0.25F);
            else GlStateManager.scale(scale, 0, scale);
        }

        float rotation = te.counterRotation.oldValue() + (te.counterRotation.value() - te.counterRotation.oldValue()) * partialTicks;
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.rotate(rotation * 2F, 0, 0, 1);

        GlStateManager.blendFunc(770, 1);

        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_LOCATION);
        shadeModel();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

        buffer.pos(1, 1, 0.0D).tex(1.0D, 1).endVertex();
        buffer.pos(1, -1, 0.0D).tex(1.0D, 0).endVertex();
        buffer.pos(-1, -1, 0.0D).tex(0, 0).endVertex();
        buffer.pos(-1, 1, 0.0D).tex(0, 1).endVertex();

        tessellator.draw();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private void renderActivatedCircle(TileAlchemyCircleSlave te, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        GlStateManager.blendFunc(770, 1);

        GlStateManager.translate(0.5F, 0.6F, 0.5F);
        GlStateManager.scale(0.5F, 0, 0.5F);

        float rotation = te.counterRotation.oldValue() + (te.counterRotation.value() - te.counterRotation.oldValue()) * partialTicks;
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.rotate(rotation, 0, 0, 1);

        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_LOCATION);
        shadeModel();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);

        buffer.pos(1, 1, 0.0D).tex(1.0D, 1).endVertex();
        buffer.pos(1, -1, 0.0D).tex(1.0D, 0).endVertex();
        buffer.pos(-1, -1, 0.0D).tex(0, 0).endVertex();
        buffer.pos(-1, 1, 0.0D).tex(0, 1).endVertex();

        tessellator.draw();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
    private void renderParticles(TileAlchemyCircleSlave te) {
        double xPos = te.getPos().getX() + 0.5;
        double yPos = te.getPos().getY() + /*0.6*/0.1;
        double zPos = te.getPos().getZ() + 0.5;
        for (int i = 0; i < 2; ++i)
        {
            getWorld().spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, 1, 0.4, 1);
            getWorld().spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, 1, 0.4, -1);
            getWorld().spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, -1, 0.4, -1);
            getWorld().spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, -1, 0.4, 1);
        }
    }

    private void shadeModel() {
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }
    }
}