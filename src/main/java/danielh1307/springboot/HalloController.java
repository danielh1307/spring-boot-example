package danielh1307.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HalloController {

	@RequestMapping(method = RequestMethod.GET, value = "/hallo", produces = "text/plain")
	public String halloWelt() {
		return "Hallo Welt";
	}
	
}
