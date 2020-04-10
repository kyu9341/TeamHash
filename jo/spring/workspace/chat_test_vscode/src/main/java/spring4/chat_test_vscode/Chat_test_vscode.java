package spring4.chat_test_vscode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class Chat_test_vscode {

	public static void main(String[] args) {
		SpringApplication.run(Chat_test_vscode.class, args);
	}
	/*
	@Controller
	public static class MainController {
		@GetMapping("/")
		public String main() {
			return "redirect:/chat/rooms";
		}
	} */
}