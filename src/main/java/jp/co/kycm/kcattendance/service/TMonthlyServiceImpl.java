package jp.co.kycm.kcattendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kycm.kcattendance.entity.TMonthly;
import jp.co.kycm.kcattendance.repository.TMonthlyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TMonthlyServiceImpl implements TMonthlyService {
	@Autowired
	private final TMonthlyRepository mMonthly;

	@Override
	@Transactional(readOnly=true)
	public  List <TMonthly>findListAll() {
		 List <TMonthly>list = mMonthly.selectListAll();
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public  List <TMonthly> findList01(String status) {
		 List <TMonthly>list = mMonthly.select01(status);
		return list;
	}
	@Override
	@Transactional
	public  void insert01(TMonthly tmonthly) {
		mMonthly.insert01(tmonthly);
	}
	@Override
	@Transactional
	public  void update01(TMonthly tmonthly) {
		 mMonthly.update01(tmonthly);
	}
	
}
