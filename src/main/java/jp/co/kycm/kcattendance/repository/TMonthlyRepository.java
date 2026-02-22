package jp.co.kycm.kcattendance.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.kycm.kcattendance.entity.TMonthly;

@Mapper
public interface TMonthlyRepository {
	
	// 全件検索
	List<TMonthly> selectListAll();
	// 全件検索
	List<TMonthly> select01(@Param("status") String status);
	// 挿入
	void insert01(@Param("TMonthly") TMonthly tmonthly);
	// 更新
	void update01(@Param("TMonthly") TMonthly tmonthly);
}
