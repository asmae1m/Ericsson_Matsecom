package ApiModule;

class UserIndexOutOfBoundsException extends RuntimeException {
	public UserIndexOutOfBoundsException() {
		super("User index is out of bounds!");
	}
}
