package com.kh.opendata.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DataVO {
	
									
		private String clearVal;		//		"clearVal":해제시 미세먼지 농도(단위 :ug/m3)
		private String sn;				//		"sn": 미세먼지 경보 현황 일련번호
		private String districtName;	//		"districtName": 발령 지역 이름
		private String dataDate;		//		"dataDate":발령 날짜
		private String issueVal;		//		"issueVal":발령시 미세먼지 농도(단위 :ug/m3)
		private String issueTime;		//		"issueTime":발령 시간
		private String clearDate;	    //		"clearDate":해제 날짜
		private String issueDate;		//		"issueDate":발령 날짜
		private String moveName;		//		"moveName":발령 권역 이름
		private String clearTime;		//		"clearTime":해제 시간
		private String issueGbn;		//		"issueGbn":경보단계(주의보, 경보)
	 	private String itemCode;		//      "itemCode":미세먼지 항목 구분(PM10, PM25)
	
	

}
