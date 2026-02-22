package jp.co.kycm.kcattendance.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.form.MenuForm;



@Controller
public class MenuEmpController {
	@GetMapping("/menu_emp")
	public String menu(@ModelAttribute MenuForm form,
							Model model,
							HttpSession session ) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");
		session.removeAttribute("download");
	
		/**** パンくずリスト ****/
		model.addAttribute("breadcrumbs", breadcrumbs());

		model.addAttribute("MenuForm",menu);
		return "menu_emp";
	}
	/**** パンくずリスト ****/
	private List<String[]> breadcrumbs() {
		List<String[]> breadcrumbs = new ArrayList<>();
		breadcrumbs.add(new String[]{"/menu_emp", "Menu"});
		return breadcrumbs;
	}
}
