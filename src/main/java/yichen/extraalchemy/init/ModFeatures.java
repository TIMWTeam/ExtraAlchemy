package yichen.extraalchemy.init;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ModFeatures {

    public static void init(FMLPreInitializationEvent event) {

    }

    public static void initCapabilities() {

    }

    public static void initRegistry() {

    }

    public static void initEvents() {

    }

    public static void initNetwork() {

    }

    public static void initWorlds() {


    }

    public static class Network {
        private static int packetId = 0;
        public static SimpleNetworkWrapper WRAPPER = null;

        private static int nextID() {
            return packetId++;
        }

    }
}
