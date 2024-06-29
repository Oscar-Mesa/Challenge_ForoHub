CREATE TABLE tbl_course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL
);

-- Crear tabla para User
CREATE TABLE tbl_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Crear tabla para Profile
CREATE TABLE tbl_profile (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Crear tabla intermedia para la relación Many-to-Many entre User y Profile
CREATE TABLE tbl_user_profile (
    user_id BIGINT NOT NULL,
    profile_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, profile_id),
    FOREIGN KEY (user_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (profile_id) REFERENCES tbl_profile(id) ON DELETE CASCADE
);


create table tbl_topic(
    id bigint auto_increment primary key,
    title varchar(100) not null,
    message TEXT not null,
    creation_date TIMESTAMP not null,
    status BOOLEAN NOT NULL,
    author_id BIGINT,
    course_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES tbl_user(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES tbl_course(id) ON DELETE CASCADE
);

CREATE TABLE tbl_answer(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message TEXT NOT NULL,
    topic_id BIGINT NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    author_id BIGINT NOT NULL,
    solution BOOLEAN,
    FOREIGN KEY (topic_id) REFERENCES tbl_topic(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES tbl_user(id) ON DELETE CASCADE
);



