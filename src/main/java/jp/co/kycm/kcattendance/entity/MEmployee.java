package jp.co.kycm.kcattendance.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MEmployee {
	private String			employeeId;
	private String			employeeName;
	private String			mailAddr;
	private String			password;
	private String			status;
	private String			affiliation;
	private String			deleteFlg;
	private Timestamp		createDate;
	private String			createUser;
	private String			createPrg;
	private Timestamp		updateDate;
	private String			updateUser;
	private String			updatePrg;
}
