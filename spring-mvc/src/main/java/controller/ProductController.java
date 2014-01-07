package controller;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import entities.Product;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/product")
@Transactional
public class ProductController {

	@PersistenceContext
	private EntityManager em;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@ModelAttribute("product")
	public Product productModel(@RequestParam(required = false) Long id) {
        if(id == null) {
            return new Product();
        }

		Product p = em.find(Product.class, id);

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
			em.persist(product);
			redirectAttrs.addFlashAttribute("flash", "saved");
		} else {
			em.merge(product);
			redirectAttrs.addFlashAttribute("flash", "updated");
		}

		return "redirect:/product/edit.do?id=" + product.getId();
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public String post(@RequestParam long id, RedirectAttributes redirectAttrs) {
		Product p = em.find(Product.class, id);

		if (p != null) {
			em.remove(p);
			redirectAttrs.addFlashAttribute("flash", "deleted");
		}

		return "redirect:/home.do";
	}

}
