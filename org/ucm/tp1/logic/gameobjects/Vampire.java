package org.ucm.tp1.logic.gameobjects;

import java.util.Random;

import org.ucm.tp1.logic.Game;
import org.ucm.tp1.logic.Level;


public class Vampire extends GameObject {
	protected int ciclosMovimientos;		
	public final static int VELOCIDAD = 2; 
	public static int VAMPIROS_TABLERO = 0;
	public static boolean VENCEDOR = false;
	public static int VAMPIROS_CREADOS = 0; //calcular

	
	public Vampire(int x, int y, Game game) {
		super(x, y, game, 5, "V");
		this.ciclosMovimientos = 0;
		VAMPIROS_CREADOS++;
		VAMPIROS_TABLERO++;
	}
	
	public Vampire(int x, int y, Game game, String letra) {
		super(x, y, game, 5, letra);
		this.ciclosMovimientos = 0;
		VAMPIROS_CREADOS++;
		VAMPIROS_TABLERO++;
	}
	
	public Vampire(int x, int y, Game game, String letra, int resistence, int pasos) {
		super(x, y, game, resistence, letra);
		this.ciclosMovimientos = pasos;
	}
	
	

	public void recibeDiparo(int dagno) {
		this.resistence = this.resistence - dagno;
	}
	
	public static void resetStatics() {
		VAMPIROS_TABLERO = 0;
		VAMPIROS_CREADOS = 0;
		Dracula.setDraculaIsAlive();
	}
	

	
	public static int getRemainingVampires(Level level) {	
		return level.getNumberOfVampires() - VAMPIROS_CREADOS;
	}
	
	public static boolean shouldAddVampire(Random rand, Level level) {
		return (getRemainingVampires(level) > 0 && rand.nextDouble() < level.getVampireFrequency());
	}

	@Override
	public void mover() {
		ciclosMovimientos++;
		if (game.isPositionEmpty(this.x - 1, this.y) && this.x >= 0) {
			avanzar();
		}
		else 
			ciclosMovimientos = 1;
	}

	private void avanzar() {
		if (ciclosMovimientos % VELOCIDAD == 0)
			this.x--;
		//Miramos si hay un vampiro vencedor
		if (this.x < 0) {
			VENCEDOR = true;
		}
		
	}

	@Override
	public void attack() {
		if (isAlive () ) {
			IAttack other = game.getAttackableInPosition(x - 1, y);
			if (other != null) {
				if (other. receiveVampireAttack(HARM))
					ciclosMovimientos = 0;
			}
		}
	}

	@Override
	public boolean receiveSlayerAttack(int damage) {
		this.resistence = this.resistence - damage;
		if (this.resistence <= 0)
			VAMPIROS_TABLERO--;
		
		return true;
	}
	
	@Override
	public boolean receiveLightFlash() {
		this.resistence = 0;
		VAMPIROS_TABLERO--;
		
		return true;
	}
	
	@Override
	public boolean receiveGarlicPush() {
		boolean b = false;
		
		if(x == game.numColumnas()-1) {
			this.resistence = 0;
			VAMPIROS_TABLERO--;
			
			b = true;
		}
		else if(game.isPositionEmpty(x + 1, y)) {
			this.x = this.x + 1;
			b = true;
			ciclosMovimientos = 0;
		}
		
		return b;
	}

	public static boolean esVencedor() {
		return VENCEDOR;
	}

	public static int vampirosEnTablero() {
		return VAMPIROS_TABLERO;
	}
	
	@Override
	public String objectSerialize() {
		return "V;" + x + ";" + y + ";" + resistence + ";" + ciclosMovimientos;		
	}

	public static void remainingVampiresDeserialize(int restantes, int vampiresNumber, int enTablero) {
		VAMPIROS_CREADOS  = vampiresNumber - restantes;
		VAMPIROS_TABLERO = enTablero;
	}
}
