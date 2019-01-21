package yichen.extraalchemy;
import org.apache.logging.log4j.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import yichen.extraalchemy.init.ItemLoader;
import yichen.extraalchemy.items.ItemCoalDust;
import yichen.extraalchemy.proxy.CommonProxy;

@Mod(modid = ExtraAlchemy.MODID, name = ExtraAlchemy.NAME, version = ExtraAlchemy.VERSION,acceptedMinecraftVersions = "1.12.2")
public class ExtraAlchemy{

	public static final String MODID = "extraalchemy"; //mod_id
	public static final String NAME = "ExtraAlchemy"; //mod名字
	public static final String VERSION = "1.0.0"; //mod版本
	
	@SidedProxy(clientSide = "yichen.extraalchemy.proxy.ClientProxy", 
            serverSide = "yichen.extraalchemy.proxy.CommonProxy")
    public static CommonProxy proxy;
	
	@Instance(ExtraAlchemy.MODID)
    public static ExtraAlchemy instance;
	
    private Logger logger;

    
    public static final CreativeTabs TAB_base = new CreativeTabs(MODID + ".creativeTab") {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemLoader.itemCoalDust);
        }
    };
    //初始化之前
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }
	//初始化进行时
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
    //初始化结束
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
    public Logger getLogger()
    {
        return logger;
    }
}