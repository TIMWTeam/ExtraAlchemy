package yichen.extraalchemy.util;

import net.minecraft.util.DamageSource;

public class DamageSourceExtraAlchemy {

	public static final DamageSource SELF_HARM = new DamageSource("self_harm").setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource DISSOLVE = new DamageSource("dissolve").setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource DISSOLVE_DRING = new DamageSource("dissolve_dring").setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode();
	
}
