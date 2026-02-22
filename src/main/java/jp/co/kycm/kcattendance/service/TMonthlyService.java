package jp.co.kycm.kcattendance.service;

import java.util.List;

import jp.co.kycm.kcattendance.entity.TMonthly;

public interface TMonthlyService {
	List<TMonthly> findListAll();
	List<TMonthly> findList01(String status);
	void insert01(TMonthly tmonthly);
	void update01(TMonthly tmonthly);
}
