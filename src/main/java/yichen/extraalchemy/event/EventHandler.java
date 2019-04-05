package yichen.extraalchemy.event;

import net.minecraftforge.event.world.BlockEvent.PortalSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

    @SubscribeEvent
    public static void onPortalSpawnEvent(PortalSpawnEvent event) {
        event.setCanceled(false);
    }
}
