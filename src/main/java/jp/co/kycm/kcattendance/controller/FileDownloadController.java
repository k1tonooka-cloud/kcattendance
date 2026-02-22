package jp.co.kycm.kcattendance.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jp.co.kycm.kcattendance.entity.TWorktime;
import jp.co.kycm.kcattendance.form.DownloadForm;
import jp.co.kycm.kcattendance.form.WorkTimeForm;
import jp.co.kycm.kcattendance.service.TWorktimeService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class FileDownloadController {
	private final TWorktimeService tWorktimeService;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(   @RequestParam(value = "year", required = false) Integer year
    												,@RequestParam(value = "month", required = false) Integer month
    												,@RequestParam(value = "id", required = false) String id ) {

    	TWorktime wt;
    	List <TWorktime>  list = tWorktimeService.findList01(year, month, id);
    	if( list.size() == 1 ) {
    		wt = list.get(0);
            String contentType = wt.getMineType();
            String headerValue = "attachment; filename=\"" + wt.getFileName() + "\"";
            byte[] decodedBytes = Base64.getDecoder().decode(wt.getWorkschedule());
            ByteArrayResource resource = new ByteArrayResource(decodedBytes);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);
    	} else {
            return ResponseEntity.notFound().build();
    	}
    }

    @GetMapping("/download2")
    public ResponseEntity<Resource> downloadFile2(   @ModelAttribute WorkTimeForm form,
													  Model model,
													  HttpSession session) {

    	DownloadForm df = (DownloadForm)session.getAttribute("download");
        String contentType = df.getMineType();
        String headerValue = "attachment; filename=\"" + df.getFileName() + "\"";
        byte[] decodedBytes = Base64.getDecoder().decode(df.getData());
        ByteArrayResource resource = new ByteArrayResource(decodedBytes);

        return ResponseEntity.ok()
        		.contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

}
