package org.ucm.tp1.control.commands;

import org.ucm.tp1.logic.Game;

import excepciones.CommandParseException;

public class SerializeCommand extends Command {
	protected static final String NAME = "serialize";
	protected static final String SHORTCUT = "z";
	private static final String DETAILS = "Seriali[z]e";
	private static final String HELP = "Serializes the board.";
	
	public SerializeCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(game.serialize());
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return parseNoParamsCommand(commandWords);
	}

}
