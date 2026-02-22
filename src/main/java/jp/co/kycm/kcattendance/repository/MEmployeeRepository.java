package jp.co.kycm.kcattendance.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.kycm.kcattendance.entity.ConfNotenter;
import jp.co.kycm.kcattendance.entity.MEmployee;

@Mapper
public interface MEmployeeRepository {
	
	// 全件検索
	List<MEmployee> selectListAll();
	// ログインチェック用
	List<MEmployee> select01(@Param("mail_addr") String MailAddr,
							@Param("password") String Password);
	// 社員マスタメンテナンス用
	List<MEmployee> select02();
	// 年、月、条件検索
	List<ConfNotenter> select03(@Param("year") Integer year,
							 @Param("month") Integer month,
							 @Param("extraction") Integer extraction,
							 @Param("orderby") Integer orderby);
	// 月次チェック用
	List<ConfNotenter> select04(@Param("year") Integer year,
							 @Param("month") Integer month);
	// パスワード変更による更新
	void update01( @Param("employee_id") String employeeId,
					@Param("passwd") String Passwd );
	// 社員マスタメンテナンス（挿入）
	void insert01( @Param("MEmployee") MEmployee emploee);
	
	// 社員マスタメンテナンス（更新）
	void update02( @Param("MEmployee") MEmployee emploee);
}
