create table IF NOT EXISTS transports
(
    dtype                     varchar(31) not null,
    id                        bigserial   not null,
    amount_of_passengers      integer,
    brand_of_transport        varchar(255),
    driver_qualification_enum varchar(255) check (driver_qualification_enum in ('BUS_DRIVER', 'TRAM_DRIVER')),
    doors_amount              integer,
    bus_type                  varchar(255),
    railcar_amount            integer,
    primary key (id)
)