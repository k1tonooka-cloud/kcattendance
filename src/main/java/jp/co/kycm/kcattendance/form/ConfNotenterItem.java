package jp.co.kycm.kcattendance.form;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConfNotenterItem implements Serializable {
	private String		EmployeeId;			/* 社員番号   */
	private String		EmployeeName;		/* 社員氏名   */
	private BigDecimal	Worktime;			/* 作業時間   */
	private String		WorkscheduleFlg;	/* 勤務表添付 */
	private String		Workschedule;		/* 勤務表ファイル */
	private BigDecimal	Cost;				/* 経費       */
	private String		Send;				/* 送信済     */
}
