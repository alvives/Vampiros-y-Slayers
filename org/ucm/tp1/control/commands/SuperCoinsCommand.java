package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandParseException;

public class SuperCoinsCommand extends Command {
	protected static final String NAME = "coins";
	protected static final String SHORTCUT = "c";
	private static final String DETAILS = "[c]oins";
	private static final String HELP = "add 1000 coins";
	private final static int AUMENTO = 1000;
	
	public SuperCoinsCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}
	
	@Override
	public boolean execute (Game game) {
		game.superCoins(AUMENTO);
		return true;
	}
	  
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return parseNoParamsCommand(commandWords);
	}
}
