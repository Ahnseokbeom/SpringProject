CREATE TABLE user(
	id bigint primary key auto_increment,
    created_at datetime(6),
    login_id varchar(255),
    nickname varchar(255),
    password varchar(255),
    received_like_cnt int,
    user_role varchar(255)
);
create table upload_image(
	id bigint primary key,
    original_filename varchar(255),
    saved_filename varchar(255)
);

create table board (
	id bigint primary key,
    created_at datetime(6),
    last_modified_at datetime(6),
    body varchar(255),
    category varchar(255),
    comment_cnt int,
    like_cnt int,
    title varchar(255),
    user_id bigint,
    upload_image_id bigint,
    foreign key (user_id) references user(id) on delete cascade,
    foreign key (upload_image_id) references upload_image(id) on delete cascade
);
create table heart(
	id bigint primary key,
    board_id bigint,
    user_id bigint,
    foreign key (user_id) references user(id) on delete cascade,
    foreign key (board_id) references board(id) on delete cascade
);
create table comment(
	id bigint primary key,
    created_at datetime(6),
    last_modified_at datetime(6),
    body varchar(255),
    board_id bigint,
    user_id bigint,
    foreign key (user_id) references user(id) on delete cascade,
    foreign key (board_id) references board(id) on delete cascade
);