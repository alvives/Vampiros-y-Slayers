package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandExecuteException;
import excepciones.CommandParseException;
import excepciones.NotEnoughCoinsException;

public class GarlicPushCommand extends Command {
	protected static final String NAME = "garlic";
	protected static final String SHORTCUT = "g";
	private static final String DETAILS = "[g]arlic";
	private static final String HELP = "pushes back vampires";
	private static final int COSTE = 10;

	public GarlicPushCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException{
		try {
		if (game.garlicPush(COSTE)) {
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
		}catch(NotEnoughCoinsException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException("[ERROR]: Failed to garlic push");
		}
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return parseNoParamsCommand(commandWords);
	}

}
