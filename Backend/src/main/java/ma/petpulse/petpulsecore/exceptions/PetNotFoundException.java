package ma.petpulse.petpulsecore.exceptions;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String s) {
        super(s);
    }
}
