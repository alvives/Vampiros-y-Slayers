package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandParseException;

public class ExitCommand extends Command {
	protected static final String NAME = "exit";
	protected static final String SHORTCUT = "e";
	private static final String DETAILS = "[e]xit";
	private static final String HELP = "exit game";
	
	public ExitCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}

	@Override
	public boolean execute (Game game) {
		game.exit();
		
		return false;
	}
	 
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return parseNoParamsCommand(commandWords);
	}
}
