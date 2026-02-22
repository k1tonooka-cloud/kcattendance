package jp.co.kycm.kcattendance.entity;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConfNotenter {
	private String		employeeId;
	private String		employeeName;
	private BigDecimal	worktime;
	private String		workscheduleFlg;
	private String		workschedule;
	private Integer		status;
	private BigDecimal	costSum;
}
