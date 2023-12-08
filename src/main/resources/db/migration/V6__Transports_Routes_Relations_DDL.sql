create table IF NOT EXISTS transports_routes
(
    transport_id bigint not null,
    route_id     bigint not null,
    primary key (transport_id, route_id)
)