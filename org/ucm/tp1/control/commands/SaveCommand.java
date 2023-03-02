package org.ucm.tp1.control.commands;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;

import org.ucm.tp1.logic.Game;

import excepciones.CommandParseException;

public class SaveCommand extends Command {
	protected static final String NAME = "save";
	protected static final String SHORTCUT = "s";
	private static final String DETAILS = "[S]ave <filename>";
	private static final String HELP = "Save the state of the game to a file.";
	private String fileName;
	
	public SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) {
		PrintWriter out = null;
		String s = game.serialize();
		
		try {
			out = new PrintWriter(fileName);
			out.print(s);
			System.out.println("Game successfully saved to file " + fileName + ".");
		} 
		catch (FileNotFoundException e) {
			System.out.println("No he conseguido crear el texto");
		} 
		finally {
			if(out != null) {
				try {
					out.close();
				} 
				catch (InputMismatchException e) {
					System.out.println("El fichero no se pudo cerrar");
				}
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
