package controller;

import entities.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author dev@sokol-web.de <Kamill Sokol>
 */
@Controller
@RequestMapping("/rest")
public class ProductRestController {

    @PersistenceContext
    private EntityManager em;

    @RequestMapping("/product/{id}")
    public String index(@PathVariable Long id, Model model) {
        model.addAttribute("product", em.find(Product.class, id));

        return "product";
    }

    @RequestMapping("/product/jsp/{id}")
    public String jsp(@PathVariable Long id, Model model) {
        model.addAttribute("product", em.find(Product.class, id));

        return "product/plain";
    }
}