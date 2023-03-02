package excepciones;

public class CommandExecuteException extends GameException {
	public CommandExecuteException (String s) {
		super(s);
	}
	public CommandExecuteException () {
		super("");
	}
}
