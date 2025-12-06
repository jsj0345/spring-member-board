/*
  -- 파일명 : 07_DML(INSERT, UPDATE, DELETE)_KH계정_연습2.SQL 
  
  DML(DATA MANIPULATION LANGUAGE)
  
  데이터 조작언어
  테이블에 새로운 데이터를 삽입(INSERT)하거나
  기존의 데이터를 수정(UPDATE)하거나
  삭제(DELETE) 하는 구문
  
  1. INSERT : 테이블에 새로운 행을 추가하는 구문
  
  [표현법]
  INSERT INTO 테이블명 VALUES(값1,값2,...);
  -해당 테이블에 모든 컬럼에 데이터를 추가하고자 할때 사용하는 구문
  -컬럼의 순서, 자료형 개수를 맞춰서 VALUES 괄호 안에 나열해야한다.
  -나열된 데이터가 정해진 컬럼보다 적을 경우 : NOT ENOUGH VALUES 오류
  -나열된 데이터가 정해진 컬럼보다 많을 경우 : NOT MANY VALUES 오류 발생
  
*/

--EMPLOYEE 테이블에 사원 데이터 추가하기

SELECT *
FROM EMPLOYEE;

INSERT INTO EMPLOYEE
VALUES(900,'김유저','000101-3322333','user01@gmail.com',
      '01033335555','D5','J6','S5',3500000,0.8,214,SYSDATE,NULL,DEFAULT);
      
/*
  2) INSERT INTO 테이블명(컬럼명, 컬럼명, ...) VALUES(값,값,...);
  - 해당 테이블에 특정 컬럼만 선택하여 해당 컬럼에 추가할 값 제시할 때 사용
  - 한 행 단위로 추가되기 때문에 선택되지 않은 컬럼은 기본값이 삽입된다 (NULL 또는 DEFAULT)
  - DEFAULT 설정이 되어있다면 해당 값이 삽입된다.
  - NOT NULL 제약조건이 설정되어 있다면 선택하여 값을 제시하거나 DEFAULT 값이 설정되어야한다.
*/      

INSERT INTO EMPLOYEE (EMP_ID
                      ,EMP_NAME
                      ,EMP_NO
                      ,JOB_CODE
                      ,SAL_LEVEL)
VALUES(901, '김사원', '990505-4433222','J6','S6');

SELECT *
FROM EMPLOYEE
WHERE EMP_ID = '901'; -- 자동 형변환이 되어서 들어간 것. ENT_YN은 DEFAULT

/*
  3) INSERT INTO 테이블명 (서브쿼리);
  -VALUES로 직접 값을 기입하는것이 아니라
  서브쿼리로 조회된 데이터를 INSERT하는 구문
  여러행을 한번에 INSERT 할 수 있다.
*/

--테이블 생성
CREATE TABLE EMP_04 (
   EMP_ID NUMBER,
   EMP_NAME VARCHAR2(30),
   DEPT_TITLE VARCHAR2(20)
); 

SELECT *
FROM EMP_04;

--전체 사원들의 사번, 이름, 부서명을 조회한 결과를 테이블에 넣어보기
SELECT EMP_ID
       ,EMP_NAME
       ,NVL(DEPT_TITLE, '부서 없음')
FROM EMPLOYEE
LEFT JOIN DEPARTMENT ON (EMPLOYEE.DEPT_CODE = DEPARTMENT.DEPT_ID); 

INSERT INTO EMP_04 (SELECT EMP_ID
                        ,EMP_NAME
                        ,NVL(DEPT_TITLE, '부서 없음')
                        FROM EMPLOYEE
                        LEFT JOIN DEPARTMENT ON (EMPLOYEE.DEPT_CODE = DEPARTMENT.DEPT_ID));
                        
SELECT *
FROM EMP_04;

/*
  INSERT ALL
  두개 이상의 테이블에 각각 INSERT 할때 사용하는 구문
  조건 : 사용하는 서브쿼리가 동일해야한다.
  [표현법]
  INSERT ALL
  INTO 테이블명1 VALUES (컬럼명, 컬럼명, ...)
  INTO 테이블명2 VALUES (컬럼명, 컬럼명, ...)
  서브쿼리;
  
  -- 테스트용 테이블 생성하기
  첫번째 테이블 사번, 사원명, 직급명
  두번째 테이블 사번, 사원명, 부서명
  
  두 테이블 모두 데이터 없이 EMPLOYEE 테이블 형식만 참조하여 만들기
  첫번째 테이블은 CREATE TABLE 구문으로 각 데이터 컬럼 정의
  두번째 테이블은 CREATE TABLE 서브쿼리 구문으로 생성하기
*/

