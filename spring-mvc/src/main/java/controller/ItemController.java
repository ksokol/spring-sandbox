package controller;

import com.google.common.base.Objects;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import security.CustomWebAuthenticationDetails;

import java.util.Map;

/**
 * @author Kamill Sokol
 */
@Controller
public class ItemController {

	@RequestMapping(value="/item/{itemId}", produces = "text/html")
	public String index(Map<String, Object> model, Authentication authentication) {
		CustomWebAuthenticationDetails details = (CustomWebAuthenticationDetails) authentication.getDetails();
		Objects.toStringHelper(details).add("itemId", details.getItemId()).toString();

		model.put("details", details);

		return "item";
	}
}
