package controller;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@PersistenceContext
	private EntityManager em;

	@RequestMapping(value = "home")
	public String home(Map<String, Object> model) {
		model.put("list", em.createQuery("from Product").getResultList());
		return "home";
	}

}
