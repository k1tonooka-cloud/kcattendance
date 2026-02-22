package jp.co.kycm.kcattendance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.kycm.kcattendance.form.LoginForm;



@Controller
public class ResetPwdController {
	@GetMapping("/reset_pwd")
	public String login(@ModelAttribute LoginForm form, Model model ) {
		return "reset_pwd";
	}
}
