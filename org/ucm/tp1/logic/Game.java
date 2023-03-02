package org.ucm.tp1.logic;

import java.util.Random;

import org.ucm.tp1.logic.gameobjects.BankBlood;
import org.ucm.tp1.logic.gameobjects.Dracula;
import org.ucm.tp1.logic.gameobjects.ExplosiveVampire;
import org.ucm.tp1.logic.gameobjects.GameObjectBoard;
import org.ucm.tp1.logic.gameobjects.IAttack;
import org.ucm.tp1.logic.gameobjects.Vampire;

import excepciones.CommandExecuteException;
import excepciones.DraculaIsAliveException;
import excepciones.NoMoreVampiresException;
import excepciones.NotEnoughCoinsException;
import excepciones.UnvalidPositionException;

import org.ucm.tp1.logic.gameobjects.Slayer;
import view.*;

import view.GamePrinter;

public class Game implements IPrintable {
	private Level level;
	public Random rand;		
	private GameObjectBoard board;
	private int numCiclos;
	private Long seed;
	private GamePrinter gamePrinter;
	private boolean exit = false;
	
	
	public Game(Long seed, Level level) {
		this.seed = seed;
		this.rand = new Random(seed);
		this.level= level;
		this.board = new GameObjectBoard();
		this.numCiclos = 0;
		this.gamePrinter = new GamePrinter(this, level.getDimX(), level.getDimY());
	}
	
	public boolean addVampire(int x, int y, String letra) throws CommandExecuteException {
		boolean b = true;
		
		if(Vampire.getRemainingVampires(this.level) == 0) {
			b = false;
			throw new NoMoreVampiresException("[ERROR]: No more remaining vampires left");
		}	
		else if(x >= numColumnas() || y >= numFilas()) {
			b = false;
			throw new UnvalidPositionException("[ERROR]: Position (" + x + ", " + y + "): Unvalid position");
		}
		else if (!board.isPositionEmpty(x, y)) {
			b = false;
			throw new UnvalidPositionException("[ERROR]: Position (" + x + ", " + y + "): Unvalid position");
		}
		else {
			if("v".equals(letra))
				board.addObject(new Vampire(x, y, this));
			else if ("d".equals(letra)) {
				if(!Dracula.draculaIsAlive())
					board.addObject(new Dracula(x, y, this));
				else {
					b = false;
					throw new DraculaIsAliveException("[ERROR]: Dracula is already on board");
				}
			}
			else if ("e".equals(letra))
				board.addObject(new ExplosiveVampire(x, y, this));		
		}
		
		return b;
	}

	public void addVampireIfNeeded() throws CommandExecuteException{
		boolean b=false;
		if (Vampire.shouldAddVampire(rand, level)) {
			int x = level.getDimX() - 1;
			int y = rand.nextInt(level.getDimY());
			if (board.isPositionEmpty(x,y)) {
				board.addObject(new Vampire(x, y, this));
			}
			/*Lo comentamos para que salga igual que los casos de prueba (el hard s1 0)
			 * else
				System.out.println("[DEBUG] vampire blocked");*/
		}
		if (Dracula.shouldAddDracula(rand, level)) {
			int x = level.getDimX() - 1;
			int y = rand.nextInt(level.getDimY());
			if (board.isPositionEmpty(x,y)) {
				board.addObject(new Dracula(x, y, this));
			}

		}
		else if(Dracula.draculaIsAlive())
			b=true;
		if (Vampire.shouldAddVampire(rand, level)) {
			int x = level.getDimX() - 1;
			int y = rand.nextInt(level.getDimY());
			if (board.isPositionEmpty(x,y)) {
				board.addObject(new ExplosiveVampire(x, y, this));
			}
			/*else
				System.out.println("[DEBUG] vampire blocked");*/
		}
		if(b)
			throw new DraculaIsAliveException("");
	}
	
