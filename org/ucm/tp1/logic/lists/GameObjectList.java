package org.ucm.tp1.logic.lists;

import java.util.ArrayList;
import org.ucm.tp1.logic.gameobjects.GameObject;

public class GameObjectList {
	private ArrayList<GameObject> gameobjects;

	public GameObjectList() {
		this.gameobjects = new ArrayList<GameObject>();
	}

	public void addObject(GameObject o) {
		gameobjects.add(o);		
	}

	public GameObject objectPosition(int x, int y) {
		int cont = 0;
		boolean encontrado = false;
		GameObject o = null;
		
		while(cont < gameobjects.size() && !encontrado) {
			if (gameobjects.get(cont).isThere(x, y) && gameobjects.get(cont).getResistence() > 0) {
				encontrado = true;
				o = gameobjects.get(cont);
			}
			else
				cont++;
		}
		
		return o;
	}

	public String toString(int x, int y) {
		String cad = "";
		
		if (objectPosition(x, y) != null) {
			cad = objectPosition(x, y).toString();
		}
		
		return cad;
	}
	
	public void removeDeadObjects() {
		for (int i = 0; i< gameobjects.size(); i++) {
			if (gameobjects.get(i).getResistence() <= 0) {
				gameobjects.remove(i);
			}
		}
	}

	public void mover() {
		for (int i = 0; i < gameobjects.size(); i++) {	
			//aumentarCiclosMovidos(i);
			gameobjects.get(i).mover();
		}
		/*for (int i = gameobjects.size() - 1; i >= 0; i--) {	
			//aumentarCiclosMovidos(i);
			gameobjects.get(i).mover();
		}*/
	}

	public void attack() {
		for (int i = 0; i < gameobjects.size(); i++)
			gameobjects.get(i).attack();
	}

	public void killThemAll() {
		for (int i = 0; i < gameobjects.size(); i++)
			gameobjects.get(i).receiveLightFlash();
	}

	public void garlicPush() {
		/*for (int i = 0; i < gameobjects.size(); i++)
			gameobjects.get(i).receiveGarlicPush();*/
		for (int i = gameobjects.size() - 1; i >= 0; i--)
			gameobjects.get(i).receiveGarlicPush();
	}

	public String objectsSerialize() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < gameobjects.size(); i++) {
			sb.append(gameobjects.get(i).objectSerialize() + "\n");
		}
		
		return sb.toString();
	}

}
