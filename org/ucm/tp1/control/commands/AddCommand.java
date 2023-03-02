package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandExecuteException;
import excepciones.CommandParseException;
import excepciones.NotEnoughCoinsException;
import excepciones.UnvalidPositionException;

public class AddCommand extends Command {
	private int x;
	private int y;
	protected static final String NAME = "add";
	protected static final String SHORTCUT = "a";
	private static final String DETAILS = "[a]dd <x> <y>";
	private static final String HELP = "add a slayer in position x, y";

	public AddCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
		
	}
	
	@Override
	public boolean execute (Game game) throws CommandExecuteException {
		try {
		if(game.addSlayer(x, y)) {
			game.update();
			game.attack();
			try {
				game.addVampireIfNeeded();		
			} catch (CommandExecuteException ex) {}	
			game.removeDeadObjects();
			
			return true;
		}
		else
			return false;
		}catch(UnvalidPositionException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException("[ERROR]: Failed to add slayer");
		}catch(NotEnoughCoinsException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException("[ERROR]: Failed to add slayer");
		}

	}
	  
	@Override
	public Command parse(String[] commandWords) throws CommandParseException  {
		if(commandWords.length == 3 && matchCommandName(commandWords[0])) {
			try {
				this.x = Integer.parseInt(commandWords[1]);
				this.y = Integer.parseInt(commandWords[2]);
				return this;			
			}
			catch (NumberFormatException nfe) {
				throw new CommandParseException("[ERROR]: Unvalid argument for add slayer command, number expected: " + DETAILS);
			}
			
		}
		else if (matchCommandName(commandWords[0]))
			throw new CommandParseException ("[ERROR]: " + incorrectNumberOfArgsMsg + " for add command: " + DETAILS);
		else 
			return null;
	}
}
