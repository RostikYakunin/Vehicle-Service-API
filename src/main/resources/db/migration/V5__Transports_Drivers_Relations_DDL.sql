create table IF NOT EXISTS transports_drivers
(
    transport_id bigint not null,
    driver_id    bigint not null,
    primary key (transport_id, driver_id)
)