package yichen.extraalchemy.event;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.network.EANetworkHandler;

@EventBusSubscriber(modid = ExtraAlchemy.MODID, value = Side.CLIENT)
public class EAClientEventHandler {
	private static KeyBinding keyUseSkill = new KeyBinding(ExtraAlchemy.MODID+"useskill", Keyboard.KEY_M, ExtraAlchemy.MODID+".key");
	
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
		System.out.println("按键");
		if(keyUseSkill.isPressed())
		{
			System.out.println("M键");
			EANetworkHandler.useSkill();
		}
	}
	
	public static void init() {
		ClientRegistry.registerKeyBinding(keyUseSkill);
	}
}
