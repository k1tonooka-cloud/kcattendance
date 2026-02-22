package jp.co.kycm.kcattendance.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.common.CommonMap;
import jp.co.kycm.kcattendance.entity.MEmployee;
import jp.co.kycm.kcattendance.form.EmpMasterForm;
import jp.co.kycm.kcattendance.form.EmpMasterItem;
import jp.co.kycm.kcattendance.form.MenuForm;
import jp.co.kycm.kcattendance.service.MEmployeeService;
import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class EmpMasterController {

	final MEmployeeService mEmployeeService;
	@Autowired
	private Environment env;

	@GetMapping("/emp_master")
	public String login(@ModelAttribute EmpMasterForm form,
										 Model model,
										 HttpSession session) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");
		/**** パンくずリスト ****/
	    model.addAttribute("breadcrumbs", breadcrumbs());
	    /**** 社員マスタ取得 ****/
	    List<MEmployee>list = mEmployeeService.findList02();

	    /**** 画面の社員マスタに格納する  ****/
	    List<EmpMasterItem> list2 = list.stream()
	    	    .map(original -> {
	    	    	EmpMasterItem copy = new EmpMasterItem();
	    	    	copy.setEmployeeId(original.getEmployeeId());
	    	    	copy.setEmployeeName(original.getEmployeeName());
	    	    	copy.setMailAddr(original.getMailAddr());
	    	    	copy.setStatusName(CommonMap.statusMap.get(original.getStatus()));
	    	    	copy.setStatus(original.getStatus());
	    	    	copy.setAffiliationName(CommonMap.affiliationMap.get(original.getAffiliation()));
	    	    	copy.setAffiliation(original.getAffiliation());
	    	    	copy.setReadOnly(true);
	    	    	copy.setNewrec(false);
	    	        return copy;
	    	    })
	    	    .collect(Collectors.toList());
	    form.setEmpMasterItems(list2);
	    model.addAttribute("EmpMasterForm", form);
		model.addAttribute("MenuForm",menu);
	    return "emp_master";
	}
	@PostMapping("/emp_master")
	String cLogin(
			Model model,
			@ModelAttribute @Validated EmpMasterForm form,
			BindingResult bindingResult,
			HttpSession session ) {

		MenuForm menu = (MenuForm)session.getAttribute("menu");
		model.addAttribute("MenuForm",menu);
		/**** パンくずリスト ****/
	    model.addAttribute("breadcrumbs", breadcrumbs());

	    /**** 新規ボタン押下時 ****/
	    if( "new".equals(form.getAction())) {
	    	List<EmpMasterItem> items = form.getEmpMasterItems();
	    	EmpMasterItem item = new EmpMasterItem();
	    	item.setCheckbox(true);
	    	item.setEmployeeId("");
	    	item.setEmployeeName("");
	    	item.setMailAddr("@kycm.co.jp");
	    	item.setStatus("1");
	    	item.setAffiliation("1");
	    	item.setNewrec(true);
	    	item.setReadOnly(false);
	    	items.add(item);
	    	form.setEmpMasterItems(items);
		    model.addAttribute("EmpMasterForm", form);
	    	return "emp_master";
	    }
	    /**** チェックボックス押下時 ****/
	    if( Objects.nonNull(form.getNo()) ) {
	    	List<EmpMasterItem> items = form.getEmpMasterItems();
	    	int no = form.getNo();
	    	items.get(no).setReadOnly(false);
	    	form.setNo(null);
	    	form.setEmpMasterItems(items);
		    model.addAttribute("EmpMasterForm", form);
	    	return "emp_master";
	    }
	    /**** 破棄ボタン押下時 ****/
	    if( "dest".equals(form.getAction())) {
		    /**** 社員マスタ取得 ****/
		    List<MEmployee>list = mEmployeeService.findList02();

		    /**** 画面の社員マスタに格納する  ****/
		    List<EmpMasterItem> list2 = list.stream()
		    	    .map(original -> {
		    	    	EmpMasterItem copy = new EmpMasterItem();
		    	    	copy.setEmployeeId(original.getEmployeeId());
		    	    	copy.setEmployeeName(original.getEmployeeName());
		    	    	copy.setMailAddr(original.getMailAddr());
		    	    	copy.setStatusName(CommonMap.statusMap.get(original.getStatus()));
		    	    	copy.setStatus(original.getStatus());
		    	    	copy.setAffiliationName(CommonMap.affiliationMap.get(original.getAffiliation()));
		    	    	copy.setAffiliation(original.getAffiliation());
		    	    	copy.setReadOnly(true);
		    	    	copy.setNewrec(false);
		    	        return copy;
		    	    })
		    	    .collect(Collectors.toList());
		    form.setEmpMasterItems(list2);
		    model.addAttribute("EmpMasterForm", form);
			model.addAttribute("MenuForm",menu);
		    return "emp_master";
	    }
	    
	    /**** エラーチェック ****/
		if (bindingResult.hasErrors()){
			return "emp_master";
        }
		if( "save".equals(form.getAction())) {
	    	List<EmpMasterItem> list = form.getEmpMasterItems();
	    	list.forEach(item -> {
	    		if( item.isCheckbox()) {
	    			long millis = System.currentTimeMillis();
	    			Timestamp timestamp = new Timestamp(millis);

	    			MEmployee emp = new MEmployee();
	    			emp.setEmployeeId(item.getEmployeeId());
	    			emp.setEmployeeName(item.getEmployeeName());
	    			emp.setMailAddr(item.getMailAddr());
	    			emp.setStatus(item.getStatus());
	    			emp.setAffiliation(item.getAffiliation());
	    			emp.setDeleteFlg("0");
	    			emp.setCreateDate(timestamp);
	    			emp.setCreateUser(menu.getEmployeeId());
	    			emp.setCreatePrg("EmpMaster");
	    			emp.setUpdateDate(timestamp);
	    			emp.setUpdateUser(menu.getEmployeeId());
	    			emp.setUpdatePrg("EmpMaster");
	    			if( item.isNewrec()) {
	    				// **** INSERT ****
	    				emp.setPassword(env.getProperty("kcattendance.passwd"));
	    				mEmployeeService.insert01(emp);
	    			} else {
	    				// **** UPDATE ****
	    				mEmployeeService.update02(emp);
	    			}
	    		}
	    	});
			// メッセージダイアログを表示する
		    model.addAttribute("EmpMasterForm", form);
			form.setMsg("データは更新しました");
			return "/emp_master";
		}
	    model.addAttribute("EmpMasterForm", form);
		return "emp_master";
	}
	/**** パンくずリスト ****/
	private List<String[]> breadcrumbs() {
		List<String[]> breadcrumbs = new ArrayList<>();

		breadcrumbs.add(new String[]{"/menu_gen", "Menu"});
		breadcrumbs.add(new String[]{"/emp_master", "社員マスタメンテナンス"});
		return breadcrumbs;
	}
}
