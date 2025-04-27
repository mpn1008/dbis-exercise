-- 5) Estate Agent
CREATE TABLE estate_agent (
                              agent_id    SERIAL PRIMARY KEY,
                              name        VARCHAR(200) NOT NULL,
                              address     TEXT,
                              login       VARCHAR(50) UNIQUE NOT NULL,
                              password    VARCHAR(100) NOT NULL
);

-- 1)  Estate
CREATE TABLE estate (
                        estate_id   SERIAL PRIMARY KEY,
                        agent_id    INT  REFERENCES estate_agent(agent_id),
                        city        VARCHAR(100) NOT NULL,
                        postal_code VARCHAR(20)  NOT NULL,
                        street      VARCHAR(200) NOT NULL,
                        street_no   VARCHAR(20)  NOT NULL,
                        area_sqm    NUMERIC(7,2)  NOT NULL
);

-- 2) Apartment
CREATE TABLE apartment (
                           estate_id   INT PRIMARY KEY
                               REFERENCES estate(estate_id) ON DELETE CASCADE,
                           floor       INT     NOT NULL,
                           rent        NUMERIC(10,2) NOT NULL,
                           rooms       INT     NOT NULL,
                           has_balcony BOOLEAN NOT NULL,
                           has_kitchen BOOLEAN NOT NULL
);

-- 3) House
CREATE TABLE house (
                       estate_id   INT PRIMARY KEY
                           REFERENCES estate(estate_id) ON DELETE CASCADE,
                       floors      INT     NOT NULL,
                       price       NUMERIC(12,2) NOT NULL,
                       has_garden  BOOLEAN NOT NULL
);

-- 4) Person
CREATE TABLE person (
                        person_id   SERIAL PRIMARY KEY,
                        first_name  VARCHAR(100) NOT NULL,
                        last_name   VARCHAR(100) NOT NULL,
                        address     TEXT
);


-- 6) Contract
CREATE TABLE contract (
                          contract_no   SERIAL PRIMARY KEY,
                          contract_date DATE    NOT NULL,
                          place         VARCHAR(100) NOT NULL,
                          person_id     INT     REFERENCES person(person_id) ,
                          estate_id     INT     REFERENCES estate(estate_id),
                          agent_id      INT     REFERENCES estate_agent(agent_id)
);

-- 7) Tenancy Contract
CREATE TABLE tenancy_contract (
                                  contract_no      INT PRIMARY KEY
                                      REFERENCES contract(contract_no) ON DELETE CASCADE,
                                  start_date       DATE    NOT NULL,
                                  duration_months  INT     NOT NULL,
                                  extra_costs      NUMERIC(10,2)
);

-- 8) Purchase Contract
CREATE TABLE purchase_contract (
                                   contract_no    INT PRIMARY KEY
                                       REFERENCES contract(contract_no) ON DELETE CASCADE,
                                   installments   INT     NOT NULL,
                                   interest_rate  NUMERIC(5,2) NOT NULL
);

--------insert sample data--------

-- 1) Insert an agent
INSERT INTO estate_agent (name, address, login, password)
VALUES ('Tom','Hamburg','liming','secret123');

-- 2) Insert two Estates and make subtable data separately
INSERT INTO estate (city, postal_code, street, street_no, area_sqm, agent_id)
VALUES ('Hamburg','100000','Hdestr','10A', 85.5, 1);
INSERT INTO house (estate_id, floors, price, has_garden)
VALUES (currval('estate_estate_id_seq'), 2, 3000000, TRUE);

INSERT INTO estate (city, postal_code, street, street_no, area_sqm, agent_id)
VALUES ('Hamburg','200000','Goodstr','88', 75.0, 1);
INSERT INTO apartment (estate_id, floor, rent, rooms, has_balcony, has_kitchen)
VALUES (currval('estate_estate_id_seq'), 5, 8000, 2, TRUE, FALSE);

-- 3) Insert a person
INSERT INTO person (first_name, last_name, address)
VALUES ('Harry','Poter','Hamburg');

-- 4) insert a lease contract
INSERT INTO contract (contract_date, place, person_id, estate_id, agent_id)
VALUES ('2025-04-22','Hamburg', 1, 2, 1);
INSERT INTO tenancy_contract (contract_no, start_date, duration_months, extra_costs)
VALUES (currval('contract_contract_no_seq'), '2025-05-01', 12, 500);

-- 5) insert a purchase contract
INSERT INTO contract (contract_date, place, person_id, estate_id, agent_id)
VALUES ('2025-04-22','Hamburg', 1, 1, 1);
INSERT INTO purchase_contract (contract_no, installments, interest_rate)
VALUES (currval('contract_contract_no_seq'), 36, 3.5);
