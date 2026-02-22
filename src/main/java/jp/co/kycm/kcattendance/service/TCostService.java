package jp.co.kycm.kcattendance.service;

import java.math.BigDecimal;
import java.util.List;

import jp.co.kycm.kcattendance.entity.TCost;

public interface TCostService {
	List<TCost> findListAll();
	List<TCost> findList01(Integer year, Integer month, String employeeid);
	BigDecimal  findList02(Integer year, Integer month, String employeeid);
	void insert01( TCost cost );
	void delete01( Integer year,Integer month,String employeeid  );
}
