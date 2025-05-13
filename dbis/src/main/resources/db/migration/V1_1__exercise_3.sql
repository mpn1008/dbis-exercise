CREATE TABLE sheet3 (
    id SERIAL PRIMARY KEY,
    name TEXT
);


INSERT INTO sheet3 (name) VALUES
('name1'),
('name2'),
('name3');


CREATE TABLE test_table (
    id SERIAL PRIMARY KEY,
    x INT,
    y INT
);


INSERT INTO test_table (id, x, y) VALUES (1, 100, 200);
