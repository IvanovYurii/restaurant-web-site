package ivanov.springbootintro.validation.impl;

import ivanov.springbootintro.validation.Author;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AuthorValidator implements ConstraintValidator<Author, String> {
    private static final String PATTERN_OF_AUTHOR = "^[A-Z][a-z]+( [A-Z][a-z]+)*$";

    @Override
    public boolean isValid(String author,
                           ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("Book validator");
        return author != null && Pattern.compile(PATTERN_OF_AUTHOR).matcher(author).matches();
    }
}
