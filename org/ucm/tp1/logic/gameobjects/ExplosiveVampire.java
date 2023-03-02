package org.ucm.tp1.logic.gameobjects;

import org.ucm.tp1.logic.Game;

public class ExplosiveVampire extends Vampire {

	public ExplosiveVampire(int x, int y, Game game) {
		super(x, y, game, "EV");
	}
	public ExplosiveVampire(int x, int y, Game game, int resistence, int pasos) {
		super(x, y, game, "EV", resistence, pasos);
	}
	
	@Override
	public boolean receiveSlayerAttack(int damage) {
		this.resistence = this.resistence - damage;
		if (this.resistence <= 0) {
			explotar();
			VAMPIROS_TABLERO--;
		}
		
		return true;
	}
	
	private void explotar () {
		for(int i = x - 1; i <= x + 1; i++) {
			for(int j = y - 1; j <= y + 1; j++) {
				if(x != i || y != j) {
					IAttack other = game.getAttackableInPosition(i,j);
					if (other != null) {
						other. receiveSlayerAttack(HARM);
						ciclosMovimientos--;
					}
				}
			}
		}
	}
	
	@Override
	public String objectSerialize() {
		return "EV;" + x + ";" + y + ";" + resistence + ";" + ciclosMovimientos;		
	}
	
}