	public boolean addSlayer (int col, int fil) throws CommandExecuteException {
		boolean b = true;
		
		//Que tenga monedas
		if (board.numMonedas() < Slayer.coste()) {
			b = false;
			throw new NotEnoughCoinsException("[ERROR]: Defender cost is 50: Not enough coins");
		}
		//Que no haya otro objeto
		else if (!board.isPositionEmpty(col, fil) || col >= level.getDimX() -1 || fil >= level.getDimY() || col < 0 || fil < 0) {
			b = false;
			throw new UnvalidPositionException("[ERROR]: Position ("+ col + ", " + fil + "): Unvalid position");
		}
		else {
			board.addObject(new Slayer(col, fil, this));
			board.gastoSlayer();
		}
			
		return b;
	}
	
	public void update() {
		board.mover();						// Los vampiros avanzan cada 2 turnos
		board.updateMonedas(this.rand);		// Actualiza monedas
		this.numCiclos++;		
	}
	
	public Random getRand() {
		return rand;
	}
	
	public String dameStringPos(int y, int x) {
		return board.dameStringPos(x, y);
	}

	public int numColumnas () {
		return level.getDimX ();
	}
	public int numFilas () {
		return level.getDimY();
	}
	
	public Level devolverLevel() {
		return this.level;	
	}
	
	public void attack() {
		board.attack();	
	}
	public void removeDeadObjects() {
		board.removeDeadObjects();
	}
	public boolean isFinished() {
		boolean acaba = false;
		
		if(this.exit)
			acaba = true;
		// Si ya no quedan vampiros (ni por salir ni en el tablero)
		else if (Vampire.getRemainingVampires(level) == 0 && !board.vampirosEnTablero()) {
			acaba = true;
			this.numCiclos--;	
		}
		//Si un vampiro llega al final
		else if (Vampire.esVencedor()) {
			acaba = true;
			this.numCiclos--;	//El vampiro que gana sale dejuego, por lo que no cuenta cuando avanzó
		}
				
		return acaba;
	}
	
	public int numMonedas() {
		return board.numMonedas();
	}
	
	public int numCiclos() {
		return this.numCiclos;
	}

	public int getNumberOfVampires() {
		return level.getNumberOfVampires();
	}

	public boolean isPositionEmpty(int x, int y) {
		return board.isPositionEmpty(x, y);
	}

	public void reset() {
		this.rand = new Random(this.seed);
		this.board = new GameObjectBoard();
		this.numCiclos = 0;
		//.gamePrinter = new GamePrinter(this, level.getDimX(), level.getDimY());
		//Reseteamos las globales
		Vampire.resetStatics();
	}

	public String getWinnerMessage() {
		if (Vampire.esVencedor()) {
			return "Vampires win!";
		}
		else if(Vampire.getRemainingVampires(level) == 0 && !board.vampirosEnTablero()) {
			return "Player wins";
		}
		else {
			return "Nobody wins...";
		}
			
	}

	public void exit() {
		this.exit = true;
	}

	public IAttack getAttackableInPosition(int x, int y) {	
		return board.getAttackableInPosition(x, y);
	}

	//Del IPrintable
	@Override
	public String getPositionToString(int x, int y) {		
		return board.getPositionToString(x,y);
	}

	//Del IPrintable
	@Override
	public String getInfo() {	
		String info = "Number of cycles: " + numCiclos() + "\n" + "Coins: " + numMonedas() + "\n" + 
						"Remaining vampires: " +  Vampire.getRemainingVampires(this.level) +"\n" + 
						"Vampires on the board: " + Vampire.vampirosEnTablero() + "\n";
		if (Dracula.draculaIsAlive())
			info = info + "Dracula is alive" + "\n";
		
		return  info;
	}
	
	@Override
	public String toString() {
		return gamePrinter.toString();
	}

	public void bankBloodGift(int z) {
		board.bankBloodGift(z);
	}

	public boolean addBankBlood(int x, int y, int z) throws CommandExecuteException {
		boolean b = false;
		
		if(x < level.getDimX() -1 && y < level.getDimY() && x >= 0 && y >= 0) {	//No puede bloquear la entrada de vampiros
			b = CouldAddBankBlood(x, y, z); 	//Que tenga dinero y no este ocupada la casilla	
		}
		else {
			throw new UnvalidPositionException("[ERROR]: Invalid position");
		}

		
		return 	b;
	}

