package yichen.extraalchemy.base.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.util.StringUtils;

public class BlockDefault extends Block {

	public BlockDefault(String name, Material material) {
		super(material);
		String[] sp = name.split(" ");
		String registry = StringUtils.mergeWithUnderline(sp);
		String unlocalized = StringUtils.mergeWithUpper(sp);
		init(registry, unlocalized);
	}
	
	public BlockDefault(String registry, String unlocalized, Material material) {
		super(material);
		init(registry, unlocalized);
	}
	
	void init(String registry, String unlocalized) {
		this.setRegistryName(registry);
		this.setUnlocalizedName(unlocalized);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}
	
}
