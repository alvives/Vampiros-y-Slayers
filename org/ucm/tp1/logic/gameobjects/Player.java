package org.ucm.tp1.logic.gameobjects;


import java.util.Random;

//hay que hacer una referencia a random

public class Player {
	private int coins;
	

	public Player() {
		this.coins = 50;
	}
	
	public void updateMonedas(Random rand) {
		if(rand.nextFloat() > 0.5)
			coins += 10;
	}
	
	public int numMonedas() {
		return coins;
	}
	
	public void gastoSlayer() {
		coins = coins - Slayer.COSTE;
	}
	
	public void gastoZ(int z) {
		coins = coins - z;
	}
	
	public void bankBloodGift (int z) {
		coins = coins + z*10/100;
	}

	public void superCoins(int aumento) {
		coins = coins + aumento;
	}

	public void coinsDeserialize(int monedas) {
		this.coins = monedas;
	}
}
