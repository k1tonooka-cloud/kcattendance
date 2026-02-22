package jp.co.kycm.kcattendance.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TCost {
	private Integer			year;
	private Integer			month;
	private String			employeeId;
	private Integer			lineNo;
	private Integer	 		cost;
	private String 			comment;
	private String			status;
	private Timestamp		createDate;
	private String			createUser;
	private String			createPrg;
	private Timestamp		updateDate;
	private String			updateUser;
	private String			updatePrg;
}
