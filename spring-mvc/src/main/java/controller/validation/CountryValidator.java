package controller.validation;

import entity.Country;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Kamill Sokol dev@sokol-web.de
 */
public class CountryValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Country.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Country c = (Country) o;

        if(c.getLocales() != null && c.getLocales().size() > 2) {
            errors.reject("locales","size");
        }
    }
}
