package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandParseException;

public class ResetCommand extends Command {
	protected static final String NAME = "reset";
	protected static final String SHORTCUT = "r";
	private static final String DETAILS = "[r]eset";
	private static final String HELP = "reset game";
	
	public ResetCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}
	
	@Override
	public boolean execute (Game game) {
		game.reset();
		
		return true;
	}
	  
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return parseNoParamsCommand(commandWords);
	}
	  
	
}
