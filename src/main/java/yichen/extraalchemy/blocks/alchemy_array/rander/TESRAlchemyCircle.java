package yichen.extraalchemy.blocks.alchemy_array.rander;

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
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.blocks.alchemy_array.tile.TileAlchemyCircle;

import org.lwjgl.opengl.GL11;

public class TESRAlchemyCircle extends TileEntitySpecialRenderer<TileAlchemyCircle> {

    private final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ExtraAlchemy.MODID, "textures/models/alchemyarray/transmute.png");

    @Override
    public void render(TileAlchemyCircle tac, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    	
    	boolean isPlayerInRange = getWorld().isAnyPlayerWithinRangeAt(tac.getPos().getX() + 0.5, tac.getPos().getY() + 0.75, tac.getPos().getZ() + 0.5, 0.5);

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        //正在激活
        if (tac.isActivating) {
            if (!tac.isActivationDone) {
            	//渲染-炼金法阵-序列化
                renderActivationSequence(tac, partialTicks);
            } else {
            	//渲染-已激活-待机中
            	renderActivatedCircle(tac, partialTicks);
            }
            //已激活-待机中
        } else if (tac.isActive()) {
            if (isPlayerInRange) {
            	//渲染-工作-顶部的圈  
                renderTeleportCircleTop(tac, partialTicks);
                if (tac.isAtPosTopMiddleCircle) {
                	//渲染-工作-底部的圈
                    renderTeleportCircleBottom(tac, partialTicks);
                }
                //渲染-工作-附属的圈
                renderTeleportCircleSide(1, 1, tac, partialTicks);
                renderTeleportCircleSide(1, -1, tac, partialTicks);
                renderTeleportCircleSide(-1, 1, tac, partialTicks);
                renderTeleportCircleSide(-1, -1, tac, partialTicks);
                
                
                
                
                //渲染-工作-附属的圈-粒子效果
                if (tac.isAtPosSideCircles) {
                    renderParticles(tac);
                }

            } else {
            	//渲染-已激活-待机中
                renderActivatedCircle(tac, partialTicks);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
    //渲染-顶部的圈
    private void renderTeleportCircleTop(TileAlchemyCircle tac, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();

        float counterTeleport = tac.counterTeleport.value();
        boolean isAtPosTopMiddleCircle = tac.isAtPosTopMiddleCircle;

        GlStateManager.blendFunc(770, 1);

        GlStateManager.translate(0.5F, 0.6F, 0.5F);
        if (isAtPosTopMiddleCircle) {
            GlStateManager.translate(0, 2F, 0);
            float translate = tac.counterTeleport.oldValue() + (counterTeleport - tac.counterTeleport.oldValue()) * partialTicks;
            GlStateManager.translate(0, -translate / 50, 0);
        } else {
            float translate = tac.counterTeleport.oldValue() + (counterTeleport - tac.counterTeleport.oldValue()) * partialTicks;
            GlStateManager.translate(0, translate / 50, 0);
        }

        GlStateManager.scale(0.75F, 0, 0.75F);
        if (isAtPosTopMiddleCircle) {
            float scale = (100F - (tac.counterTeleport.oldValue() + (counterTeleport - tac.counterTeleport.oldValue()) * partialTicks)) / 37.5F;
            GlStateManager.scale(scale, 0, scale);
        } else {
            float scale = (tac.counterTeleport.oldValue() + (counterTeleport - tac.counterTeleport.oldValue()) * partialTicks) / 37.5F;
            GlStateManager.scale(scale, 0, scale);
        }

        float rotation = tac.counterRotation.oldValue() + (tac.counterRotation.value() - tac.counterRotation.oldValue()) * partialTicks;
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
    //渲染-底部的圈
    private void renderTeleportCircleBottom(TileAlchemyCircle tac, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableBlend();

        float counterTeleport = tac.counterTeleport.value();

        GlStateManager.blendFunc(770, 1);

        float translate = tac.counterTeleport.oldValue() + (counterTeleport - tac.counterTeleport.oldValue()) * partialTicks;
        GlStateManager.translate(0.5F, 0.6F, 0.5F);
        GlStateManager.translate(0, translate / 50, 0);

        float scale = (tac.counterTeleport.oldValue() + (counterTeleport - tac.counterTeleport.oldValue()) * partialTicks) / 37.5F;
        GlStateManager.scale(0.75F, 0, 0.75F);
        GlStateManager.scale(scale, 0, scale);

        float rotation = tac.counterRotation.oldValue() + (tac.counterRotation.value() - tac.counterRotation.oldValue()) * partialTicks;
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
  //渲染-附属的圈
    private void renderTeleportCircleSide(double posX, double posZ, TileAlchemyCircle tac, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        float counterCircle = tac.counterCircle.value();

        if (tac.isAtPosSideCircles) {
            GlStateManager.translate(0.5F + posX, /*2*/1.5F, 0.5F + posZ);
            GlStateManager.scale(0.25F, 0, 0.25F);
        } else {
            float translate = 0.65F + (tac.counterCircle.oldValue() + (counterCircle - tac.counterCircle.oldValue()) * partialTicks) / 90F;
            if (translate >= 1.5F) tac.isAtPosSideCircles = true;
            GlStateManager.translate(0.5F + posX, translate, 0.5F + posZ);

            float scale = ((tac.counterCircle.oldValue() + (counterCircle - tac.counterCircle.oldValue()) * partialTicks)) / 300F;
            if (scale >= 0.25F) GlStateManager.scale(0.25F, 0, 0.25F);
            else GlStateManager.scale(scale, 0, scale);
        }

        float rotation = tac.counterRotation.oldValue() + (tac.counterRotation.value() - tac.counterRotation.oldValue()) * partialTicks;
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
	//渲染-炼金法阵�?�?-序列
    private void renderActivationSequence(TileAlchemyCircle tac, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();

        float counterActivation = tac.counterActivation.value();

        GlStateManager.blendFunc(770, 1);

        GlStateManager.translate(0.5F, 0, 0.5F);
        GlStateManager.scale(1.5F, 1F, 1.5F);

        float translate = (tac.counterActivation.oldValue() + (counterActivation - tac.counterActivation.oldValue()) * partialTicks) / 275F;
        GlStateManager.translate(0, translate, 0);

        float scale = (tac.counterActivation.oldValue() + (counterActivation - tac.counterActivation.oldValue()) * partialTicks - 200) / 100F;

        GlStateManager.scale(scale, 1, scale);

        float rotation = tac.counterRotation.oldValue() + (tac.counterRotation.value() - tac.counterRotation.oldValue()) * partialTicks;
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.rotate(rotation * 3F, 0, 0, 1);

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
  //渲染-已激�?-待机中的�?
    private void renderActivatedCircle(TileAlchemyCircle tac, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        GlStateManager.blendFunc(770, 1);

        GlStateManager.translate(0.5F, 0.6F, 0.5F);
        GlStateManager.scale(0.5F, 0, 0.5F);

        float rotation = tac.counterRotation.oldValue() + (tac.counterRotation.value() - tac.counterRotation.oldValue()) * partialTicks;
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
	//渲染-工作-附属的圈-粒子效果
    private void renderParticles(TileAlchemyCircle tac) {
        double xPos = tac.getPos().getX() + 0.5;
        double yPos = tac.getPos().getY() + /*0.6*/0.1;
        double zPos = tac.getPos().getZ() + 0.5;
        for (int i = 0; i < 2; ++i)
        {
            getWorld().spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, 1, 0.4, 1);
            getWorld().spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, 1, 0.4, -1);
            getWorld().spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, -1, 0.4, -1);
            getWorld().spawnParticle(EnumParticleTypes.PORTAL, xPos, yPos, zPos, -1, 0.4, 1);
        }
    }
    //阴影模型
    private void shadeModel() {
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }
    }
}
