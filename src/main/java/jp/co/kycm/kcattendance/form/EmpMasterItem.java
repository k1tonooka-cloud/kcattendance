package jp.co.kycm.kcattendance.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmpMasterItem implements Serializable {
	private boolean div;
	private boolean checkbox;

	@NotEmpty(message = "社員番号を入力して下さい")
	@Pattern(regexp="^[0-9][0-9][0-9]", message="社員番号は3桁の数字を入力して下さい")
	private String   employeeId;

	@NotEmpty(message = "氏名を入力して下さい")
	private String employeeName;

	@NotEmpty(message = "メールアドレスを入力して下さい")
	private String   mailAddr;
	private String   status;
	private String   statusName;
	private String   affiliation;
	private String   affiliationName;
	private boolean deleteFlg;
	private boolean readOnly;
	private boolean newrec;
}
