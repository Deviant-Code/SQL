SELECT address, salary
FROM EMPLOYEE
WHERE name = 'Joyce English';

SELECT fname, Bdate
FROM EMPLOYEE
WHERE Dno IN(1, 4);

SELECT fname, Minit, Lname
FROM EMPLOYEE
WHERE SEX = 'F' AND Salary < 30000;

SELECT Fname, Minit, Lname, Address
FROM EMPLOYEE
WHERE ADDRESS = '%Houston%';

SELECT Dnumber, Dlocation
From DEPT_LOCATIONS
WHERE Dnumber IN(1,5);

SELECT Fname, Minit, Lname
FROM EMPLOYEE 
WHERE Bdate <= '19530709'