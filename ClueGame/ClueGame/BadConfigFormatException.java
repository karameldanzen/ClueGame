package ClueGame;
import java.util.*;
import java.io.*;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super();
	}
	public BadConfigFormatException(String error) {
		super(error);
	}
}