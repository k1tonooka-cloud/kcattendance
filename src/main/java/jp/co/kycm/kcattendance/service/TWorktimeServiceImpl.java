package jp.co.kycm.kcattendance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.kycm.kcattendance.entity.TWorktime;
import jp.co.kycm.kcattendance.repository.TWorktimeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TWorktimeServiceImpl implements TWorktimeService {
	@Autowired
	private final TWorktimeRepository tWorktime;

	@Override
	@Transactional(readOnly=true)
	public  List <TWorktime>findListAll() {
		 List <TWorktime>list = tWorktime.selectListAll();
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public  List <TWorktime> findList01(Integer year,Integer month,String employeeId) {
		 List <TWorktime>list = tWorktime.select01(year,month,employeeId);
		return list;
	}

	@Override
	@Transactional
	public  void update01(TWorktime w) {
		tWorktime.update01(w);
		return;
	}
	@Override
	@Transactional
	public  void insert01(TWorktime w) {
		tWorktime.insert01(w);
		return;
	}

}

