package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandParseException;

public class HelpCommand extends Command {
	protected static final String NAME = "help";
	protected static final String SHORTCUT = "h";
	private static final String DETAILS = "[h]elp";
	private static final String HELP = "show this help";
	
	public HelpCommand() {
		super(NAME,SHORTCUT,DETAILS,HELP);
	}
	
	@Override
	public boolean execute (Game game) {
		System.out.println(CommandGenerator.commandHelp());
		
		return false;
	}
	  
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return parseNoParamsCommand(commandWords);
	}
	  
}
