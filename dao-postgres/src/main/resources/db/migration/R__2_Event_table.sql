CREATE TABLE event (
                       id BIGSERIAL PRIMARY KEY,
                       name TEXT NOT NULL,
                       start_time TIMESTAMP NOT NULL,
                       end_time TIMESTAMP NOT NULL
);
