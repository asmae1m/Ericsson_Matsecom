package ApiModule;

/**
 * Exception thrown when a session is rejected because the subscriber does not have enough data volume left
 *  
 * @author ALARA
 *
 * @since 1.0
 */
public class NotEnoughDataVolumeException extends RuntimeException {
	public NotEnoughDataVolumeException() {
		super("Not enough Data Volume!");
	}
}
