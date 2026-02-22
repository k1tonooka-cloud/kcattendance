package jp.co.kycm.kcattendance.service;

import java.util.List;

import jp.co.kycm.kcattendance.entity.ConfNotenter;
import jp.co.kycm.kcattendance.entity.MEmployee;

public interface MEmployeeService {
	List<MEmployee> findListAll();
	List<MEmployee> findList01(String mailAddr, String Password);
	List<MEmployee> findList02();
	List<ConfNotenter>findList03(Integer year, Integer month, Integer extraction, Integer orderby);
	List<ConfNotenter>findList04(Integer year, Integer month );
	void update01( String employeeId, String passwd );
	void insert01( MEmployee emploee );
	void update02( MEmployee emploee );
}
