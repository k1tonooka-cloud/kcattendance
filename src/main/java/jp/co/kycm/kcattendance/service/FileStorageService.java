package jp.co.kycm.kcattendance.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jp.co.kycm.kcattendance.entity.FileDB;
import jp.co.kycm.kcattendance.repository.FileDBRepository;

@Service
public class FileStorageService {
	  @Autowired
	  private FileDBRepository fileDBRepository;

	  public FileDB store(MultipartFile file) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    FileDB FileDB = new FileDB(fileName, file.getContentType(), file.getBytes());

	    return FileDB;
	    //return fileDBRepository.save(FileDB);
	  }

}
