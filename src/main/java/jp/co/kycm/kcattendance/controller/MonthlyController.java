package jp.co.kycm.kcattendance.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.entity.ConfNotenter;
import jp.co.kycm.kcattendance.entity.TMonthly;
import jp.co.kycm.kcattendance.form.MenuForm;
import jp.co.kycm.kcattendance.form.MonthlyForm;
import jp.co.kycm.kcattendance.service.MEmployeeService;
import jp.co.kycm.kcattendance.service.TMonthlyService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MonthlyController {

	final TMonthlyService tmonthService;
	final MEmployeeService mEmployeeService;

	@GetMapping("/monthly")
	public String monthly(@ModelAttribute MonthlyForm form,
			BindingResult bindingResult,
			Model model,
			HttpSession session ) {
		
		drowScreen(model, form, session );
		return "monthly";
	}
	@PostMapping("/monthly")
	String cmonthly(
			Model model,
			@ModelAttribute @Validated MonthlyForm form,
			BindingResult bindingResult,
			HttpSession session ) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");
	    /**** 社員マスタ、作業時間テーブル取得 ****/
	    List<ConfNotenter>list = mEmployeeService.findList04( form.getYear1(), 
	    													form.getMonth1()); 
	    // **** 未入力チェック   ****
	    if( list.size() > 0 ) {
		 	FieldError fieldError = new FieldError(bindingResult.getObjectName(), "fieldName", "当月の未入力者が残っています");
		 	bindingResult.addError(fieldError);
			/**** パンくずリスト ****/
			model.addAttribute("breadcrumbs", breadcrumbs());
			/**** ダイアログメッセージ ****/
			form.setMsg("");
			model.addAttribute("MenuForm",menu);
			model.addAttribute("MonthlyForm",form);
			return "monthly";
	    }

	    drowScreen(model, form, session );

		long millis = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(millis);

	    // 当月の月次テーブルを更新する。
	    TMonthly tm1 = new TMonthly();
	    tm1.setYear(form.getYear1());
	    tm1.setMonth(form.getMonth1());
	    tm1.setStatus(1);
	    tm1.setCreateDate(timestamp);
	    tm1.setCreateUser(menu.getEmployeeId());
	    tm1.setCreatePrg("Monthly");
	    tm1.setUpdateDate(timestamp);
	    tm1.setUpdateUser(menu.getEmployeeId());
	    tm1.setUpdatePrg("Monthly");
	    tmonthService.update01(tm1);
	    
	    // 翌月の月次テーブルを挿入する。
	    TMonthly tm2 = new TMonthly();
	    tm2.setYear(form.getYear2());
	    tm2.setMonth(form.getMonth2());
	    tm2.setStatus(0);
	    tm2.setUpdateDate(timestamp);
	    tm2.setUpdateUser(menu.getEmployeeId());
	    tm2.setUpdatePrg("Monthly");
	    tmonthService.insert01(tm2);
	    

		form.setMsg("月次処理が完了しました");
		return "monthly";
	}

	/**** パンくずリスト ****/
	private List<String[]> breadcrumbs() {
		List<String[]> breadcrumbs = new ArrayList<>();

		breadcrumbs.add(new String[]{"/menu_gen", "Menu"});
		breadcrumbs.add(new String[]{"/monthly", "月次処理"});
		return breadcrumbs;
	}
	private void drowScreen(Model model, MonthlyForm form, HttpSession session ) {
		MenuForm menu = (MenuForm)session.getAttribute("menu");
		// 月次テーブル取得
		List <TMonthly> list2 = tmonthService.findList01("0");
		if( list2.size() != 1 ) {
			model.addAttribute("LoginForm", form);
			return;
		}
		menu.setYear(list2.get(0).getYear());
		menu.setMonth(list2.get(0).getMonth());
		Integer year = menu.getYear();
		Integer month = menu.getMonth();
		form.setYear1(year);
		form.setMonth1(month);
		month = month + 1;
		if( month > 12 ) {
			month = 1;
			year ++;
		}
		form.setYear2(year);
		form.setMonth2(month);
			
		/**** パンくずリスト ****/
		model.addAttribute("breadcrumbs", breadcrumbs());
		model.addAttribute("MenuForm",menu);
		model.addAttribute("MonthlyForm",form);
		return;
	}
}
