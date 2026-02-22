package jp.co.kycm.kcattendance.form;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;

@Data
public class EmpMasterForm {
	@Valid
	private List<EmpMasterItem> empMasterItems;
	
    public EmpMasterForm(List<EmpMasterItem> empMasterItems) {
        this.empMasterItems = empMasterItems;
    }
	private String		msg;
	private String		action;
	private Integer		no;

	@AssertTrue(message = "社員番号が重複しています")
	public boolean isDuplicate() {
		for( int j = 0; j < empMasterItems.size(); j++ ) {
			for( int i = j+1; i < empMasterItems.size(); i++ ) {
				if( empMasterItems.get(i).getEmployeeId().equals(empMasterItems.get(j).getEmployeeId())) {
					return false;
				}
			}
		}
		return true;
	}

}
