package yichen.extraalchemy.base.items;

import net.minecraft.item.Item;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.util.StringUtils;

public class ItemDefault extends Item {

	public ItemDefault(String name) {
		String[] sp = name.split(" ");
		String registry = StringUtils.mergeWithUnderline(sp);
		String unlocalized = StringUtils.mergeWithUpper(sp);
		init(registry, unlocalized);
	}
	
	public ItemDefault(String registry, String unlocalized) {
		init(registry, unlocalized);
	}
	
	void init(String registry, String unlocalized) {
		this.setRegistryName(registry);
		this.setUnlocalizedName(unlocalized);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}
	
}
