package org.ucm.tp1.control.commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.ucm.tp1.logic.Game;

import excepciones.CommandParseException;

public class LoadCommand extends Command {
	protected static final String NAME = "load";
	protected static final String SHORTCUT = "o";
	private static final String DETAILS = "l[o]ad <filename>";
	private static final String HELP = "Load the state of the game from a file.";
	private String fileName;
	
	public LoadCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game)  {
		Scanner sc = null;
		String s;

		try {
			sc = new Scanner(new BufferedReader (new FileReader (fileName)));
			game.deserialize();
			game.cycleDeserialize(sc.nextLine().toString());
			game.coinsDeserialize(sc.nextLine().toString());
			game.levelDeserialize(sc.nextLine().toString());
			game.remainingVampiresDeserialize(sc.nextLine().toString(), sc.nextLine().toString());
			s = sc.nextLine();
			s= sc.nextLine();
			while (sc.hasNextLine()) {
				s = sc.nextLine();			
				game.objectDeserialize(s);
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Sorry for no conseguirlo");
		}
		
		finally {
			try {
				sc.close();
			} 
			catch (InputMismatchException ime) {
				System.out.println(ime.getMessage());
			}
		}
		
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException  {
		if(commandWords.length == 2 && matchCommandName(commandWords[0])) {
			this.fileName = commandWords[1] + ".dat";
			return this;			
		}
		else if (matchCommandName(commandWords[0]))
			throw new CommandParseException ("[ERROR]: Command " + name + " :" + incorrectNumberOfArgsMsg);
		else 
			return null;
	}
}
