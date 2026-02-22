package jp.co.kycm.kcattendance.form;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class WorktimeCostItem implements Serializable  {
	private String		EmployeeId;
	private String		EmployeeName;
	private BigDecimal	Worktime;
	private BigDecimal	Cost;
}
