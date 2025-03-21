CREATE TABLE users
(
    id            UUID PRIMARY KEY,
    version       BIGINT default 0,
    department_id UUID REFERENCES department (id),
    first_name    VARCHAR(128)       NOT NULL,
    last_name     VARCHAR(128)       NOT NULL,
    middle_name   VARCHAR(128),
    password      VARCHAR(64)        NOT NULL,
    email         VARCHAR(64) UNIQUE NOT NULL,
    role          VARCHAR(64)        NOT NULL,
    position      VARCHAR(64),
    salary        NUMERIC(12, 2),
    phone         VARCHAR(32),
    hire_date     DATE,
    created_by    VARCHAR(64),
    updated_by    VARCHAR(64),
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP
);

CREATE TABLE department
(
    id          UUID PRIMARY KEY,
    head_id     UUID REFERENCES users (id),
    name        VARCHAR(128) UNIQUE NOT NULL,
    description VARCHAR(1000),
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    created_by  VARCHAR(64),
    updated_by  VARCHAR(64)
);


CREATE TABLE candidate
(
    id               UUID PRIMARY KEY,
    version       BIGINT default 0,
    hr_id            UUID REFERENCES users (id),
    first_name       VARCHAR(128)       NOT NULL,
    last_name        VARCHAR(128)       NOT NULL,
    middle_name      VARCHAR(128),
    email            VARCHAR(64) UNIQUE NOT NULL,
    phone            VARCHAR(32),
    skills           VARCHAR(1000),
    status           VARCHAR(64)        NOT NULL,
    another_contact  VARCHAR(128),
    desired_position VARCHAR(64)        NOT NULL,
    birth_date       DATE,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    created_by       VARCHAR(64),
    updated_by       VARCHAR(64)
);

CREATE TABLE vacancy
(
    id            UUID PRIMARY KEY,
    hr_id         UUID REFERENCES users (id),
    department_id UUID REFERENCES department (id),
    title         VARCHAR(64),
    description   VARCHAR(1000),
    status        VARCHAR(64) NOT NULL,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    created_by    VARCHAR(64),
    updated_by    VARCHAR(64)
);

CREATE TABLE interview
(
    id             UUID PRIMARY KEY,
    candidate_id   UUID REFERENCES candidate (id),
    interviewer_id UUID REFERENCES users (id),
    vacancy_id     UUID REFERENCES vacancy (id),
    result         VARCHAR(1000),
    date_time      DATE,
    notes          VARCHAR(1000),
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    created_by     VARCHAR(64),
    updated_by     VARCHAR(64)
);
