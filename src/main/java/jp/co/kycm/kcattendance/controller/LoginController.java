package jp.co.kycm.kcattendance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.entity.MEmployee;
import jp.co.kycm.kcattendance.entity.TMonthly;
import jp.co.kycm.kcattendance.form.LoginForm;
import jp.co.kycm.kcattendance.form.MenuForm;
import jp.co.kycm.kcattendance.service.MEmployeeService;
import jp.co.kycm.kcattendance.service.TMonthlyService;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class LoginController {
	private final MEmployeeService ms;
	private final TMonthlyService ts;

	@Autowired
	private Environment env;

	@GetMapping("/login")
	public String login(	@ModelAttribute LoginForm form,
							@CookieValue(value="mailaddr", required=false) String cookieMailaddr,
							@CookieValue(value="passwd", required = false) String cookiePasswd,
							Model model,
							HttpSession session,
							HttpServletResponse httpServletResponse) {
		
		/**** クッキーチェック ****/
		if( !StringUtil.isNullOrEmpty(cookieMailaddr) && !StringUtil.isNullOrEmpty(cookiePasswd)) {
			// 社員マスタ取得
			List<MEmployee> list = ms.findList01(cookieMailaddr, cookiePasswd);
			if( list.size() <= 0 ) {
				/** 保存したパスワードが違う場合、ログイン不可 **/
				form.setSavePasswd(false);
				model.addAttribute("LoginForm", form);
				return "login";
			}
			// 月次テーブル取得
			List <TMonthly> list2 = ts.findList01("0");
			if( list2.size() != 1 ) {
				form.setSavePasswd(false);
				model.addAttribute("LoginForm", form);
				return "login";
			}
			String sel = list.get(0).getAffiliation();
			MenuForm menu = new MenuForm();
			menu.setYear(list2.get(0).getYear());
			menu.setMonth(list2.get(0).getMonth());
			menu.setEmployeeId(list.get(0).getEmployeeId());
			menu.setEmployeeName(list.get(0).getEmployeeName());
			menu.setMailAddr(form.getLogin());
			menu.setPasswd( form.getPasswd());
			menu.setStatus(list.get(0).getStatus());
			menu.setAffiliation(sel);
			menu.setSavePasswd(true);
				
			session.setAttribute("menu", menu);
			form.setLogin(cookieMailaddr);
			form.setPasswd(cookiePasswd);
			form.setSavePasswd(true);
			/*** メールアドレスとクッキーをパスワードを記憶する ***/
			if( form.isSavePasswd()) {
				Integer sec = Integer.valueOf(env.getProperty("kcattendance.cookietime"));
				Cookie cookie1 = new  Cookie("mailaddr", form.getLogin());
				Cookie cookie2 = new  Cookie("passwd", form.getPasswd());
				cookie1.setMaxAge(sec);		// 一か月分の秒数
				cookie2.setMaxAge(sec);		// 一か月分の秒数

				httpServletResponse.addCookie(cookie1);
				httpServletResponse.addCookie(cookie2);
			}
			if( "1".equals(sel)) {
				// メニュー（従業員）
				return "redirect:/menu_emp";
			} else {
				// メニュー（総務部）
				return "redirect:/menu_gen";
			}
		}
		return "login";
	}
	@PostMapping("/login")
	String cLogin(
			Model model,
			@ModelAttribute @Validated LoginForm form,
			BindingResult bindingResult,
			//RedirectAttributes redirectAttributes,
			HttpSession session,
			HttpServletResponse httpServletResponse) {

		if (bindingResult.hasErrors()){
			model.addAttribute("LoginForm", form);
			return "login";
        }

		 // 社員マスタ取得
		 List<MEmployee> list = ms.findList01(form.getLogin(), form.getPasswd());
		 if( list.size() <= 0 ) {
			 /** ログイン不可 **/
			 	FieldError fieldError = new FieldError(bindingResult.getObjectName(), "fieldName", "メールアドレスまたはパスワードが違います");
			 	bindingResult.addError(fieldError);
				model.addAttribute("LoginForm", form);
				return "login";
		 }

		// 月次テーブル取得
		List <TMonthly> list2 = ts.findList01("0");
		if( list2.size() != 1 ) {
			model.addAttribute("LoginForm", form);
			return "login";
		}
		String sel = list.get(0).getAffiliation();
		MenuForm menu = new MenuForm();
		menu.setYear(list2.get(0).getYear());
		menu.setMonth(list2.get(0).getMonth());
		menu.setEmployeeId(list.get(0).getEmployeeId());
		menu.setEmployeeName(list.get(0).getEmployeeName());
		menu.setMailAddr(form.getLogin());
		menu.setPasswd( form.getPasswd());
		menu.setStatus(list.get(0).getStatus());
		menu.setAffiliation(sel);
		menu.setSavePasswd(form.isSavePasswd());
		
		session.setAttribute("menu", menu);
		/*** メールアドレスとクッキーをパスワードを記憶する ***/
		if( form.isSavePasswd()) {
			Integer sec = Integer.valueOf(env.getProperty("kcattendance.cookietime"));
			Cookie cookie1 = new  Cookie("mailaddr", form.getLogin());
			Cookie cookie2 = new  Cookie("passwd", form.getPasswd());
			cookie1.setMaxAge(sec);		// 一か月分の秒数
			cookie2.setMaxAge(sec);		// 一か月分の秒数

			httpServletResponse.addCookie(cookie1);
			httpServletResponse.addCookie(cookie2);
		}
		if( "1".equals(sel)) {
			// メニュー（開発部）
			return "redirect:/menu_emp";
		} else {
			// メニュー（総務部、業務部）
			return "redirect:/menu_gen";
		}
	}
}
