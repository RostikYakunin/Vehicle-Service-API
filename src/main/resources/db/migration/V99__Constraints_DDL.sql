alter table if exists drivers_routes
    add constraint FKr5c4invag7rt8o19uv1w7c2mj foreign key (route_id) references routes;

alter table if exists drivers_routes
    add constraint FKa4xemhl5gshwwyn8ls5dujba7 foreign key (driver_id) references drivers;

alter table if exists transports_drivers
    add constraint FKr1swl53axdgmvi9rsuqjray4e foreign key (driver_id) references drivers;

alter table if exists transports_drivers
    add constraint FK4jj68l3nx7o2nc30qg56ik3x7 foreign key (transport_id) references transports;

alter table if exists transports_routes
    add constraint FK9hsvbnxgrv6v6v2tubh5pyjrk foreign key (route_id) references routes;

alter table if exists transports_routes
    add constraint FK86snbrhtoilhoysy849vy090l foreign key (transport_id) references transports;