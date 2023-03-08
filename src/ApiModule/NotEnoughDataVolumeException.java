package ApiModule;

public class NotEnoughDataVolumeException extends RuntimeException {
	public NotEnoughDataVolumeException() {
		super("Not enough Data Volume!");
	}
}
