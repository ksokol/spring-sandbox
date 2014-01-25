package controller;

import dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class HomeController {

    private ProductDao productDao;

    @Autowired
    public HomeController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping(value = "home")
    public String home(Map<String, Object> model) {
        model.put("list", productDao.findAll());
        return "home";
    }

}
