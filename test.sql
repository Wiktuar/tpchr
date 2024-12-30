use tphr;

create table test (
    id      bigint primary key auto_increment,
    header  varchar(255) not null,
    text    varchar(255) not null
);

insert into test (header, text) value ('hello', 'goodbye');