CREATE TABLE IF NOT EXISTS service(
    id BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(255),
    type CHARACTER VARYING(255),
    description CHARACTER VARYING(255),
    status CHARACTER VARYING(255)
);

COMMENT ON TABLE service IS 'Дополнительные услуги';

CREATE TABLE IF NOT EXISTS bill(
    id BIGSERIAL PRIMARY KEY,
    issued DATE,
    paid BOOLEAN,
    size NUMERIC(12,2),
    status CHARACTER VARYING(255),
    client_id BIGINT
);

COMMENT ON TABLE bill IS 'Счета для оплаты';

CREATE TABLE IF NOT EXISTS tariff_plan(
    id BIGSERIAL PRIMARY KEY,
    price NUMERIC(12,2),
    internet BIGINT,
    status CHARACTER VARYING(255),
    minutes_in BIGINT,
    minutes_out BIGINT
);

COMMENT ON TABLE tariff_plan IS 'Тарифные планы связи';

CREATE TABLE IF NOT EXISTS client (
    id BIGSERIAL PRIMARY KEY,
    passport_number VARCHAR(20) UNIQUE,
    mobile_number VARCHAR(15) UNIQUE,
    status CHARACTER VARYING(255),
    fio CHARACTER VARYING(255));

COMMENT ON TABLE client IS 'Клиенты сотового оператора';

CREATE TABLE IF NOT EXISTS employee
(
    id BIGSERIAL PRIMARY KEY,
    passport_number VARCHAR(20) UNIQUE,
    bonus NUMERIC(12,2),
    hired DATE,
    fio CHARACTER VARYING(255),
    position_id BIGINT
);

COMMENT ON TABLE employee IS 'Сотрудники компании';

CREATE TABLE IF NOT EXISTS position
(
    id BIGSERIAL PRIMARY KEY,
    name CHARACTER VARYING(255) UNIQUE,
    description CHARACTER VARYING(255),
    salary NUMERIC(12,2),
    category CHARACTER VARYING(255)
);

COMMENT ON TABLE position IS 'Должности и оклады сотрудников';

CREATE TABLE IF NOT EXISTS contract (
    id BIGSERIAL PRIMARY KEY,
    concluded DATE,
    expire DATE,
    prepayment NUMERIC(12,2),
    status CHARACTER VARYING(255),
    tariff_plan_id BIGINT,
    client_id BIGINT,
    employee_id BIGINT
);

COMMENT
ON TABLE contract IS 'Договоры с клиентами';

ALTER TABLE bill
    ADD CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
ALTER TABLE employee
    ADD CONSTRAINT fk_position FOREIGN KEY (position_id) REFERENCES position (id)
        ON DELETE SET NULL
        ON UPDATE SET NULL;
ALTER TABLE contract
    ADD CONSTRAINT fk_tariff_plan FOREIGN KEY (tariff_plan_id) REFERENCES tariff_plan (id)
        ON DELETE SET NULL
        ON UPDATE SET NULL;
ALTER TABLE contract
    ADD CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES client (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE;
ALTER TABLE contract
    ADD CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee (id)
        ON DELETE SET NULL
        ON UPDATE SET NULL;

CREATE TABLE IF NOT EXISTS support_tickets
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    client_id BIGINT REFERENCES client(id),
    employee_id BIGINT REFERENCES employee(id),
    timestamp TIMESTAMP,
    response CHARACTER VARYING(255)
);

COMMENT
ON TABLE support_tickets IS 'Обращения в техподдержку';

CREATE TABLE IF NOT EXISTS contract_service (
    contract_id BIGINT REFERENCES contract(id),
    service_id BIGINT REFERENCES service(id),
    PRIMARY KEY(contract_id,service_id)
);

COMMENT
ON TABLE contract_service IS 'Связь договоров с услугами';

ALTER TABLE client
    ADD CONSTRAINT chk_mobile_format
        CHECK (mobile_number ~* '^\+[1-9]\d{1,14}$');

COMMENT
ON COLUMN client.mobile_number IS
    'Формат: +<код страны><номер> (E.164). Примеры:
    +79211234567 (Россия)
    +14155550123 (США)
    +442079460018 (Великобритания)
    +33612345678 (Франция)';

ALTER TABLE client
    ADD CONSTRAINT chk_passport_format
        CHECK (passport_number ~ '^[A-Z0-9]{6,9}$');

COMMENT
ON COLUMN client.passport_number IS
    'Поддерживает:
    - Российские паспорта (9 цифр)
    - Загранпаспорта (буквы + цифры)
    - Иностранные документы (международный формат)';

ALTER TABLE client
    ADD CONSTRAINT chk_client_status CHECK (
        status IN (
                   'Активен',
                   'Заблокирован',
                   'Не активен'
            )
        );

-- Для таблицы employee
ALTER TABLE employee
    ADD CONSTRAINT chk_employee_passport CHECK (passport_number ~ '^[A-Z0-9]{6,9}$');

-- Для таблицы tariff_plan
ALTER TABLE tariff_plan
    ADD CONSTRAINT chk_internet_positive CHECK (internet >= 0);

ALTER TABLE tariff_plan
    ADD CONSTRAINT chk_minutes_in_positive CHECK (minutes_in >= 0);

ALTER TABLE tariff_plan
    ADD CONSTRAINT chk_minutes_out_positive CHECK (minutes_out >= 0);

ALTER TABLE tariff_plan
    ADD CONSTRAINT chk_status_valid CHECK (
        status IN (
                   'Активен',
                   'Не активен',
                   'Архивный'
            )
        );

ALTER TABLE bill
    ADD CONSTRAINT chk_bill_status CHECK (
        status IN (
                   'Оплачен',
                   'Выставлен',
                   'Просрочен',
                   'Возвращён',
                   'Отменён'
            )
        );

ALTER TABLE bill
    ADD CONSTRAINT chk_bill_status_paid CHECK (
        (
            paid = true
                AND status = 'Оплачен'
            )
            OR (
            paid = false
                AND status IN (
                               'Выставлен',
                               'Просрочен',
                               'Возвращён',
                               'Отменён'
                )
            )
        );

-- Для таблицы contract
ALTER TABLE contract
    ADD CONSTRAINT chk_dates_order CHECK (expire > concluded);

ALTER TABLE contract
    ADD CONSTRAINT chk_contract_status CHECK (
        status IN (
                   'Активен',
                   'Завершен',
                   'Расторгнут'
            )
        );

ALTER TABLE service
    ADD CONSTRAINT chk_service_status CHECK (
        status IN ('Активен', 'Не активен')
        );
