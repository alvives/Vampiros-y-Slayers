package org.ucm.tp1.control.commands;

import excepciones.CommandParseException;

public class CommandGenerator {
	private static Command[] availableCommands = {
			new AddCommand(),
			new HelpCommand(),
			new ResetCommand(),
			new ExitCommand(),
			new UpdateCommand(),
			new GarlicPushCommand(),
			new LightFlashCommand(),
			new AddBankBloodCommand(),
			new SuperCoinsCommand(), 
			new AddVampireCommand(),
			new SaveCommand(),
			new SerializeCommand(),
			new LoadCommand()
			};
	
	//Invoca a cada método parse() de las subclases
	public static Command parse (String[] commandWords) throws CommandParseException {
		int cont = 0;
		boolean encontrado = false;
		
		while(cont < availableCommands.length && !encontrado) {
			if(availableCommands[cont].parse(commandWords) != null)
				encontrado = true;
			else
				cont++;
		}	
		
		if(encontrado) 	
			return availableCommands[cont];
		else	
			throw new CommandParseException ("[ERROR]: Unknown command" + "%n%n");
	}
	
	//Invoca a cada método helpText() de las subclases
	//Lo invoca el execute() de la clase HelpCommand
	public static String commandHelp() {
		StringBuilder helpMessage = new StringBuilder();
		
		helpMessage.append("Available commands:" + "\n");
		
		for(int i = 0; i < availableCommands.length; i++) {
			helpMessage.append(availableCommands[i].helpText());
		}
		
		return helpMessage.toString();
	}
}
