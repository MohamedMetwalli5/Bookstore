DELIMITER $$
SET GLOBAL event_scheduler=ON;
CREATE EVENT DELETE_SALES_MORE_THAN_3MONTHS
ON SCHEDULE EVERY 1 DAY 
	DO 
		BEGIN 
			DELETE FROM SALES WHERE SALE_TIME < (NOW() - INTERVAL 3 MONTH);
	END $$
DELIMITER ;