-- 1)  Estate
CREATE TABLE estate (
                        estate_id   SERIAL PRIMARY KEY,
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

-- 5) Estate Agent
CREATE TABLE estate_agent (
                              agent_id    SERIAL PRIMARY KEY,
                              name        VARCHAR(200) NOT NULL,
                              address     TEXT,
                              login       VARCHAR(50) UNIQUE NOT NULL,
                              password    VARCHAR(100) NOT NULL
);

-- 6) Contract
CREATE TABLE contract (
                          contract_no   SERIAL PRIMARY KEY,
                          contract_date DATE    NOT NULL,
                          place         VARCHAR(100) NOT NULL,
                          person_id     INT     REFERENCES person(person_id),
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
