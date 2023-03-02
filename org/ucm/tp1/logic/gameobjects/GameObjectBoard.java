package org.ucm.tp1.logic.gameobjects;

import java.util.Random;

import org.ucm.tp1.logic.lists.GameObjectList;

public class GameObjectBoard {
	private GameObjectList gameObjectList;
	private Player player;
	
	
	public GameObjectBoard(/*VampireList vampireList, SlayerList slayerList*/) {
		this.gameObjectList = new GameObjectList();
		this.player=new Player();
	}
	
	public void addObject (GameObject o) {
		gameObjectList.addObject(o);
	}

	
	public void updateMonedas(Random rand) {
		player.updateMonedas(rand);
	}
	
	public boolean isPositionEmpty(int x, int y) {
		boolean b = false;
		
		if ( gameObjectList.objectPosition(x, y) == null) {
			b = true;
		}
			
		return b;
	}


	public void mover() {
		gameObjectList.mover();
	}
	
	public void gastoSlayer() {
		player.gastoSlayer();
	}
	
	public boolean vampirosEnTablero (){
		return Vampire.vampirosEnTablero() > 0;
	}

	
	//Me dice lo que hay en una casilla del tablero;
	public String dameStringPos(int x, int y) {
		return gameObjectList.toString(x, y);
	}
	
	public int numMonedas() {
		return player.numMonedas();
	}
	
	public void attack() {
		gameObjectList.attack();
	}
	
	public void removeDeadObjects() {
		gameObjectList.removeDeadObjects();
	}

	public IAttack getAttackableInPosition(int x, int y) {
		return gameObjectList.objectPosition(x, y);
	}

	public String getPositionToString(int x, int y) {
		return gameObjectList.toString(x,y);
	}

	
	public void bankBloodGift(int z) {
		player.bankBloodGift(z);
	}	
	public void gastoZ(int z) {
		player.gastoZ(z);
	}

	public void killThemAll() {
		gameObjectList.killThemAll();
	}

	public void garlicPush() {
		gameObjectList.garlicPush();
	}

	public void superCoins(int aumento) {
		player.superCoins(aumento);
	}

	public String objectsSerialize() {
		return gameObjectList.objectsSerialize();
	}

	public void coinsDeserialize(int monedas) {
		player.coinsDeserialize(monedas);
	}	
}
