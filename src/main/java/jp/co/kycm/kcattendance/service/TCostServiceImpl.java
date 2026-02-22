package jp.co.kycm.kcattendance.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kycm.kcattendance.entity.TCost;
import jp.co.kycm.kcattendance.repository.TCostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TCostServiceImpl implements TCostService {
	@Autowired
	private final TCostRepository tCost;

	@Override
	@Transactional(readOnly=true)
	public  List <TCost>findListAll() {
		 List <TCost>list = tCost.selectListAll();
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public  List <TCost> findList01(Integer year, Integer month, String employeeid) {
		 List <TCost>list = tCost.select01(year, month, employeeid);
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public  BigDecimal  findList02(Integer year, Integer month, String employeeid) {
		BigDecimal sum = tCost.select02(year, month, employeeid);
		return sum;
	}
	@Override
	@Transactional
	public  void  insert01(TCost cost) {
		tCost.insert01(cost);
		return;
	}
	@Override
	@Transactional
	public  void  delete01(Integer year,Integer month,String employeeid ) {
		tCost.delete01(year, month, employeeid);
		return;
	}
	
}
