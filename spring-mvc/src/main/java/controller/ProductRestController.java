package controller;

import dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author dev@sokol-web.de <Kamill Sokol>
 */
@Controller
@RequestMapping("/rest")
public class ProductRestController {

    private ProductDao productDao;

    @Autowired
    public ProductRestController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping("/product/{id}")
    public String index(@PathVariable Long id, Model model) {
        model.addAttribute("product", productDao.findOne(id));

        return "product";
    }

    @RequestMapping("/product/jsp/{id}")
    public String jsp(@PathVariable Long id, Model model) {
        model.addAttribute("product", productDao.findOne(id));

        return "product/plain";
    }
}