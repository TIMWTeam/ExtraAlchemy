package yichen.extraalchemy.blocks.alchemy_circle;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import yichen.extraalchemy.ExtraAlchemy;
import yichen.extraalchemy.blocks.alchemy_circle.tile.TileAlchemyCircle;
import yichen.extraalchemy.init.ItemLoader;

public class BlockAlchemyCircle extends Block{
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.1, 1);
	
	public BlockAlchemyCircle() {
        super(Material.CLOTH);
        this.setHardness(0);
        this.setLightOpacity(3);
        this.setLightLevel(0.5F);
		this.setCreativeTab(ExtraAlchemy.TAB_base);
    }
	@Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        // No-op
    }
	//����߿��С
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }
	//�Ƿ�Ϊ��ͨ����
	@Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
	//�Ƿ�Ϊ��������
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    //�Ƿ��ʹ��ҵ�����Ϣ
    @Override
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }
    //�Ƿ�Ϊ��͸������
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    //��ȡ��Ⱦ����
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    //����ʵ��
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
    //����ģʽ�м���ȡ������Ʒ
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ItemLoader.itemCoalDust);
    }
    //�ƻ�����
    @Override
    public int quantityDropped(Random random) {
        return 0;
    }
    //�ƻ�����
    @Override
    public void breakBlock(World world, BlockPos blockPos, IBlockState blockState) {
        TileAlchemyCircle alchemyArray = (TileAlchemyCircle) world.getTileEntity(blockPos);
        if (alchemyArray != null) {
        	 //�ڲ���Ʒ����
        }

        super.breakBlock(world, blockPos, blockState);
    }
    //����ʵ��
    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileAlchemyCircle();
    }
	

}
