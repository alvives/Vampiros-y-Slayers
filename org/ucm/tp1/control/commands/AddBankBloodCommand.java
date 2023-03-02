package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandExecuteException;
import excepciones.CommandParseException;
import excepciones.NotEnoughCoinsException;
import excepciones.UnvalidPositionException;

public class AddBankBloodCommand extends Command {
	private int x;
	private int y;
	private int z;
	protected static final String NAME = "bank";
	protected static final String SHORTCUT = "b";
	private static final String DETAILS = "[b]ank <x> <y> <z>";
	private static final String HELP = "add a blood bank with cost z in position x, y";

	public AddBankBloodCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
		
	}
	
	@Override
	public boolean execute (Game game) throws CommandExecuteException  {
		try {
		if(game.addBankBlood(x, y, z)) {
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
			throw new CommandExecuteException("[ERROR]: Failed to add bank blood");
		}catch(NotEnoughCoinsException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException("[ERROR]: Failed to add bank blood");
		}

	}
	  
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(commandWords.length == 4 && matchCommandName(commandWords[0])) {
			try {
				this.x = Integer.parseInt(commandWords[1]);
				this.y = Integer.parseInt(commandWords[2]);
				this.z = Integer.parseInt(commandWords[3]);
				return this;
			}
			catch (NumberFormatException nfe) {
				throw new CommandParseException(" [ERROR]:  Invalid argument for add blood bank command, number expected: " + DETAILS);
			}
		}
		else if(matchCommandName(commandWords[0]))
			throw new CommandParseException (" [ERROR]: Command " + name + " :" + incorrectNumberOfArgsMsg);
		else 
			return null;
	}
	
}
