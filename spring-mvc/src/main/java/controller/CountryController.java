package controller;

import controller.validation.CountryValidator;
import entity.Country;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Kamill Sokol dev@sokol-web.de
 */
@Controller
@RequestMapping("country")
public class CountryController {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(3);
        binder.setValidator(new CountryValidator());
    }

    @ModelAttribute("country")
    public Country model() {
        return new Country();
    }

    @RequestMapping("edit")
    public String edit(@ModelAttribute("country") Country country, BindingResult result) {
        if(result.hasErrors()) {
            // return to edit page with error message
        } else {
            // go ahead
        }
        return "country/edit";
    }
}
