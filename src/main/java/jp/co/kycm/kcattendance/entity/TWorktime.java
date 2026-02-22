package jp.co.kycm.kcattendance.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class TWorktime {
	private Integer			year;
	private Integer			month;
	private String			employeeId;
	private BigDecimal		worktime;
	private String			workscheduleFlg;
	private String			fileName;
	private String			mineType;
	private String			workschedule;
	private String			status;
	private Timestamp 		createDate;
	private String			createUser;
	private String			createPrg;
	private Timestamp 		updateDate;
	private String			updateUser;
	private String			updatePrg;
}
