create table If Not Exists drivers_routes
(
    driver_id bigint not null,
    route_id  bigint not null,
    primary key (driver_id, route_id)
)