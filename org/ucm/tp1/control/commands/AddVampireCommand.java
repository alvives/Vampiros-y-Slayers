package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandExecuteException;
import excepciones.CommandParseException;
import excepciones.DraculaIsAliveException;
import excepciones.NoMoreVampiresException;
import excepciones.UnvalidPositionException;

public class AddVampireCommand extends Command{
	private int x;
	private int y;
	private String letra;
	protected static final String NAME = "vampire";
	protected static final String SHORTCUT = "v";
	private static final String DETAILS = "[v]ampire [<type>] <x> <y>. Type = {\"\"|\"D\"|\"E\"}";
	private static final String HELP = "add a vampire in position x, y";

	public AddVampireCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);		
	}
	
	@Override
	public boolean execute (Game game) throws CommandExecuteException {
		try {
		return game.addVampire(x, y, letra);
		}catch(UnvalidPositionException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException("[ERROR]: Failed to add this vampire");
		}catch(NoMoreVampiresException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException("[ERROR]: Failed to add this vampire");
		}catch(DraculaIsAliveException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException("[ERROR]: Failed to add this vampire");
		}
		
	}
	  
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(commandWords.length == 4 && matchCommandName(commandWords[0])) {
			try {
				this.letra = commandWords[1];
				this.x = Integer.parseInt(commandWords[2]);
				this.y = Integer.parseInt(commandWords[3]);
				if(!"d".equals(letra) && !"e".equals(letra) && !"v".equals(letra))
					throw new CommandParseException("[ERROR]: Unvalid type: " + DETAILS);
				return this;
			}
			catch (NumberFormatException nfe) {
				throw new CommandParseException("[ERROR]: Unvalid argument for add vampire command, number expected: " + DETAILS);
			}
		}
		else if(commandWords.length == 3 && matchCommandName(commandWords[0])) {
			try {
				this.letra = "v";
				this.x = Integer.parseInt(commandWords[1]);
				this.y = Integer.parseInt(commandWords[2]);
				return this;
			}
			catch (NumberFormatException nfe) {
				throw new CommandParseException("[ERROR]: Invalid argument for add vampire command, number expected: " + DETAILS);
			}
		}
		else if (matchCommandName(commandWords[0]))
			throw new CommandParseException ("[ERROR]: Command " + name + " :" + incorrectNumberOfArgsMsg);
		else
			return null;
	}
}
