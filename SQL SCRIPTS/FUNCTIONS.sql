-- SIGN IN 
DELIMITER $$
CREATE FUNCTION SIGN_IN(USER_NAME VARCHAR(45),PASS VARCHAR(45))
RETURNS BOOLEAN 
deterministic BEGIN 
	DECLARE EX_PASS VARCHAR(45);
    SELECT PASSWORD INTO EX_PASS FROM USERS WHERE NAME = USER_NAME;
	IF (STRCMP(BINARY PASS,EX_PASS)=0) THEN 
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$
DELIMITER ;

DELIMITER $$
CREATE FUNCTION IS_MANAGER(USER_NAME VARCHAR(45))
RETURNS BOOLEAN
deterministic BEGIN 
	IF (BINARY USER_NAME IN (SELECT NAME FROM MANAGERS)) THEN 
		RETURN TRUE;
	ELSE
		RETURN FALSE;
	END IF;
END $$
DELIMITER ;



