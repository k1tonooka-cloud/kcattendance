package jp.co.kycm.kcattendance.common;

import java.util.Map;

public class CommonMap {
	public static Map<String,String> statusMap = Map.of(
			 "1","稼働中",
			 "2","休業中",
			 "3","退職済"
	);
	public static Map<String,String> affiliationMap = Map.of(
			 "1","開発部",
			 "2","総務部",
			 "3","業務部"
	);
	

}
