package jp.co.kycm.kcattendance.form;

import lombok.Data;

@Data
public class MenuForm {
	private Integer  year;
	private Integer  month;
	private String   employeeId;
	private String   employeeName;
	private String   mailAddr;
	private String   passwd;
	private String   status;
	private String   affiliation;
	private boolean savePasswd;
}
