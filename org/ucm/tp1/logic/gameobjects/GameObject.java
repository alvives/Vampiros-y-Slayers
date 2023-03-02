package org.ucm.tp1.logic.gameobjects;

import org.ucm.tp1.logic.Game;

public abstract class GameObject implements IAttack{
	protected int x, y;	
	protected int resistence;
	protected Game game;
	private String letra; 
	public static int HARM = 1;

	
	public GameObject(int x, int y, Game game, int resistence, String letra) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.resistence = resistence;
		this.letra = letra;
	}

	public abstract void mover();
	
	protected boolean isAlive() {
		return resistence > 0;
	}
	
	public String toString () {
		return letra + " [" + resistence + "]";
	}

	public boolean isThere(int x, int y) {	
		return (x == this.x) && (y == this.y);
	}

	public int getResistence() {
		return this.resistence;
	}

	public abstract String objectSerialize();

}
