package jp.co.kycm.kcattendance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.common.CommonUtil;
import jp.co.kycm.kcattendance.entity.ConfNotenter;
import jp.co.kycm.kcattendance.form.ConfNotenterForm;
import jp.co.kycm.kcattendance.form.ConfNotenterItem;
import jp.co.kycm.kcattendance.form.MenuForm;
import jp.co.kycm.kcattendance.service.MEmployeeService;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class ConfNotenterController {

	final MEmployeeService mEmployeeService;

	@GetMapping("/conf_notenter")
	public String confNotenter(@ModelAttribute ConfNotenterForm form,
										 Model model,
										 HttpSession session ) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");
		session.removeAttribute("download");

		/**** パンくずリスト ****/
	    model.addAttribute("breadcrumbs", breadcrumbs());
	    /**** 社員マスタ、作業時間テーブル取得 ****/
	    List<ConfNotenter>list = mEmployeeService.findList03( menu.getYear(), 
	    													menu.getMonth(), 
	    													form.getExtraction(), 
	    													form.getOrderby());

	    /**** 未入力確認テーブルに格納する  ****/
	    List<ConfNotenterItem> list2 = list.stream()
	    	    .map(original -> {
	    	    	ConfNotenterItem copy = new ConfNotenterItem();
	    	    	copy.setEmployeeId(original.getEmployeeId());
	    	    	copy.setEmployeeName(original.getEmployeeName());
	    	    	copy.setWorktime(original.getWorktime());
	    	    	copy.setWorkscheduleFlg(original.getWorkscheduleFlg());
	    	    	copy.setWorkschedule(original.getWorkschedule());
	    	    	copy.setSend(CommonUtil.send_status(original.getStatus()));
	    	    	copy.setCost(original.getCostSum());
	    	        return copy;
	    	    })
	    	    .collect(Collectors.toList());
	    form.setConfNotenterItems(list2);
	    model.addAttribute("ConfNotenterForm", form);
		model.addAttribute("MenuForm",menu);
	    return "conf_notenter";
	}
	@PostMapping("/conf_notenter")
	String cconfNotenter(
			Model model,
			@ModelAttribute @Validated ConfNotenterForm form,
			BindingResult bindingResult,
			HttpSession session ) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");
		/**** パンくずリスト ****/
	    model.addAttribute("breadcrumbs", breadcrumbs());
	    /**** 社員マスタ、作業時間テーブル取得 ****/
	    List<ConfNotenter>list = mEmployeeService.findList03( menu.getYear(), 
	    													menu.getMonth(), 
	    													form.getExtraction(), 
	    													form.getOrderby());

	    /**** 未入力確認テーブルに格納する  ****/
	    List<ConfNotenterItem> list2 = list.stream()
	    	    .map(original -> {
	    	    	ConfNotenterItem copy = new ConfNotenterItem();
	    	    	copy.setEmployeeId(original.getEmployeeId());
	    	    	copy.setEmployeeName(original.getEmployeeName());
	    	    	copy.setWorktime(original.getWorktime());
	    	    	copy.setWorkscheduleFlg(original.getWorkscheduleFlg());
	    	    	copy.setSend(CommonUtil.send_status(original.getStatus()));
	    	    	copy.setCost(original.getCostSum());
	    	        return copy;
	    	    })
	    	    .collect(Collectors.toList());
	    form.setConfNotenterItems(list2);
	    model.addAttribute("ConfNotenterForm", form);
		model.addAttribute("MenuForm",menu);

		return "conf_notenter";
	}
	/**** パンくずリスト ****/
	private List<String[]> breadcrumbs() {
		List<String[]> breadcrumbs = new ArrayList<>();

		breadcrumbs.add(new String[]{"/menu_gen", "Menu"});
		breadcrumbs.add(new String[]{"/conf_notenter", "未入力確認"});
		return breadcrumbs;
	}
}
