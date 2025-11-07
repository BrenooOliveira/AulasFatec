-- Listar o nome e sobrenome dos empregados e o nome do departamento que ele trabalha. Devemos garantir que todos os empregados serão retornados, mesmo que haja empregados sem departamento.
SELECT emp.first_name, emp.last_name, dep.department_name  FROM employees emp
LEFT JOIN departments dep on emp.department_id = dep.department_id
ORDER BY emp.FIRST_NAME, emp.LAST_NAME

-- Listar o nome e sobrenome dos empregados, o nome do departamento que ele trabalha e o nome do cargo que ele exerce. Garantir que todos os empregados sejam retornados.
SELECT emp.first_name, emp.last_name, dep.department_name, job.job_title  FROM employees emp
LEFT JOIN departments dep on emp.department_id = dep.department_id
LEFT JOIN jobs job on emp.job_id = job.job_id
ORDER BY emp.FIRST_NAME, emp.LAST_NAME

-- # Exercício 3
-- Listar o nome e sobrenome dos empregados, o nome do departamento que ele trabalha, o nome do cargo que ele exerce e a cidade onde o departamento se localiza. Garantir que todos os empregados sejam retornados.
SELECT
    emp.first_name,
    emp.last_name,
    dep.department_name,
    job.job_title,
    loc.city
    FROM employees emp
LEFT JOIN departments dep on emp.department_id = dep.department_id
LEFT JOIN jobs job on emp.job_id = job.job_id
LEFT JOIN locations loc on dep.location_id = loc.location_id
ORDER BY emp.FIRST_NAME, emp.LAST_NAME

--# 4.
-- Listar o nome e sobrenome dos empregados, o nome do departamento que ele trabalha, o nome do cargo que ele exerce, a cidade onde o departamento se localiza e o país onde essa cidade se encontra. Garantir que todos os empregados sejam retornados.
SELECT
    emp.first_name,
    emp.last_name,
    dep.department_name,
    job.job_title,
    loc.city,
    cou.country_name

FROM employees emp
LEFT JOIN departments dep on emp.department_id = dep.department_id
LEFT JOIN jobs job on emp.job_id = job.job_id
LEFT JOIN locations loc on dep.location_id = loc.location_id
LEFT JOIN COUNTRIES cou on loc.country_id = cou.country_id
ORDER BY emp.FIRST_NAME, emp.LAST_NAME

-- # 5.
-- Listar o nome e sobrenome dos empregados, o nome do departamento que ele trabalha, o nome do cargo que ele exerce, a cidade onde o departamento se localiza, o país onde essa cidade se encontra e a região a qual esse país pertence. Garantir que todos os empregados sejam retornados.
SELECT
    emp.first_name,
    emp.last_name,
    dep.department_name,
    job.job_title,
    loc.city,
    cou.country_name,
    reg.region_name

FROM employees emp
LEFT JOIN departments dep on emp.department_id = dep.department_id
LEFT JOIN jobs job on emp.job_id = job.job_id
LEFT JOIN locations loc on dep.location_id = loc.location_id
LEFT JOIN COUNTRIES cou on loc.country_id = cou.country_id
LEFT JOIN REGIONS reg on cou.region_id = reg.region_id
ORDER BY emp.FIRST_NAME, emp.LAST_NAME

--# 6.
-- Listar o nome e sobrenome dos empregados, o nome do departamento que ele trabalha, o nome do cargo que ele exerce, a cidade onde o departamento se localiza, o país onde essa cidade se encontra e a região a qual esse país pertence. Devem ser retornados os dados de todos os empregados cujo valor do salário seja maior que a metade do salário de seu gerente (supervisor).
WITH
base AS (
    SELECT
    employee_id,
    emp.first_name,
    emp.last_name,
    dep.department_name,
    job.job_title,
    loc.city,
    cou.country_name,
    reg.region_name

FROM employees emp
LEFT JOIN departments dep on emp.department_id = dep.department_id
LEFT JOIN jobs job on emp.job_id = job.job_id
LEFT JOIN locations loc on dep.location_id = loc.location_id
LEFT JOIN COUNTRIES cou on loc.country_id = cou.country_id
LEFT JOIN REGIONS reg on cou.region_id = reg.region_id
ORDER BY emp.FIRST_NAME, emp.LAST_NAME
),

empregados_salario AS (
    SELECT
        emp.employee_id as employee_id
    FROM employees emp
    LEFT JOIN employees mgr ON emp.manager_id = mgr.employee_id
    WHERE emp.salary > NVL(mgr.salary / 2, 0)
        AND mgr.salary IS NOT NULL
),

empregados_salario_maior_metade_gerente AS (
        SELECT *
        FROM base
        WHERE employee_id IN (SELECT employee_id FROM empregados_salario)
    )
-- com filtro: 55

SELECT * FROM empregados_salario_maior_metade_gerente

--# 7.
-- Listar o nome de todos os departamentos e a quantidade de empregados que ele possui.
SELECT
    dep.department_name,
    COUNT(emp.employee_id) AS quantidade_empregados
FROM departments dep
LEFT JOIN employees emp ON dep.department_id = emp.department_id
GROUP BY dep.department_name
ORDER BY quantidade_empregados DESC

--8.
-- Listar o nome do país e a quantidade de empregados que trabalham no departamento localizado neste país
SELECT
    cou.country_name as country_name,
    COUNT(emp.employee_id) AS quantidade_empregados
FROM COUNTRIES cou
LEFT JOIN locations loc ON cou.country_id = loc.country_id
LEFT JOIN DEPARTMENTS dep ON dep.LOCATION_ID = loc.LOCATION_ID
LEFT JOIN EMPLOYEES emp ON emp.DEPARTMENT_ID = dep.DEPARTMENT_ID
GROUP BY cou.country_name
HAVING COUNT(emp.employee_id) > 0
ORDER BY quantidade_empregados DESC;

--# 9.
-- Listar o nome do departamento e o montante mensal de salários pago por cada departamento
SELECT
    dep.DEPARTMENT_NAME,
    emp.salary as mount_salary
FROM DEPARTMENTS dep
INNER JOIN (SELECT emp.DEPARTMENT_ID, sum(emp.SALARY) as salary FROM EMPLOYEES emp GROUP BY emp.DEPARTMENT_ID) emp ON emp.DEPARTMENT_ID = dep.DEPARTMENT_ID
ORDER BY emp.salary ASC

--# 10.
-- Listar o nome do cargo e a quantidade de empregados que cada cargo possui
SELECT
    job.JOB_TITLE,
    count(emp.EMPLOYEE_ID) as emp_qty
FROM JOBS job
LEFT JOIN EMPLOYEES emp ON job.JOB_ID = emp.JOB_ID
GROUP BY job.JOB_TITLE
ORDER BY job.JOB_TITLE DESC
