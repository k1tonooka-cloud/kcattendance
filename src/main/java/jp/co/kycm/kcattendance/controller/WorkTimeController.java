package jp.co.kycm.kcattendance.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.kycm.kcattendance.entity.FileDB;
import jp.co.kycm.kcattendance.entity.TCost;
import jp.co.kycm.kcattendance.entity.TWorktime;
import jp.co.kycm.kcattendance.form.CostItem;
import jp.co.kycm.kcattendance.form.DownloadForm;
import jp.co.kycm.kcattendance.form.MenuForm;
import jp.co.kycm.kcattendance.form.WorkTimeForm;
import jp.co.kycm.kcattendance.service.FileStorageService;
import jp.co.kycm.kcattendance.service.TCostService;
import jp.co.kycm.kcattendance.service.TWorktimeService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WorkTimeController {
	private final TWorktimeService tWorktimeService;
	private final TCostService tCostService;
	@Autowired  private FileStorageService storageService;

	/***************************************************/
	/*　開発部から                                     */ 
	/***************************************************/
	@GetMapping("/worktime")
	public String worktime(@ModelAttribute WorkTimeForm form,
							Model model,
							HttpSession session ) {
		
		/**** セッションからログイン情報を取得する  ****/
		MenuForm menu = (MenuForm)session.getAttribute("menu");
		form.setYear(menu.getYear());
		form.setMonth(menu.getMonth());
		form.setEmployeeId(menu.getEmployeeId());
		model.addAttribute("MenuForm",menu);
	
		get_from_db( form, model, "1");

		DownloadForm df = new DownloadForm();
		df.setFileName(form.getFileName());
		df.setMineType(form.getMineType());
		df.setData(form.getData());

		session.setAttribute("download", df);

		/**** パンくずリスト ****/
	    //model.addAttribute("breadcrumbs", breadcrumbs(1));

	    return "worktime";
	}
	/***************************************************/
	/*　総務部、業務部から                             */ 
	/***************************************************/
	@GetMapping("/worktime2")
	public String worktime2(@RequestParam(value = "id", required = false) String param1,
							@ModelAttribute WorkTimeForm form,
							Model model,
							HttpSession session ) {
		/**** セッションからログイン情報を取得する  ****/
		MenuForm menu = (MenuForm)session.getAttribute("menu");

		form.setYear(menu.getYear());
		form.setMonth(menu.getMonth());
		form.setEmployeeId(param1);
		model.addAttribute("MenuForm",menu);

		get_from_db( form, model, "2");
	
		DownloadForm df = new DownloadForm();
		df.setFileName(form.getFileName());
		df.setMineType(form.getMineType());
		df.setData(form.getData());

		session.setAttribute("download", df);

		
		/**** パンくずリスト ****/
	    //model.addAttribute("breadcrumbs", breadcrumbs(2));
		return "worktime";
	}

	@PostMapping("/worktime")
	String cWorktime(
			@ModelAttribute @Valid WorkTimeForm form,
			BindingResult bindingResult,
			Model model,
			HttpSession session ) {

		/**** セッションからログイン情報を取得する  ****/
		MenuForm menu = (MenuForm)session.getAttribute("menu");

		/**** パンくずリスト ****/
	    model.addAttribute("breadcrumbs", breadcrumbs(menu.getAffiliation()));

	    form.setYear(menu.getYear());
	    form.setMonth(menu.getMonth());
	    form.setEmployeeId(menu.getEmployeeId());
		model.addAttribute("WorkTimeForm", form);
		model.addAttribute("MenuForm",menu);

		form.setMsg(null);
		List<CostItem>list2 = form.getCostItems();
		if( list2 == null ) {
			list2 = new ArrayList<CostItem>();
		}
		
		if( "add".equals(form.getAction())) {
			CostItem item = new CostItem();
			item.setCost(0);
			list2.add(item);
		    /**** Form にデータをセットする  ****/
		    drowScreen(form,model,list2,"1" );
			return "worktime";
		}
		if( "delete".equals(form.getAction())) {
			list2.removeIf(item -> item.isCheckbox());
		    /**** Form にデータをセットする  ****/
		    drowScreen(form,model,list2,"1" );
			return "worktime";
		}
		if( "upload".equals(form.getAction())) {
			try {
				FileDB savedFile = storageService.store(form.getFile());
				byte[] bytes = savedFile.getData();
				String image =  Base64.getEncoder().encodeToString(bytes);
				//byte[] decodeBytes = Base64.getDecoder().decode(image);
				form.setWorkscheduleFlg("1");
				form.setFileName(savedFile.getName());
				form.setMineType(savedFile.getType());
				form.setData(image);
				
				DownloadForm df = new DownloadForm();
				df.setFileName(savedFile.getName());
				df.setMineType(savedFile.getType());
				df.setData(image);
				
				session.setAttribute("download", df);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		    /**** Form にデータをセットする  ****/
		    drowScreen(form,model,list2,"1" );
			return "worktime";
		}
		/*
		if( "download".equals(form.getAction())) {
	        String contentType = form.getMineType();
	        String headerValue = "attachment; filename=\"" + form.getFileName() + "\"";
	        byte[] decodedBytes = Base64.getDecoder().decode(form.getData());
	        ByteArrayResource resource = new ByteArrayResource(decodedBytes);

	        return ResponseEntity.ok()
	        		.contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
	                .body(resource);

		}
		*/
		if (bindingResult.hasErrors()){
		    /**** Form にデータをセットする  ****/
		    drowScreen(form,model,list2,"1" );
			return "worktime";
        }
		long millis = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(millis);

	    List <TWorktime>  list = tWorktimeService.findList01(menu.getYear(),
  				 							menu.getMonth(),
  				 							menu.getEmployeeId());
	    TWorktime w = new TWorktime();
	    w.setYear(menu.getYear());
	    w.setMonth(menu.getMonth());
	    w.setEmployeeId(menu.getEmployeeId());
	    w.setWorktime(form.getWorktime());
	    w.setWorkscheduleFlg(form.getWorkscheduleFlg());
	    w.setFileName(form.getFileName());
	    w.setMineType(form.getMineType());
	    w.setWorkschedule(form.getData());
	    w.setStatus("1");
	    w.setCreateDate(timestamp);
	    w.setCreateUser(menu.getEmployeeId());
	    w.setCreatePrg("worktime");
	    w.setUpdateDate(timestamp);
	    w.setUpdateUser(menu.getEmployeeId());
	    w.setUpdatePrg("worktime");
	    
	    if( list.size() > 0 ) {
	    	// レコードが存在する場合、UPDATE
	    	tWorktimeService.update01(w);
	    } else {
	    	// レコードが存在しない場合、INSERT
	    	tWorktimeService.insert01(w);
	    }
		/*** 番号をリナンバーする  ***/
		AtomicInteger j = new AtomicInteger(1); 
		list2.forEach(item -> item.setLineNo(j.getAndIncrement()));

	    List<TCost> list3 = list2.stream()
	    	    .map(original -> {
	    	        TCost copy = new TCost();
	    	        copy.setYear(form.getYear());
	    	        copy.setMonth(form.getMonth());
	    	        copy.setEmployeeId(form.getEmployeeId());
	    	        copy.setLineNo(original.getLineNo());
	    	        copy.setCost(original.getCost());
	    	        copy.setComment(original.getComment());
	    	        copy.setStatus("1");
	    	        copy.setCreateDate(timestamp);
	    	        copy.setCreateUser(menu.getEmployeeId());
	    	        copy.setCreatePrg("worktime");
	    	        copy.setUpdateDate(timestamp);
	    	        copy.setUpdateUser(menu.getEmployeeId());
	    	        copy.setUpdatePrg("worktime");
	    	        return copy;
	    	    })
	    	    .collect(Collectors.toList());

		/*** 一旦、レコードを削除する ***/
		tCostService.delete01(menu.getYear(), menu.getMonth(), menu.getEmployeeId());
		
		/*** レコードをINSERTする ***/
		list3.forEach(item-> tCostService.insert01(item));
		form.setReadonly("1");
	 	form.setMsg("送信しました。");
		model.addAttribute("WorkTimeForm", form);
		model.addAttribute("MenuForm",menu);

		return "worktime";
	}
	private void get_from_db(	WorkTimeForm form,
									Model model,
									String affiliation) {
		
	    /**** 作業時間テーブルからデータを取得する   ****/
	    List <TWorktime>  list = tWorktimeService.findList01(form.getYear(),
	    													  form.getMonth(),
	    									   				  form.getEmployeeId());
	    /**** 経費テーブルよりデータを取得する  ****/
	    List<TCost> list2 = tCostService.findList01(form.getYear(), form.getMonth(), form.getEmployeeId());

	    /**** 経費テーブルのメモリー格納する  ****/
	    List<CostItem> list3 = list2.stream()
	    	    .map(original -> {
	    	        CostItem copy = new CostItem();
	    	        copy.setCheckbox(false);
	    	        copy.setLineNo(original.getLineNo());
	    	        copy.setCost(original.getCost());
	    	        copy.setComment(original.getComment());
	    	        copy.setStatus(original.getStatus());
	    	        return copy;
	    	    })
	    	    .collect(Collectors.toList());

	    if( list.size() == 1 ) {
		    form.setWorktime(list.get(0).getWorktime());
		    form.setWorkscheduleFlg(list.get(0).getWorkscheduleFlg());
		    form.setFileName(list.get(0).getFileName());
		    form.setMineType(list.get(0).getMineType());
		    form.setData(String.valueOf(list.get(0).getWorkschedule()));
		    form.setStatus("1");
	    	form.setReadonly("1");
	    } else {
	    	form.setWorktime(BigDecimal.ZERO);
		    form.setWorkscheduleFlg("0");
		    form.setFileName(null);
		    form.setMineType(null);
	    	form.setData(null);
		    form.setStatus("0");
		    if( !"1".equals(affiliation)) {
		    	form.setReadonly("1");
		    } else {
		    	form.setReadonly("0");
		    }
	    }
	    if( !"1".equals(affiliation) ) {
	    	form.setStatus("1");
	    	
	    } else {

	    }
		drowScreen(form,model, list3, affiliation );
	}
	void drowScreen(WorkTimeForm form,Model model, List<CostItem> list2, String affiliation ) {
		/**** パンくずリスト ****/
	    model.addAttribute("breadcrumbs", breadcrumbs(affiliation));

	    /**** Form にデータをセットする  ****/
	    form.setCostItems(list2);

	    model.addAttribute("WorkTimeForm", form);
	    // sum の計算
    	Integer sum = 0;
	    if( list2 != null ) {
	    	for( int i = 0; i < list2.size(); i++ ) {
	    		if( list2.get(i).getCost() != null ) {
		    		sum = sum + list2.get(i).getCost();
	    		}
	    	}
	    }
	    model.addAttribute("sum",sum);		
	}

	/**** パンくずリスト ****/
	private List<String[]> breadcrumbs(String a ) {
		List<String[]> breadcrumbs = new ArrayList<>();

		if( "1".equals(a) ) {
			breadcrumbs.add(new String[]{"/menu_emp", "Menu"});
			breadcrumbs.add(new String[]{"/worktime", "作業時間・経費入力"});
		} else {
			breadcrumbs.add(new String[]{"/menu_gen", "Menu"});
			breadcrumbs.add(new String[]{"/conf_notenter", "未入力確認"});
			breadcrumbs.add(new String[]{"/worktime", "作業時間・経費確認"});
		}
		return breadcrumbs;
	}
}
