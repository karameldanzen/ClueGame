package clueGame;
/**
 * BadConfigFormatException - thrown whenever a user messes up the configuration files
 * @author Kyle Strayer
 * @author Garrett Van Buskirk
 */
public class BadConfigFormatException extends Exception {

	private static final long serialVersionUID = 1L;
	public BadConfigFormatException() {
		super();
	}
	public BadConfigFormatException(String error) {
		super(error);
	}
}