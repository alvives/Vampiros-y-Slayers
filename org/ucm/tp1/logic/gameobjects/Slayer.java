package org.ucm.tp1.logic.gameobjects;

import org.ucm.tp1.logic.Game;

public class Slayer extends GameObject {
	public final static int FRECUENCIA = 1;
	public final static int COSTE = 50;
	
	
	public Slayer(int x, int y, Game game) {
		super(x, y, game, 3, "S");
	}
	
	public Slayer(int x, int y, Game game, int resistence) {
		super(x, y, game, resistence, "S");
	}


	public void recibeDagno(int dagno) {
		this.resistence = this.resistence - dagno;
	}

	@Override
	public void mover() {
		
	}
	
	@Override
	public void attack() {			
		if (isAlive () ) {
			int cont = 0;
			boolean encontrado = false;
			IAttack other;
			
		 	while(cont < game.numColumnas() && !encontrado) { 
				other = game.getAttackableInPosition(cont, y);
			
				if (other != null)
					encontrado = other.receiveSlayerAttack(HARM);
				cont++;
			}
		}		
	}
	
	@Override
	public boolean receiveVampireAttack(int damage) {
		this.resistence = this.resistence - damage;
		
		return true;
	}
	
	@Override
	public boolean receiveDraculaAttack() {
		this.resistence = 0;
		
		return true;
	}


	public static int coste() {
		return COSTE;
	}
	
	@Override
	public String objectSerialize() {
		return "S;" + x + ";" + y + ";" + resistence;		
	}

}
