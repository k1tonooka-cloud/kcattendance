package jp.co.kycm.kcattendance.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.form.MenuForm;
import jp.co.kycm.kcattendance.form.SetPwdForm;
import jp.co.kycm.kcattendance.service.MEmployeeService;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class SetPwdController {
	final MEmployeeService mEmployeeService;
	private String sel;

	@GetMapping("/set_pwd")
	public String setPwd(@ModelAttribute SetPwdForm form, 
							Model model, 
							HttpSession session  ) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");
		sel = menu.getAffiliation();
		/**** パンくずリスト ****/
	    model.addAttribute("breadcrumbs", breadcrumbs());

	    form.setMailaddr(menu.getMailAddr());
	    form.setPasswd(menu.getPasswd());
	    form.setOldpasswd("");
	    form.setNewpasswd1("");
	    form.setNewpasswd2("");
	    model.addAttribute("SetPwdForm", form);
		model.addAttribute("MenuForm",menu);
		return "set_pwd";
	}
	@PostMapping("/set_pwd")
	public String csetPwd( Model model,
							@ModelAttribute("SetPwdForm") @Validated  SetPwdForm form,
							BindingResult bindingResult,
							HttpSession session ) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");
	    form.setMailaddr(menu.getMailAddr());
	    form.setPasswd(menu.getPasswd());
		form.setMsg(null);
		model.addAttribute("MenuForm",menu);
		sel = menu.getAffiliation();
		/**** パンくずリスト ****/
		model.addAttribute("breadcrumbs", breadcrumbs());
		model.addAttribute("SetPwdForm", form);
		if (bindingResult.hasErrors()){
			return "set_pwd";
        }
		// パスワード変更
		mEmployeeService.update01(menu.getEmployeeId(), form.getNewpasswd1());
		menu.setPasswd(form.getNewpasswd1());
		model.addAttribute("MenuForm",menu);
		session.setAttribute("menu", menu);

		// メッセージダイアログを表示する
		String msg;
		msg = "パスワードが変更されました";
		form.setMsg(msg);
		model.addAttribute("LoginForm", form);

		return "set_pwd";
	}

	/**** パンくずリスト ****/
	private List<String[]> breadcrumbs() {
		List<String[]> breadcrumbs = new ArrayList<>();

		if( "1".equals(sel)) {
			/* 開発部 */
			breadcrumbs.add(new String[]{"/menu_emp", "Menu"});
		} else {
			/* 総務部, 業務部 */
			breadcrumbs.add(new String[]{"/menu_gen", "Menu"});
		}
		breadcrumbs.add(new String[]{"/set_pwd", "パスワード設定画面"});
		return breadcrumbs;
	}
}
