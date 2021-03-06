package exceptions;

public class TranslationAlreadyExists extends RuntimeException {
	
	public TranslationAlreadyExists() {
		super("Die ‹bersetzung existiert bereits!");
	}
	
	public TranslationAlreadyExists(String errorMessage) {
		super(errorMessage);
	}
	
}
