package ApiModule;

/**
 * Exception thrown when a user can not be created because there already is a user with the same IMSI
 *  
 * @author ALARA
 *
 * @since 1.0
 */
public class UserAlreadyExistsException extends RuntimeException{
	public UserAlreadyExistsException() {
        super("A User with this IMSI already exists!");
    }
}
