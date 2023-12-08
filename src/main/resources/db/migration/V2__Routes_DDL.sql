create table IF NOT EXISTS routes
(
    id        bigserial not null,
    end_way   varchar(255),
    start_way varchar(255),
    primary key (id)
)