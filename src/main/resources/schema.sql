create table if not exists users (
    id identity,
    username varchar(50) not null,
    password varchar(50) not null,
    fullname varchar(50) not null,
    street varchar(50) not null,
    city varchar(50) not null,
    state varchar(2) not null,
    zip varchar(10) not null,
    phone varchar(16) not null
);

create table if not exists taco_order (
    id identity,
    delivery_name varchar(50) not null,
    delivery_street varchar(50) not null,
    delivery_city varchar(50) not null,
    delivery_state varchar(2) not null,
    delivery_zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null
);

create table if not exists taco (
    id identity,
    name varchar(50) not null,
    taco_order bigint not null,
    taco_order_key bigint not null,
    created_at timestamp not null
);

create table if not exists ingredient_ref (
    ingredient varchar(4) not null,
    taco bigint not null,
    taco_key bigint not null
);

create table if not exists ingredient(
    id varchar(4) not null unique,
    name varchar(25) not null,
    type varchar(10) not null
);

alter table taco add foreign key (taco_order) references taco_order(id);
alter table ingredient_ref add foreign key (ingredient) references ingredient(id);