CREATE TABLE EMP_JOB_2(
  EMP_ID VARCHAR2(20),
  EMP_NAME VARCHAR2(20),
  JOB_NAME VARCHAR2(20)
); 

CREATE TABLE EMP_JOB_3 
AS (SELECT EMP_ID
           ,EMP_NAME
           ,DEPT_TITLE
    FROM EMPLOYEE E      
    JOIN DEPARTMENT D ON E.DEPT_CODE = D.DEPT_ID
    WHERE 1=0);
    
SELECT * FROM EMP_JOB_2;
SELECT * FROM EMP_JOB_3;

--EMP_JOB_2, EMP_DEPT 테이블에 각각 조회된 데이터 넣어보기
--급여 300만원 이상 받는 사원들의 사번, 이름, 직급명, 부서명 조회

--조회구문
INSERT ALL 
       INTO EMP_JOB_2 VALUES(EMP_ID, EMP_NAME, JOB_NAME)
       INTO EMP_JOB_3 VALUES(EMP_ID, EMP_NAME, DEPT_TITLE)
SELECT EMP_ID
       ,EMP_NAME
       ,JOB_NAME
       ,DEPT_TITLE
FROM EMPLOYEE E
JOIN JOB J ON E.JOB_CODE = J.JOB_CODE
JOIN DEPARTMENT D ON E.DEPT_CODE = D.DEPT_ID
WHERE SALARY >= 3000000; 

SELECT * FROM EMP_JOB_2;

SELECT * FROM EMP_JOB_3;

INSERT ALL
   INTO EMP_JOB VALUES(300,'김데모','대리')
   INTO EMP_JOB VALUES(301,'박연습','사원')
   INTO EMP_JOB VALUES(302,'최과장','과장')
SELECT *
FROM DUAL; -- 가상 테이블을 이용하여 서브쿼리 구문 작성, 나중에 MYBATIS에서 쓸 수 있음.

-- SELECT * FROM DUAL; 에서 3개의 행이 나왔다고 생각하자
-- 그럼 1개씩마다 INSERT ALL 에 들어가서 실행 즉, 3 X 3 = 9

SELECT * FROM EMP_JOB;
    
/*
  INSERT ALL
    WHEN 조건1 THEN
    INTO 테이블명 VALUES(컬럼명, 컬럼명, ...)
    WHEN 조건2 THEN
    INTO 테이블명 VALUES(컬럼명, 컬럼명)
  서브쿼리
  
  -조건에 맞는 값들을 삽입하겠다.
*/

--테스트용 테이블 생성
--사번, 사원명, 입사일, 급여를 담을 테이블 EMP_OLD/EMP_NEW 두개 만들기

CREATE TABLE EMP_OLD_07
AS (SELECT EMP_ID
           ,EMP_NAME
           ,HIRE_DATE
           ,SALARY
    FROM EMPLOYEE 
    WHERE 1=0);
 
CREATE TABLE EMP_NEW_07
AS (SELECT EMP_ID
           ,EMP_NAME
           ,HIRE_DATE
           ,SALARY
     FROM EMPLOYEE
     WHERE 1 = 0);
     
SELECT * FROM EMP_NEW_07;
SELECT * FROM EMP_OLD_07;

--서브쿼리로 이용할 구문 작성
--2010년 이전 이후 조건으로 조회

INSERT ALL
   WHEN HIRE_DATE < '2010/01/01' THEN
   INTO EMP_OLD_07(EMP_ID,EMP_NAME,HIRE_DATE,SALARY)
   WHEN HIRE_DATE >= '2010/01/01' THEN
   INTO EMP_NEW_07(EMP_ID,EMP_NAME,HIRE_DATE,SALARY)
SELECT EMP_ID,EMP_NAME,HIRE_DATE,SALARY
FROM EMPLOYEE;

-----------------------------------------------------------------------------------------


    
   

      
      