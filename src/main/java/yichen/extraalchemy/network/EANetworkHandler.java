package yichen.extraalchemy.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.network.message.MessageAlchemyCircle;

public class EANetworkHandler {
	private static SimpleNetworkWrapper networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(ExtraAlchemy.MODID);
	
	public static void init() {
		networkWrapper.registerMessage(MessageAlchemyCircle::onMessage, MessageAlchemyCircle.class, 0, Side.SERVER);
	}
	@SideOnly(Side.CLIENT)
	public static void useSkill() {
		networkWrapper.sendToServer(new MessageAlchemyCircle());
	}

	
	
}
