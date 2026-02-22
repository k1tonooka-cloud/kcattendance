package jp.co.kycm.kcattendance.service;

import java.util.List;

import jp.co.kycm.kcattendance.entity.TWorktime;

public interface TWorktimeService {
	List<TWorktime> findListAll();
	List<TWorktime> findList01(Integer year,Integer month, String employeeId);
	void update01(TWorktime w);
	void insert01(TWorktime w);
}
