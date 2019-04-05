package yichen.extraalchemy.base.blocks.alchemy_array;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.config.ConfigLoader;
import yichen.extraalchemy.util.Counter;

import org.lwjgl.opengl.GL11;

public class TESRAlchemyArrayTransmute extends TileEntitySpecialRenderer<TileAlchemyArrayTransmute> {

    private final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(ExtraAlchemy.MODID, "textures/models/alchemyarray/transmute.png");
    
    @Override
    public void render(TileAlchemyArrayTransmute aa, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

    	boolean isPlayerInRange = getWorld().isAnyPlayerWithinRangeAt(aa.getPos().getX() + 0.5, aa.getPos().getY() + 0.75, aa.getPos().getZ() + 0.5, 0.5);

        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();
        if(aa.status == 0) {
        	//未启动
        	renderInactivated(aa.getTicksExisted(),partialTicks);
        	
        } else if (aa.status == 1) {
        	//启动中
        	renderActivationSequence(aa.getTicksExisted(),aa.TICKS_ACTIVE,aa.counter,aa.eff,partialTicks);
        	
        } else if (aa.status == 2) {
        	//已启动
        	renderActivated(aa.getTicksExisted(), partialTicks);
        	renderTOPFloat(aa.getTicksExisted(),aa.TICKS_TRANSMUTE, aa.counterWork, aa.eff, partialTicks);
        	renderBUTFloat(aa.getTicksExisted(),aa.TICKS_TRANSMUTE, aa.counterWork, aa.eff, partialTicks);
        }else if (aa.status == 3) {
        	//关闭中
        	renderActivationSequence(aa.getTicksExisted(),aa.TICKS_ACTIVE,aa.counter,aa.eff,partialTicks);
        	
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
	//Status 0
    public void renderInactivated(int tick,float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        GlStateManager.blendFunc(770, 1);

        GlStateManager.translate(0.5F, 0.05F, 0.5F);
        GlStateManager.scale(0.5F, 0, 0.5F);
        GlStateManager.rotate(90, 1, 0, 0);
        float color = (float) ((Math.sin((tick + partialTicks) / 12D) + 1D) / 20D + 0.05D);
        GlStateManager.color(color, color, color);
        
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
	//Status 1
    private void renderActivationSequence(int tick, float active,Counter counter, float eff, float partialTicks) {
    	GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        GlStateManager.blendFunc(770, 1);
        
        float ratio = 0.3F;
        
        //位移
        GlStateManager.translate(0.5F, 0.05F, 0.5F);
        if((counter.value()/active) >= ratio) {
            float translate = (((counter.value()-(active*ratio))/720)/eff)/(1-ratio);
            GlStateManager.translate(0, translate , 0);
        }
        
        //缩放
        GlStateManager.scale(0.5F, 0, 0.5F);
        //旋转
        GlStateManager.rotate(90, 1, 0, 0);
        if((counter.value()/active) >= 0.25) {
            float rotation = tick % 360 ;
        	GlStateManager.rotate(rotation * 3F, 0, 0, 1);
        }
        //颜色
        float color = 0.05F+counter.value()/active;
        GlStateManager.color(color, color, color);
        
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
    //Status 2
    public void renderActivated(int tick,float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        GlStateManager.blendFunc(770, 1);
        GlStateManager.translate(0.5F, 0.33F, 0.5F);
        GlStateManager.scale(0.5F, 0, 0.5F);

        float rotation = tick % 360 ;
        GlStateManager.rotate(90, 1, 0, 0);
        GlStateManager.rotate(rotation * 3F, 0, 0, 1);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE_LOCATION);
        shadeModel();

        //float color = (float) ((Math.sin((tick + partialTicks) / 8D) + 1D) / 6D + 0.8D);
        //GlStateManager.color(1, color, color);
        
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
    //浮动炼金阵-top
    public void renderTOPFloat(int tick,float worktime,Counter work,float eff,float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        GlStateManager.blendFunc(770, 1);

        float magnification = worktime/100;
        float translate = 0.008F * ((work.value() * eff)/magnification);
        float scale = 0.008F * ((work.value() * eff)/magnification);
        if(work.value() <= (worktime/4)) {
            GlStateManager.translate(0.5F, 0.33F, 0.5F);
            GlStateManager.translate(0, translate, 0);
            
            GlStateManager.scale(0.5F+scale, 0, 0.5F+scale);
        }else if (work.value() > (worktime/4) && work.value() <= (worktime-(worktime/4))) {
            GlStateManager.translate(0.5F, 0.53F, 0.5F);
            GlStateManager.translate(0, -(translate-0.2F), 0);

            GlStateManager.scale(0.7F, 0, 0.7F);
		}else {
            GlStateManager.translate(0.5F, 0.13F, 0.5F);
            GlStateManager.translate(0, (translate-0.6F), 0);

            GlStateManager.scale(0.7F-(scale-0.6), 0, 0.7F-(scale-0.6));
		}
        
        float rotation = tick % 360 ;
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
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    private void renderBUTFloat(int tick, float worktime, Counter work, float eff, float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();

        GlStateManager.blendFunc(770, 1);
        
        float magnification = worktime/100;
        float translate = 0.008F * ((work.value() * eff)/magnification);
        float scale = 0.008F * ((work.value() * eff)/magnification);
        if(work.value() <= (worktime/4)) {
            GlStateManager.translate(0.5F, 0.33F, 0.5F);
            GlStateManager.translate(0, -translate, 0);
            
            GlStateManager.scale(0.5F+scale, 0, 0.5F+scale);
        }else if (work.value() > (worktime/4) && work.value() <= (worktime-(worktime/4))) {
            GlStateManager.translate(0.5F, 0.13F, 0.5F);
            GlStateManager.translate(0, (translate-0.2F), 0);

            GlStateManager.scale(0.7F, 0, 0.7F);
		}else {
            GlStateManager.translate(0.5F, 0.53F, 0.5F);
            GlStateManager.translate(0, -(translate-0.6F), 0);

            GlStateManager.scale(0.7F-(scale-0.6), 0, 0.7F-(scale-0.6));
		}
        
        float rotation = tick % 360 ;
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
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
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
