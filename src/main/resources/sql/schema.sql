

CREATE TABLE USERS (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    encoded_password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);


CREATE TABLE course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price > 0),
    creator_id BIGINT NOT NULL,
    CONSTRAINT fk_course_creator FOREIGN KEY (creator_id) REFERENCES USERS (id)
);


CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    transaction_date DATE DEFAULT CURRENT_DATE,
    CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES USERS (id),
    CONSTRAINT fk_transaction_course FOREIGN KEY (course_id) REFERENCES course (id)
);
