package yichen.extraalchemy.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {
	private static Configuration config;
	private static Logger logger;
	private String comment;

	public static float alchemyArrayEfficiency;
	public static int bloodBottleDrinking;
	
	public ConfigLoader(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		load();
		
		
	}
	private void load() 
	{
		logger.info("Started loading config.\n开始配置文件加载 ");

		comment = "Alchemy array basic work efficiency. [range: 0.1 ~ 5.0, default: 1.0] \n炼金法阵基础工作效率。[范围：0.1 ~ 5.0，默认：1.0]";
		alchemyArrayEfficiency = (float) config.get(Configuration.CATEGORY_GENERAL, "alchemyArrayEfficiency", 1.0, comment, 0.1, 5.0).getDouble();

		comment = "Blood bottle drinking speed.  \n血瓶饮用速度。";
		bloodBottleDrinking = config.getInt("bloodBottleDrinking", Configuration.CATEGORY_GENERAL, 20, 1, 60, comment, "bloodBottleDrinking");

		config.save();
		
		logger.info("Finished loading config.\n完成配置文件加载 ");
		
	}
	
	public static Logger logger()
	{
		return logger;
	}
}
