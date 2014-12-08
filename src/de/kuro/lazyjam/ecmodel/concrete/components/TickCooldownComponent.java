package de.kuro.lazyjam.ecmodel.concrete.components;

import de.kuro.lazyjam.cdiutils.annotations.Update;

/**
 * Counts ticks for you
 * @author kuro
 *
 */
public abstract class TickCooldownComponent {
	
	protected int cooldownTicks;
	protected int currentTicks;
	public int reduction;
	public boolean autoFire;
	
	public TickCooldownComponent(int cooldownTicks) {
		this.cooldownTicks = cooldownTicks;
		this.currentTicks = 0;
	}
	
	@Update
	public void count() {
		this.currentTicks++;
	}
	
	public boolean canFire() {
		if(this.currentTicks >= cooldownTicks - reduction) {
			this.currentTicks = 0;
			return true;
		}
		return false;
	}
	
}
