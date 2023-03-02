package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandExecuteException;
import excepciones.CommandParseException;


public class UpdateCommand extends Command {
	protected static final String NAME = "none";
	protected static final String SHORTCUT = "n";
	private static final String DETAILS = "[n]one | []";
	private static final String HELP = "update";

	public UpdateCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}
	
	@Override
	public boolean execute (Game game) {
		game.update();
		game.attack();
		try {
			game.addVampireIfNeeded();		
		} catch (CommandExecuteException ex) {}
		game.removeDeadObjects();
		
		return true;
	}
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if (commandWords[0].equalsIgnoreCase(""))
			commandWords[0]=shortcut;
		return parseNoParamsCommand(commandWords);
	}
}
