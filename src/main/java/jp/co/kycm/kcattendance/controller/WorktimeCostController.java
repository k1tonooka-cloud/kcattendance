package jp.co.kycm.kcattendance.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.entity.ConfNotenter;
import jp.co.kycm.kcattendance.form.MenuForm;
import jp.co.kycm.kcattendance.form.WorktimeCostForm;
import jp.co.kycm.kcattendance.form.WorktimeCostItem;
import jp.co.kycm.kcattendance.service.MEmployeeService;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class WorktimeCostController {

	final MEmployeeService mEmployeeService;

	@GetMapping("/worktime_cost")
	public String worktimeCost(@ModelAttribute WorktimeCostForm form,
										 Model model,
										 HttpSession session ) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");

	    form.setYear(menu.getYear());
	    form.setMonth(menu.getMonth());

		common( form,model, session);
		model.addAttribute("MenuForm",menu);

	    return "worktime_cost";
	}
	@PostMapping("/worktime_cost")
	public String worktimeCost2(@ModelAttribute WorktimeCostForm form,
										 Model model,
										 HttpSession session ) {
		MenuForm menu = (MenuForm)session.getAttribute("menu");

		common( form,model, session);
		model.addAttribute("MenuForm",menu);

	    return "worktime_cost";
	}
	/**** 共通処理 *****/
	private void common(WorktimeCostForm form,Model model,HttpSession session) {

		/**** パンくずリスト ****/
	    model.addAttribute("breadcrumbs", breadcrumbs());
	    /**** 社員マスタ、作業時間テーブル取得 ****/
	    List<ConfNotenter>list = mEmployeeService.findList03( form.getYear(), 
	    													form.getMonth(), 
	    													0, 
	    													0);

	    /**** 作業時間・経費一覧テーブルに格納する  ****/
	    List<WorktimeCostItem> list2 = list.stream()
	    	    .map(original -> {
	    	    	WorktimeCostItem copy = new WorktimeCostItem();
	    	    	copy.setEmployeeId(original.getEmployeeId());
	    	    	copy.setEmployeeName(original.getEmployeeName());
	    	    	copy.setWorktime(original.getWorktime());
	    	    	copy.setCost(original.getCostSum());
	    	        return copy;
	    	    })
	    	    .collect(Collectors.toList());
	    form.setWorktimeCostItems(list2);
	    model.addAttribute("worktimeCostForm", form);
		
	}
	/**** パンくずリスト ****/
	private List<String[]> breadcrumbs() {
		List<String[]> breadcrumbs = new ArrayList<>();

		breadcrumbs.add(new String[]{"/menu_gen", "Menu"});
		breadcrumbs.add(new String[]{"/worktime_cost", "作業時間・経費一覧"});
		return breadcrumbs;
	}
}
