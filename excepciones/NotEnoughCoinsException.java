package excepciones;

public class NotEnoughCoinsException extends CommandExecuteException {
	public NotEnoughCoinsException (String s) {
		super(s);
	}
}
