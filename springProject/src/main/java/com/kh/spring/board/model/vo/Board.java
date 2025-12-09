package com.kh.spring.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Board {
	
	private int boardNo; // 게시글번호					//	BOARD_NO	NUMBER
	private String boardTitle; //게시글제목				//	BOARD_TITLE	VARCHAR2(100 BYTE)
	private String boardWriter; //게시글작성자아이디		//	BOARD_WRITER	VARCHAR2(4000 BYTE)
	private String boardContent; // 게시글내용					//	BOARD_CONTENT	VARCHAR2(4000 BYTE)
	private String originName; //첨부파일원래이름					//	ORIGIN_NAME	VARCHAR2(100 BYTE)
	private String changeName; //첨부파일변경이름					//	CHANGE_NAME	VARCHAR2(100 BYTE)
	private int count; //조회수					//	COUNT	NUMBER
	private String createDate; //게시글작성날짜					//	CREATE_DATE	DATE
	private String status; //게시글상태값					//	STATUS	VARCHAR2(1 BYTE)
	
}

/*
 * public class Board {
 *     private int boardNo; // 게시글번호
 *     private String boardTitle; // 게시글제목
 *     private String boardWriter; // 게시글 작성자 아이디
 *     private String boardContent; // 게시글 내용
 *     private String originName; // 첨부파일 원래 이름
 *     private String changeName; // 첨부파일 변경 이름
 *     private int count; // 조회수
 *     private Date createDate; // 게시글 작성 날짜
 *     private String status; // 게시글상태값 
 */
