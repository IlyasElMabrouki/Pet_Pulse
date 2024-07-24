package ma.petpulse.petpulsecore.validators;

import ma.petpulse.petpulsecore.service.dtos.ReportDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import java.util.Set;

@Component
public class ReportValidator implements Validator {
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final int MAX_CITY_LENGTH = 100;
    private static final int MAX_ADDRESS_LENGTH = 300;
    private static final int MAX_ADDITIONAL_NOTES_LENGTH = 100;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ReportDto reportDto = (ReportDto) target;

        if (reportDto.getTitle() == null || reportDto.getTitle().isEmpty()) {
            errors.rejectValue("title", "required", "Title is required");
        }
        if (reportDto.getTitle() != null && reportDto.getTitle().length() > MAX_TITLE_LENGTH) {
            errors.rejectValue("title", "size", "Title must be at most " + MAX_TITLE_LENGTH + " characters");
        }

        if (reportDto.getDescription() == null || reportDto.getDescription().isEmpty()) {
            errors.rejectValue("description", "required", "Description is required");
        }
        if (reportDto.getDescription() != null && reportDto.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            errors.rejectValue("description", "size", "Description must be at most " + MAX_DESCRIPTION_LENGTH + " characters");
        }

        if (reportDto.getCity() == null || reportDto.getCity().isEmpty()) {
            errors.rejectValue("city", "required", "City is required");
        }
        if (reportDto.getCity() != null && reportDto.getCity().length() > MAX_CITY_LENGTH) {
            errors.rejectValue("city", "size", "City must be at most " + MAX_CITY_LENGTH + " characters");
        }

        if (reportDto.getAddress() == null || reportDto.getAddress().isEmpty()) {
            errors.rejectValue("address", "required", "address is required");
        }
        if (reportDto.getAddress() != null && reportDto.getAddress().length() > MAX_ADDRESS_LENGTH) {
            errors.rejectValue("address", "size", "Address must be at most " + MAX_ADDRESS_LENGTH + " characters");
        }


        if (reportDto.getAdditionalNotes() != null && reportDto.getAdditionalNotes().length() > MAX_ADDITIONAL_NOTES_LENGTH) {
            errors.rejectValue("additional notes", "size", "Additional notes must be at most " + MAX_ADDITIONAL_NOTES_LENGTH + " characters");
        }

        if (reportDto.getType() == null) {
            errors.rejectValue("Type", "required", "Type is required");
        }


    }
}
