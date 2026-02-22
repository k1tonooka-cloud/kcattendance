package jp.co.kycm.kcattendance.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class DownloadForm implements Serializable {
	String		fileName;
	String		mineType;
	String		data;
}
