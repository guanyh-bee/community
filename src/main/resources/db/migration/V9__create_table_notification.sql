create table notification
(
    id int auto_increment,
    notifier int not null,
    receiver int not null,
    outer_id int not null,
    status int default 0 not null,
    gmt_create bigint not null,
    type int not null,
    constraint NOTIFICATION_PK
        primary key (id)
);
