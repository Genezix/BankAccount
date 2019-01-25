package exception;

public class ClientAccountDoesNotExists extends Exception {

	private static final long serialVersionUID = -2029264208364281613L;

	public ClientAccountDoesNotExists(String message) {
		super(message);
	}
}
