package jp.co.kycm.kcattendance.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TMonthly {
	private Integer			year;
	private Integer			month;
	private Integer			status;
	private Timestamp		createDate;
	private String			createUser;
	private String			createPrg;
	private Timestamp		updateDate;
	private String			updateUser;
	private String			updatePrg;
}
