package ApiModule;

public class UserAlreadyExistsException extends RuntimeException{
	public UserAlreadyExistsException() {
        super("A User with this IMSI already exists!");
    }
}
