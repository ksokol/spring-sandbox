package controller;

import org.springframework.beans.InvalidPropertyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kamill Sokol dev@sokol-web.de
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(InvalidPropertyException.class)
    public ModelAndView exception(InvalidPropertyException e) {
        ModelAndView modelAndView = new ModelAndView("error/property_access");
        modelAndView.addObject("property", e.getPropertyName());
        return modelAndView;
    }
}
