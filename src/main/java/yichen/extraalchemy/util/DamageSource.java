package yichen.extraalchemy.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DamageSource extends net.minecraft.util.DamageSource{
	
	public static final DamageSource SELF_HARM = new DamageSource();

    public DamageSource() {
        super("YiChen_");
        setDamageBypassesArmor();
        setDamageIsAbsolute();
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase livingBase) {
        return new TextComponentString(TextHelper.localizeEffect("death.attack.extraalchemy.self_harm", livingBase.getName()));
    }
}
