CREATE DATABASE IF NOT EXISTS bsinfo;
GRANT ALL PRIVILEGES ON bsinfo.* TO 'bsinfo'@'%';
FLUSH PRIVILEGES;
CREATE TABLE IF NOT EXISTS bsinfo.user
(
    id            bigint       not null
        primary key,
    email_address varchar(255) null,
    first_name    varchar(255) null,
    generated_at  datetime(6)  null,
    last_name     varchar(255) null,
    password      varchar(255) null,
    updated_at    datetime(6)  null,
    user_type     int          null,
    username      varchar(255) null
);

INSERT INTO bsinfo.user(id, email_address, first_name, last_name, password, user_type, username)
    VALUE (1, 'admin@admin.com', 'Admin', 'Admin', '$2a$10$VMqa0u/SUSVx1YvAxVhB/uioTY0pVJq8QB3D6ywztMT6A5QjeikZy', 1,
           'admin')
