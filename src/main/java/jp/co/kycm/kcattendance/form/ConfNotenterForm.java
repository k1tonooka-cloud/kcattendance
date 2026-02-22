package jp.co.kycm.kcattendance.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ConfNotenterForm implements Serializable {
	private List<ConfNotenterItem> confNotenterItems;
	
    public ConfNotenterForm(List<ConfNotenterItem> confNotentItems) {
        this.confNotenterItems = confNotentItems;
    }

	private int year;						/* 年 */
	private int month;						/* 月 */
	
	private int extraction;				/* 抽出条件プルダウン */
	private int orderby;					/* ソートプルダウン   */
	
}
