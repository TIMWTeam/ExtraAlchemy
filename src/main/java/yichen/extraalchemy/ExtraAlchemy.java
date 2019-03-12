package yichen.extraalchemy;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import yichen.extraalchemy.base.items.ItemCoalDust;
import yichen.extraalchemy.init.ItemLoader;
import yichen.extraalchemy.proxy.CommonProxy;

@Mod(modid = ExtraAlchemy.MODID, name = ExtraAlchemy.NAME, version = ExtraAlchemy.VERSION,acceptedMinecraftVersions = "1.12.2")
public class ExtraAlchemy{

	public static final String MODID = "extraalchemy"; //mod_id
	public static final String NAME = "ExtraAlchemy"; //mod名称
	public static final String VERSION = "1.0.0"; //mod版本
	
	@SidedProxy(clientSide = "yichen.extraalchemy.proxy.ClientProxy", 
            serverSide = "yichen.extraalchemy.proxy.CommonProxy")
    public static CommonProxy proxy;
	
	@Instance(ExtraAlchemy.MODID)
    public static ExtraAlchemy instance;
	
    private Logger logger;
    public static Logger log = LogManager.getLogger(NAME);
    
    public static final CreativeTabs TAB_base = new CreativeTabs(MODID + ".creativeTab") {
        @Override
        public ItemStack getTabIconItem()
        {
            return new ItemStack(ItemLoader.itemCoalDust);
        }
    };
	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }
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