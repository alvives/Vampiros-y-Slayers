package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandExecuteException;
import excepciones.CommandParseException;
import excepciones.NotEnoughCoinsException;

public class LightFlashCommand extends Command {
	protected static final String NAME = "light";
	protected static final String SHORTCUT = "l";
	private static final String DETAILS = "[l]ight";
	private static final String HELP = "kills all the vampires";
	private static final int COSTE = 50;

	public LightFlashCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException{
		try {
			if(game.killThemAll(COSTE)) {
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
	
		}
		catch(NotEnoughCoinsException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException("[ERROR]: Failed to light flash");
		}
	}	

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return parseNoParamsCommand(commandWords);

	}

}
