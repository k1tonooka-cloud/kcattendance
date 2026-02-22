package jp.co.kycm.kcattendance.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.form.MenuForm;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CancelAutoController {
	@GetMapping("/cancel_auto")
	public String deleteCookie( HttpServletRequest request,
								 HttpServletResponse response,
								 HttpSession session ) throws Exception {
	    Cookie cookies[] = request.getCookies();
		MenuForm menu = (MenuForm)session.getAttribute("menu");
		/**** パンくずリスト ****/
		//model.addAttribute("breadcrumbs", breadcrumbs());

		//model.addAttribute("MenuForm",menu);
	    for (Cookie cookie : cookies) {
	        if ("mailaddr".equals(cookie.getName())||"passwd".equals(cookie.getName())) {
	            cookie.setMaxAge(0);
	            cookie.setPath("/");
	            response.addCookie(cookie);
	        }
	    }
	    menu.setSavePasswd(false);
	    session.setAttribute("menu", menu);
	    if( "1".equals(menu.getAffiliation())) {
	    	// メニュー（従業員）
	    	return "redirect:/menu_emp";
	    } else {
	    	// メニュー（総務部,業務部）
			return "redirect:/menu_gen";
	    }
	}
}
