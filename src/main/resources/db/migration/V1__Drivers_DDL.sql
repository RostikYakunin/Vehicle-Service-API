create table IF NOT EXISTS drivers
(
    driver_id         bigserial not null,
    name_of_driver    varchar(255),
    phone_number      varchar(255),
    qualification     varchar(255) check (qualification in ('BUS_DRIVER', 'TRAM_DRIVER')),
    surname_of_driver varchar(255),
    primary key (driver_id)
)
