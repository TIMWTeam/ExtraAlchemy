package yichen.extraalchemy.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageAlchemyCircle implements IMessage{

	public void fromBytes(ByteBuf buf)
    {
        
    }

    public void toBytes(ByteBuf buf)
    {
        
    }

    public static IMessage onMessage(MessageAlchemyCircle message, MessageContext ctx)
    {
    	if(ctx.side.isClient()) return null;
        ((WorldServer)ctx.getServerHandler().player.world).addScheduledTask(() -> 
                        ctx.getServerHandler().player.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 10 * 20, 1)));
        return null;
    }
}
