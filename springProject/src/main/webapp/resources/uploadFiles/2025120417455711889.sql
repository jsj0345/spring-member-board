-- KH_서브쿼리_2,10,11.SQL

--KH_JOIN및서브쿼리 문제결과 파일에서 2,10,11번 

-- 2. 나이 상 가장 막내의 사원 코드, 사원 명, 나이, 부서 명, 직급 명 조회

SELECT ROWNUM,A.*
FROM  (SELECT EMP_ID
       ,EMP_NAME
       ,EXTRACT(YEAR FROM SYSDATE) - (19||SUBSTR(EMP_NO,1,2)) "나이"
       ,DEPT_TITLE
       ,JOB_NAME
        FROM EMPLOYEE E, DEPARTMENT D, JOB J
        WHERE E.DEPT_CODE = D.DEPT_ID
        AND E.JOB_CODE = J.JOB_CODE 
        ORDER BY "나이") A
WHERE ROWNUM = 1; 

-- 10. 보너스 포함한 연봉이 높은 5명의 사번, 이름, 부서 명, 직급, 입사일, 순위 조회

SELECT * 
FROM (SELECT EMP_ID
       ,EMP_NAME
       ,DEPT_TITLE
       ,HIRE_DATE
       ,RANK() OVER(ORDER BY (SALARY + (SALARY * NVL(BONUS,0)))* 12 DESC) "순위" 
        FROM EMPLOYEE E, DEPARTMENT D, JOB J
        WHERE E.DEPT_CODE = D.DEPT_ID
        AND E.JOB_CODE = J.JOB_CODE) 
WHERE 순위 <= 5;

-- 11.  부서 별 급여 합계가 전체 급여 총 합의 20%보다 많은 부서의 부서 명, 부서 별 급여 합계 조회
-- 11-1. JOIN과 HAVING 사용

SELECT DEPT_TITLE
       ,SUM(SALARY)
FROM DEPARTMENT D, EMPLOYEE E
WHERE E.DEPT_CODE = D.DEPT_ID
GROUP BY DEPT_TITLE
HAVING SUM(SALARY) > (SELECT SUM(SALARY) * 0.2
                      FROM EMPLOYEE);

-- 11-2. 인라인 뷰 사용

SELECT *
FROM (SELECT DEPT_TITLE
       ,SUM(SALARY)
    FROM DEPARTMENT D, EMPLOYEE E
    WHERE E.DEPT_CODE = D.DEPT_ID
    GROUP BY DEPT_TITLE
    HAVING SUM(SALARY) > (SELECT SUM(SALARY) * 0.2
                      FROM EMPLOYEE));
                      
-- 11-3. WITH 사용
WITH SAL_TOTAL AS (
                SELECT DEPT_TITLE
                        ,SUM(SALARY)
                FROM DEPARTMENT D, EMPLOYEE E
                WHERE E.DEPT_CODE = D.DEPT_ID
                GROUP BY DEPT_TITLE
                HAVING SUM(SALARY) > (SELECT SUM(SALARY) * 0.2
                                  FROM EMPLOYEE)

                 )       
SELECT *
FROM SAL_TOTAL;