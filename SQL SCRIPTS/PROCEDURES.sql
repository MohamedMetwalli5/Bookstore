-- -----------------------------------------------------
-- Report procedures
-- -----------------------------------------------------
DELIMITER $$ 
CREATE PROCEDURE TOTAL_BOOK_SALES_LAST_MONTH() 
BEGIN 
	SELECT B.ISBN,B.TITLE,S.TOTAL_SALES 
	FROM BOOKS AS B  JOIN (
							SELECT ISBN,SUM(QUANTITY) AS TOTAL_SALES
							FROM SALES 
                            WHERE SALE_TIME > NOW() - INTERVAL 1 MONTH
							GROUP BY ISBN) AS S
							ON B.ISBN=S.ISBN;
END$$
DELIMITER ;
DELIMITER $$
CREATE PROCEDURE TOP_FIVE_CUSTOMERS()
BEGIN
	SELECT USER_NAME, SUM(SALE_PRICE) AS PURCHASE_AMOUNT 
	FROM SALES 
	GROUP BY USER_NAME
	ORDER BY PURCHASE_AMOUNT DESC
	LIMIT 5;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE TOP_10_BOOKS()
BEGIN 
	SELECT B.ISBN,B.TITLE,S.TOTAL_SALES
    FROM BOOKS AS B JOIN(
					SELECT ISBN,SUM(QUANTITY) AS TOTAL_SALES
                    FROM SALES 
                    GROUP BY ISBN) AS S
					ON B.ISBN=S.ISBN
	ORDER BY S.TOTAL_SALES DESC
    LIMIT 10;
END $$
DELIMITER ;

                    
                    
                    
                    
                    
                    
                    
                    
                    
