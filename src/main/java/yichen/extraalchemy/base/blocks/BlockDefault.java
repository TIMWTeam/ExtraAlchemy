package yichen.extraalchemy.base.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.util.StringUtils;

public class BlockDefault extends Block {

	public BlockDefault(String name, Material material) {
		super(material);
		init(name);
	}
	
	void init(String name) {
		this.setRegistryName(name);
		this.setUnlocalizedName(ExtraAlchemy.MODID+"."+name);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
	}
	
}
