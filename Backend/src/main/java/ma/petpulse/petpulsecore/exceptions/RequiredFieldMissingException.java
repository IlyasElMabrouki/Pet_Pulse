package ma.petpulse.petpulsecore.exceptions;

public class RequiredFieldMissingException extends RuntimeException {
    public RequiredFieldMissingException(String requiredFieldsAreMissing) {
        super(requiredFieldsAreMissing);
    }
}
