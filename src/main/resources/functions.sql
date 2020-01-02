DROP PROCEDURE IF EXISTS limfy.generate_results;
DROP FUNCTION IF EXISTS limfy.get_heartbeat_average;

DELIMITER $$
$$
CREATE DEFINER=`limfy`@`%` FUNCTION `limfy`.`get_heartbeat_average`(param_user_id VARCHAR(36), measurements_date DATE) RETURNS FLOAT
BEGIN
    DECLARE average FLOAT DEFAULT 0.0;
    DECLARE c_heartbeat INTEGER DEFAULT 0;
    DECLARE c_shakiness INTEGER DEFAULT 0;
    DECLARE c_counter INTEGER DEFAULT 0;
    DECLARE c_measurement CURSOR FOR SELECT m.heartbeat, m.shakiness FROM measurement m WHERE m.user_id = param_user_id AND DATE(m.timestamp) = measurements_date;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET c_counter = -1;

    OPEN c_measurement;
    c_loop: LOOP
        FETCH FROM c_measurement INTO c_heartbeat, c_shakiness;
        IF c_counter = -1 THEN
            LEAVE c_loop;
        ELSEIF c_counter = 0 THEN
            SELECT AVG(m.heartbeat) INTO average FROM measurement m WHERE m.user_id = param_user_id AND DATE(m.timestamp) = measurements_date;
        ELSE
            SET average = (average * ((c_counter - 1) - ((100 - c_shakiness) / 100) + 1) + ((100 - c_shakiness) / 100) * c_heartbeat) / c_counter;
        END IF;

        SET c_counter = c_counter + 1;
    END LOOP c_loop;
    CLOSE c_measurement;

    RETURN average;
END
$$
DELIMITER ;


DELIMITER $$
$$
CREATE DEFINER=`limfy`@`%` PROCEDURE `limfy`.`generate_results`(user_id VARCHAR(36))
BEGIN
    DECLARE c_counter INTEGER DEFAULT 0;
    DECLARE c_timestamp DATETIME DEFAULT CURDATE();
    DECLARE c_steps INTEGER DEFAULT 0;
    DECLARE c_measurement CURSOR FOR SELECT m.timestamp, SUM(m.steps) FROM measurement m WHERE m.user_id = user_id GROUP BY DAY(m.`timestamp`) ORDER BY m.`timestamp` DESC;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET c_counter = -1;
    DELETE FROM measurement_result mr WHERE mr.user_id = user_id;

    OPEN c_measurement;
    c_loop: LOOP
        FETCH FROM c_measurement INTO c_timestamp, c_steps;
        IF c_counter = -1 THEN
            LEAVE c_loop;
        END IF;

        INSERT INTO measurement_result (user_id, `timestamp`, heartbeat, steps) VALUES (user_id, c_timestamp, get_heartbeat_average(user_id, c_timestamp), c_steps);
    END LOOP c_loop;
    CLOSE c_measurement;
END
$$
DELIMITER ;
