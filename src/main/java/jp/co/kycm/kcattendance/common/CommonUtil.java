//  *************************************************************
//  * 共通クラス                                                *
//  *************************************************************


package jp.co.kycm.kcattendance.common;

public class CommonUtil {
	// ****** ログインチェック ******
	// ** 戻り値　　1  従業員       *
	// **         　2  総務         *
	// **          -1  エラー       *
	// ******************************
	
	public static int login_check(String login, String passwd) {
		if( "emp".equals(login) && "emp".equals(passwd)) {
			return 1;
		}
		if( "general".equals(login) && "general".equals(passwd)) {
			return 2;
		}
		return -1;
	}
	public static String send_status(Integer status) {
		if(status != null ) {
			if( status == 0 ) {
				return "未";
			} else {
				return "済";
			}
			
		}
		return "未";
	}
}