	private boolean CouldAddBankBlood(int x, int y, int z) throws CommandExecuteException {
		boolean b = true;
		
		//Que tenga monedas
		if (board.numMonedas() < z) {
			b = false;
			throw new NotEnoughCoinsException ("[ERROR]: Not enough coins");
		}
		//Que no haya otro objeto
		else if (!board.isPositionEmpty(x, y)) {
			b = false;
			throw new UnvalidPositionException ("[ERROR]: Invalid position");
		}
		else {
			board.addObject(new BankBlood(x, y, this, z));
			board.gastoZ(z);
		}
			
		return b;
	}

	public boolean killThemAll(int z) throws NotEnoughCoinsException {
		boolean b = true;
		
		if (board.numMonedas() < z) {
			b = false;
			throw new NotEnoughCoinsException ("[ERROR]: Not enough coins");
		}
		else {
			board.killThemAll();
			board.gastoZ(z);
		}
		
		return b;
	}

	public boolean garlicPush(int z) throws NotEnoughCoinsException {
		boolean b = true;
		
		if (numMonedas() < z) {
			b = false;
			throw new NotEnoughCoinsException("[ERROR]: Not enough coins");
		}
		else {
			board.garlicPush();
			board.gastoZ(z);
		}
		
		
		return b;
	}

	public void superCoins(int aumento) {
		board.superCoins(aumento);
	}

	public String serialize() {
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append("Cycles: " + numCiclos + "\n");
	    sb.append("Coins: " + numMonedas() + "\n");
	    sb.append("Level: " + level.levelName() + "\n");
	    sb.append("Remaining Vampires: " + Vampire.getRemainingVampires(this.level) + "\n");
	    sb.append("Vampires on Board: " +  Vampire.vampirosEnTablero() + "\n" + "\n");
	    //Ahora ponemos los objetos:
	    sb.append("Game Object List: " + "\n");
	    sb.append(board.objectsSerialize());
	
		return 	sb.toString();
	}

	public void cycleDeserialize(String s) {
		s = s.replaceAll("Cycles: ", "");
		numCiclos = Integer.parseInt(s);
	}
	
	public void levelDeserialize(String s) {
		s = s.replaceAll("Level: ", "");
		level = Level.parse(s);
		this.gamePrinter = new GamePrinter(this, this.level.getDimX(), this.level.getDimY());
	}

	public void coinsDeserialize(String s) {
		s = s.replaceAll("Coins: ", "");
		board.coinsDeserialize(Integer.parseInt(s));
	}
	public void remainingVampiresDeserialize(String restantes, String enTablero) {
		restantes = restantes.replaceAll("Remaining Vampires: ", "");
		enTablero = enTablero.replaceAll("Vampires on Board: ", "");
		Vampire.remainingVampiresDeserialize(Integer.parseInt(restantes), level.getNumberOfVampires(), Integer.parseInt(enTablero));
	}

	public void deserialize() {
		this.rand = new Random(this.seed);
		this.board = new GameObjectBoard();
	}

	public void objectDeserialize(String s) {
		String[] objeto = s.split(";");
		if(objeto[0].equals("V")) {
			board.addObject(new Vampire(Integer.parseInt(objeto[1]), Integer.parseInt(objeto[2]), this, "V", Integer.parseInt(objeto[3]), Integer.parseInt(objeto[4])));
		}
		else if(objeto[0].equals("EV")) {
			board.addObject(new ExplosiveVampire(Integer.parseInt(objeto[1]), Integer.parseInt(objeto[2]), this, Integer.parseInt(objeto[3]), Integer.parseInt(objeto[4])));
		}
		else if(objeto[0].equals("D")) {
			board.addObject(new Dracula(Integer.parseInt(objeto[1]), Integer.parseInt(objeto[2]), this, Integer.parseInt(objeto[3]), Integer.parseInt(objeto[4])));
		}
		else if(objeto[0].equals("S")) {
			board.addObject(new Slayer(Integer.parseInt(objeto[1]), Integer.parseInt(objeto[2]), this, Integer.parseInt(objeto[3])));
		}
		else if(objeto[0].equals("B")) {
			board.addObject(new BankBlood(Integer.parseInt(objeto[1]), Integer.parseInt(objeto[2]), this, Integer.parseInt(objeto[3]), Integer.parseInt(objeto[4])));
		}
	}
}
