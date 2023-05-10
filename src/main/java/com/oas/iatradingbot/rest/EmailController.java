package src.main.java.com.oas.iatradingbot.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import src.main.java.com.oas.iatradingbot.services.EmailService;

@RestController
@CrossOrigin(origins = "http://localhost:8100")
@RequestMapping("/account")
public class EmailController {
	
	@Autowired
	EmailService emailService;

	@PostMapping(path = "/message")
	String sendEmailMessage() {
	
		this.emailService.sendMessage(
				"apdu77@yahoo.fr","test","does it work ?");
		return "Message sent";
	}
	
}
