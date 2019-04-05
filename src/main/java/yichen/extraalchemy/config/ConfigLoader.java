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
		config.save();
		
		
	}
	private void load() 
	{
		logger.info("Started loading config.\n开始配置文件加载 ");

		comment = "Alchemy array basic work efficiency. \n炼金法阵基础工作效率。";
		alchemyArrayEfficiency = config.getFloat("alchemyArrayEfficiency", Configuration.CATEGORY_GENERAL, 1.0f, 0.1f, 5.0f, comment);

		comment = "Blood bottle drinking speed.\n血瓶饮用速度。";
		bloodBottleDrinking = config.getInt("bloodBottleDrinking", Configuration.CATEGORY_GENERAL, 20, 1, 60, comment, "bloodBottleDrinking");
		
		logger.info("Finished loading config.\n完成配置文件加载 ");
		
	}
	
	public static Logger logger()
	{
		return logger;
	}
}
