package yichen.extraalchemy.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DamageSourceExtraAlchemy extends DamageSource{
	


	public static final DamageSource SELF_HARM =(new DamageSourceExtraAlchemy("self_harm")).setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource DISSOLVE =(new DamageSourceExtraAlchemy("dissolve")).setDamageBypassesArmor().setDamageIsAbsolute();

	private String damageType;
	public DamageSourceExtraAlchemy(String damageTypeIn) {
		super(damageTypeIn);
		damageType=damageTypeIn;
	}
    @Override
    public ITextComponent getDeathMessage(EntityLivingBase livingBase) {
        return new TextComponentString(TextHelper.localizeEffect("death.attack.extraalchemy."+damageType, livingBase.getName()));
    }
}
