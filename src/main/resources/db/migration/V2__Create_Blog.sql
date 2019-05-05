create table blog
(
    id                 bigint primary key auto_increment,
    user_id           bigint,
    title varchar(100),
    description varchar(100),
    content TEXT,
    created_at         datetime,
    updated_at         datetime
)