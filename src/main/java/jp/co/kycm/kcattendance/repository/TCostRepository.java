package jp.co.kycm.kcattendance.repository;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.kycm.kcattendance.entity.TCost;

@Mapper
public interface TCostRepository {
	
	// 全件検索
	List<TCost> selectListAll();
	// 全件検索
	List<TCost> select01(@Param("year") Integer year,
						 @Param("month") Integer month,
						 @Param("employee_id") String employeeid);
	// 合計値
	BigDecimal select02(@Param("year") Integer year,
			 @Param("month") Integer month,
			 @Param("employee_id") String employeeid);
	// INSERT
	void insert01(@Param("cost") TCost cost );
	
	// DELETE
	void delete01(@Param("year") Integer year,
			 @Param("month") Integer month,
			 @Param("employee_id") String employeeid);
}
