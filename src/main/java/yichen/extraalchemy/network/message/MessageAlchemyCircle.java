package yichen.extraalchemy.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
public class MessageAlchemyCircle implements IMessage{

	public void fromBytes(ByteBuf buf)
    {
        
    }

    public void toBytes(ByteBuf buf)
    {
        
    }

    public static IMessage onMessage(MessageAlchemyCircle message, MessageContext ctx)
    {
        return null;
    }
}
