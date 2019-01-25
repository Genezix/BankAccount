package exception;

public class ClientAccountAlreadyExists extends Exception {

	private static final long serialVersionUID = -1785181748191729089L;

	public ClientAccountAlreadyExists(String message) {
		super(message);
	}
}
