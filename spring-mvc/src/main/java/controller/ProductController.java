package controller;

import dao.ProductDao;
import entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/product")
@Transactional
public class ProductController {

    @Autowired
    private ProductDao productDao;

//    BeanCreationException: Error creating bean with name 'productController': Initialization of bean failed; nested exception is org.springframework.aop.framework.AopConfigException: Could not generate CGLIB subclass of class [class controller.ProductController]: Common causes of this problem include using a final class or a non-visible class; nested exception is java.lang.IllegalArgumentException: Superclass has no null constructors but no arguments were given
//    @Autowired
//    public ProductController(ProductDao productDao) {
//        this.productDao = productDao;
//    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("product")
    public Product productModel(@RequestParam(required = false) Long id) {
        if (id == null) {
            return new Product();
        }

        Product p = productDao.findOne(id);

        if (p == null) {
            return new Product();
        } else {
            return p;
        }
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String editGet(@ModelAttribute("product") Product product, Map<String, Object> model) {
        // do some fancy stuff here
        return "product/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String post(@Valid @ModelAttribute("product") Product product, BindingResult result, Map<String, Object> model, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return this.editGet(product, model);
        }

        if (product.getId() == 0) {
            redirectAttrs.addFlashAttribute("flash", "saved");
        } else {
            redirectAttrs.addFlashAttribute("flash", "updated");
        }

        product = productDao.save(product);

        return "redirect:/product/edit.do?id=" + product.getId();
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String post(@RequestParam long id, RedirectAttributes redirectAttrs) {
        Product p = productDao.findOne(id);

        if (p != null) {
            productDao.remove(p);
            redirectAttrs.addFlashAttribute("flash", "deleted");
        }

        return "redirect:/home.do";
    }

}
