package org.ucm.tp1.logic.gameobjects;

import org.ucm.tp1.logic.Game;

public class BankBlood extends GameObject{
	private int z;

	public BankBlood(int x, int y, Game game, int z) {
		super(x, y, game, 1, "B");
		this.z = z;
	}
	
	public BankBlood(int x, int y, Game game, int resistence, int z) {
		super(x, y, game, resistence, "B");
		this.z = z;
	}

	public String toString () {
		return "B" + " [" + z + "]";
	}
	
	@Override
	public void attack() {	}

	@Override
	public void mover() {
		if (this.resistence > 0)
			game.bankBloodGift(this.z);
	}

	@Override
	public boolean receiveVampireAttack(int damage) {
		this.resistence = 0;
		
		return true;
	}
	
	@Override
	public boolean receiveDraculaAttack() {
		this.resistence = 0;
		
		return true;
	}
	@Override
	public String objectSerialize() {
		return "B;" + x + ";" + y + ";" + resistence + ";" + z;		
	}
}
