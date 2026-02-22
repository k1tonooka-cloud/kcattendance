package jp.co.kycm.kcattendance.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class WorktimeCostForm implements Serializable  {
	private List<WorktimeCostItem> worktimeCostItems;
	public WorktimeCostForm(List<WorktimeCostItem> worktimeCostItem) {
		this.worktimeCostItems = worktimeCostItem;
	}
	private int year;						/* 年 */
	private int month;						/* 月 */
}
