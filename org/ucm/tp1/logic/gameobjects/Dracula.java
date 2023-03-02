package org.ucm.tp1.logic.gameobjects;

import java.util.Random;

import org.ucm.tp1.logic.Game;
import org.ucm.tp1.logic.Level;

public class Dracula extends Vampire {
	public static boolean DRACULA_ALIVE = false;

	public Dracula(int x, int y, Game game) {
		super(x, y, game, "D");
		DRACULA_ALIVE = true;
	}
	public Dracula(int x, int y, Game game, int resistence, int pasos) {
		super(x, y, game, "D", resistence, pasos);
		DRACULA_ALIVE = true;
	}

	public static boolean shouldAddDracula(Random rand, Level level){		
		return (!DRACULA_ALIVE && getRemainingVampires(level) > 0 && rand.nextDouble() < level.getVampireFrequency());
	}
	
	@Override
	public void attack() {
		if (isAlive () ) {
			IAttack other = game.getAttackableInPosition(x - 1, y);
			if (other != null) {
				other.receiveDraculaAttack();
				//if(other.receiveDraculaAttack())
					//ciclosMovimientos--;
			}
		}
	}	
	
	@Override
	public boolean receiveSlayerAttack(int damage) {
		this.resistence = this.resistence - damage;
		if (this.resistence <= 0) {
			VAMPIROS_TABLERO--;
			DRACULA_ALIVE = false;
		}
		
		return true;
	}
	
	@Override
	public boolean receiveLightFlash() {
		return false;
	}
	
	@Override
	public boolean receiveGarlicPush() {
		boolean b = false;
		
		if(x == game.numColumnas()-1) {
			this.resistence = 0;
			VAMPIROS_TABLERO--;
			DRACULA_ALIVE = false;
			
			b = true;
		}
		else if(game.isPositionEmpty(x + 1, y)) {
			this.x = this.x + 1;
			b = true;
			ciclosMovimientos = 0;
		}
		
		return b;
	}

	public static boolean draculaIsAlive() {
		return DRACULA_ALIVE;
	}

	public static void setDraculaIsAlive() {
		DRACULA_ALIVE = false;
	}
	
	@Override
	public String objectSerialize() {
		return "D;" + x + ";" + y + ";" + resistence + ";" + ciclosMovimientos;		
	}
}
