package jp.co.kycm.kcattendance.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.kycm.kcattendance.entity.TWorktime;

@Mapper
public interface TWorktimeRepository {
	
	// 全件検索
	List<TWorktime> selectListAll();
	// 年、月、社員ID検索
	List<TWorktime> select01(@Param("year") Integer year,
							 @Param("month") Integer month,
							 @Param("employee_id") String employeeId);
	// 作業時間入力（更新）
	void update01(@Param("worktime") TWorktime tWorktime);
	// 作業時間入力（挿入）
	void insert01(@Param("worktime") TWorktime tWorktime);
}
