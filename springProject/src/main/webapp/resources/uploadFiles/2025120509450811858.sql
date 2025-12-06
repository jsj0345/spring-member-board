/*
  <GROUP BY 절>
  
  그룹을 묶어줄 기준을 제시할 수 있는 구문 -> 그룹함수와 같이 사용된다.
  해당 제시된 기준별로 그룹을 묶을 수 있다.
  여러개의 값들을 하나의 그룹으로 묶어 처리할 목적으로 사용
  
  [표현법]
  GROUP BY 묶어줄 기준 칼럼
*/   

--각 부서별로 총 급여의 합계
SELECT SUM(SALARY)
FROM EMPLOYEE; -- 전체 데이터가 하나의 그룹으로 처리됨

SELECT SUM(SALARY)
FROM EMPLOYEE
WHERE DEPT_CODE = 'D9'; -- D9인 사원들의 급여합계

--각 부서들 그룹만들기
SELECT DEPT_CODE , COUNT(*)
FROM EMPLOYEE
GROUP BY DEPT_CODE; -- EMPLOYEE 테이블에 있는 DEPT_CODE 데이터 별로 그룹화

--묶은 그룹 별로 합계 구하기
SELECT DEPT_CODE,SUM(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE; -- 각 그룹별로 합계 구해오기

--직급별 직급코드, 급여합, 사원수, 보너스를 받는 사원수, 평균 급여, 최소급여, 최고 급여 조회해보기
SELECT JOB_CODE
       ,SUM(SALARY)
       ,COUNT(*)
       ,COUNT(BONUS)
       ,AVG(SALARY)
       ,MIN(SALARY)
       ,MAX(SALARY)
FROM EMPLOYEE
GROUP BY JOB_CODE;

--각 부서별 총 급여의 합을 부서별 오름차순 정렬 후 조회
SELECT DEPT_CODE, SUM(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE
ORDER BY DEPT_CODE ASC;

--각 부서별 부서코드, 사원수, 보너스받는 사원수, 사수가있는 사원수, 평균급여를 부서별 오름차순 정렬 조회하기
SELECT DEPT_CODE
       ,COUNT(*)
       ,COUNT(BONUS)
       ,COUNT(MANAGER_ID)
       ,AVG(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE
ORDER BY DEPT_CODE;

--성별 별 사원수 조회
SELECT SUBSTR(EMP_NO,8,1) 성별
       ,COUNT(*)
FROM EMPLOYEE
GROUP BY SUBSTR(EMP_NO,8,1); 

--DECODE를 이용하여 성별별 조회에서 남/여로 나올 수 있도록 처리하기
SELECT DECODE(SUBSTR(EMP_NO,8,1),'1','남자','2','여자') 성별
       ,COUNT(*)
FROM EMPLOYEE
GROUP BY SUBSTR(EMP_NO,8,1);

-- 각 부서별로 평균 급여가 300만원 이상인 부서 조회해보기
SELECT DEPT_CODE
       ,AVG(SALARY)
FROM EMPLOYEE
WHERE AVG(SALARY) >= 3000000 -- WHERE절에는 그룹 함수 작성 불가능. (WHERE은 단일 행 처리이기 때문) 
GROUP BY DEPT_CODE;  

SELECT DEPT_CODE, COUNT(*)
FROM EMPLOYEE
WHERE DEPT_CODE = 'D9'
GROUP BY DEPT_CODE;

/*
    <HAVING 절>
    그룹에 대한 조건을 제시하고자 할때 사용되는 구문
    (주로 그룹 함수를 이용하여 조건 제시) - GROUP BY절과 함께 사용된다.
    위치는 GROUP BY절 뒤에 사용
    
    SELECT 구문 실행 순서
    1. FROM : 조회하고자 하는 테이블
    2. WHERE : 조건식(단일행기준(그룹함수 불가))
    3. GROUP BY : 그룹 기준에 대한 컬럼 또는 함수식
    4. HAVING : 그룹함수식에 대한 조건식(그룹조건)
    5. SELECT :조회하고자 하는 컬럼들 나열
    6. ORDER BY : 정렬 기준 작성
*/    

--각 부서별로 평균 급여가 300만원 이상인 부서 조회
SELECT DEPT_CODE
       ,ROUND(AVG(SALARY))
FROM EMPLOYEE
GROUP BY DEPT_CODE
HAVING AVG(SALARY) >= 3000000; -- 그룹함수를 이용한 조건처리

--각 직급 별 총 급여합이 1000만원 이상인 직급코드 급여합 조회
SELECT JOB_CODE
       ,SUM(SALARY)
FROM EMPLOYEE
GROUP BY JOB_CODE
HAVING SUM(SALARY) >= 10000000;

--각 직급 별 급여 평균이 300만원 이상인 직급코드, 평균급여, 사원수, 최고급여, 최소급여 조회하기
SELECT JOB_CODE
       ,AVG(SALARY)
       ,COUNT(*)
       ,MAX(SALARY)
       ,MIN(SALARY)
FROM EMPLOYEE
GROUP BY JOB_CODE
HAVING AVG(SALARY) >= 3000000;

--각 부서별 보너스를 받은 사원이 없는 부서만을 조회(부서코드 사원수)
SELECT DEPT_CODE
       ,COUNT(*)
FROM EMPLOYEE
GROUP BY DEPT_CODE
HAVING COUNT(BONUS) = 0; -- NULL을 세지 않기 때문에 0이라면 보너스를 받는 사원이 없다.

--각 부서별 평균 급여가 350만원 이하인 부서만을 조회(부서코드,평균급여)
SELECT NVL(DEPT_CODE, '부서 없음')
      ,ROUND(AVG(SALARY)) || '원' 평균급여
FROM EMPLOYEE
GROUP BY DEPT_CODE
HAVING AVG(SALARY) <= 3500000;

SELECT COUNT(*)
FROM EMPLOYEE;

SELECT COUNT(1)
FROM EMPLOYEE;

--COUNT에 선택함수를 넣어서 처리해보기
SELECT CASE WHEN SALARY > 3000000 THEN 1
       END "300만원 이상 받은 사람 수"
FROM EMPLOYEE; -- 선택함수에서 조건에 부합하지 않으면 NULL반환        

SELECT  NVL(DEPT_CODE, '부서없음') "부서코드"
       ,COUNT(*) "부서별 사원수"
       ,COUNT(CASE WHEN SALARY >= 3000000 THEN 1 END) "300이상 사원수"
FROM EMPLOYEE
GROUP BY DEPT_CODE;
       



