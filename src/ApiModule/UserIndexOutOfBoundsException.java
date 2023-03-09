package ApiModule;

/**
 * Exception thrown when trying to get a user from the list of all users with an invalid index
 *  
 * @author ALARA
 *
 * @since 1.0
 */
class UserIndexOutOfBoundsException extends RuntimeException {
	public UserIndexOutOfBoundsException() {
		super("User index is out of bounds!");
	}
}
