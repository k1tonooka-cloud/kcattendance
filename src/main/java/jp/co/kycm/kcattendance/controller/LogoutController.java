package jp.co.kycm.kcattendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.form.LogoutForm;


@Controller
public class LogoutController {
	@PostMapping("/logout")
	public String logout(@ModelAttribute LogoutForm form,
						   HttpSession session) {
		session.removeAttribute("download");
		session.removeAttribute("menu");
		return "redirect:login";
	}

}
