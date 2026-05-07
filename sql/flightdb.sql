CREATE TABLE flight (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    origin VARCHAR(255),
    destination VARCHAR(255),
    departure_time VARCHAR(255),
    price DOUBLE
);

CREATE TABLE flight_seat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flight_id BIGINT,
    seat_number INT,
    reserved BOOLEAN,
    CONSTRAINT fk_flight
        FOREIGN KEY (flight_id)
        REFERENCES flight(id)
        ON DELETE CASCADE
);
