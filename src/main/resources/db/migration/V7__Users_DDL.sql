create table users
(
    role       smallint check (role between 0 and 2),
    created    timestamp(6) with time zone,
    id         bigserial    not null,
    updated    timestamp(6) with time zone,
    email      varchar(100) not null unique,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    password   varchar(255) not null,
    user_name  varchar(255) not null unique,
    primary key (id)
)