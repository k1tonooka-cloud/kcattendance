package jp.co.kycm.kcattendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kycm.kcattendance.entity.ConfNotenter;
import jp.co.kycm.kcattendance.entity.MEmployee;
import jp.co.kycm.kcattendance.repository.MEmployeeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MEmployeeServiceImpl implements MEmployeeService {
	@Autowired
	private final MEmployeeRepository mEmployee;

	@Override
	@Transactional(readOnly=true)
	public  List <MEmployee>findListAll() {
		 List <MEmployee>list = mEmployee.selectListAll();
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public  List <MEmployee>findList01(String mailAddr, String password) {
		 List <MEmployee>list = mEmployee.select01(mailAddr, password);
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public  List <MEmployee>findList02() {
		 List <MEmployee>list = mEmployee.select02();
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public  List <ConfNotenter>findList03(Integer year, Integer month, Integer extraction, Integer orderby ) {
		 List <ConfNotenter>list = mEmployee.select03(year, month,extraction,orderby);
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public  List <ConfNotenter>findList04(Integer year, Integer month ) {
		 List <ConfNotenter>list = mEmployee.select04(year, month);
		return list;
	}
	@Override
	@Transactional
	public  void update01(String employeeId, String Passwd ) {
		 mEmployee.update01(employeeId, Passwd);
	}
	@Override
	@Transactional
	public  void insert01( MEmployee emploee ) {
		 mEmployee.insert01(emploee);
	}
	@Override
	@Transactional
	public  void update02( MEmployee emploee ) {
		 mEmployee.update02(emploee);
	}
	
}
