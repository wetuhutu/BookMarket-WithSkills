create table book
(
    id             bigint auto_increment
        primary key,
    title          varchar(255)                       not null comment '书名',
    author         varchar(100)                       not null comment '作者',
    isbn           varchar(20)                        null comment 'ISBN',
    publisher      varchar(100)                       null comment '出版社',
    publish_date   varchar(20)                        null comment '出版时间',
    pages          int                                null comment '页数',
    category_id    varchar(50)                        not null comment '分类ID',
    `condition`    varchar(20)                        not null comment '新旧程度',
    price          decimal(10, 2)                     not null comment '售价',
    original_price decimal(10, 2)                     null comment '原价',
    stock          int      default 1                 null comment '库存',
    cover          varchar(500)                       not null comment '封面URL',
    images         json                               null comment '图片数组',
    description    text                               null comment '描述',
    seller_id      bigint                             not null comment '卖家ID',
    status         tinyint  default 1                 null comment '状态：1-在售，0-下架',
    created_at     datetime default CURRENT_TIMESTAMP null,
    updated_at     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    constraint isbn
        unique (isbn)
)
    comment '书籍表' charset = utf8mb4;

create index idx_category_id
    on book (category_id);

create index idx_created
    on book (created_at);

create index idx_price
    on book (price);

create index idx_seller
    on book (seller_id);

create fulltext index idx_title_author
    on book (title, author);

create table cart
(
    id         bigint auto_increment
        primary key,
    user_id    bigint                             not null comment '用户ID',
    book_id    bigint                             not null comment '书籍ID',
    quantity   int      default 1                 null comment '数量',
    price      decimal(10, 2)                     not null comment '添加时的价格',
    created_at datetime default CURRENT_TIMESTAMP null,
    constraint uk_user_book
        unique (user_id, book_id)
)
    comment '购物车表' charset = utf8mb4;

create index book_id
    on cart (book_id);

create index idx_user
    on cart (user_id);

create table category
(
    id          varchar(50)                        not null comment '分类ID（英文标识）'
        primary key,
    name        varchar(50)                        not null comment '分类名称',
    icon        varchar(10)                        not null comment '分类图标（emoji）',
    description varchar(200)                       null comment '分类描述',
    created_at  datetime default CURRENT_TIMESTAMP null,
    updated_at  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    comment '分类表' charset = utf8mb4;

create table favorite
(
    id         bigint auto_increment
        primary key,
    user_id    bigint                             not null comment '用户ID',
    book_id    bigint                             not null comment '书籍ID',
    created_at datetime default CURRENT_TIMESTAMP null,
    constraint uk_user_book
        unique (user_id, book_id)
)
    comment '收藏表' charset = utf8mb4;

create index idx_book
    on favorite (book_id);

create index idx_user
    on favorite (user_id);

create table `order`
(
    id              bigint auto_increment
        primary key,
    order_no        varchar(32)                           not null comment '订单号',
    buyer_id        bigint                                not null comment '买家ID',
    seller_id       bigint                                not null comment '卖家ID',
    book_id         bigint                                not null comment '书籍ID',
    quantity        int         default 1                 null comment '数量',
    price           decimal(10, 2)                        not null comment '单价',
    total_price     decimal(10, 2)                        not null comment '总价',
    status          varchar(20) default 'pending'         null comment '订单状态',
    payment_status  varchar(20) default 'unpaid'          null comment '支付状态',
    shipping_status varchar(20) default 'unshipped'       null comment '物流状态',
    created_at      datetime    default CURRENT_TIMESTAMP null,
    paid_at         datetime                              null comment '支付时间',
    shipped_at      datetime                              null comment '发货时间',
    received_at     datetime                              null comment '收货时间',
    book_title      varchar(255)                          null,
    cancelled_at    datetime                              null,
    constraint order_no
        unique (order_no)
)
    comment '订单表' charset = utf8mb4;

create index idx_buyer
    on `order` (buyer_id);

create index idx_seller
    on `order` (seller_id);

create index idx_status
    on `order` (status);

create table review
(
    id          bigint auto_increment
        primary key,
    book_id     bigint                             not null comment '书籍ID',
    user_id     bigint                             not null comment '评价用户ID',
    user_name   varchar(50)                        not null comment '评价用户名称',
    user_avatar varchar(500)                       null comment '评价用户头像',
    rating      tinyint                            not null comment '评分（1-5）',
    content     text                               null comment '评价内容',
    created_at  datetime default CURRENT_TIMESTAMP null
)
    comment '评价表' charset = utf8mb4;

create index idx_book
    on review (book_id);

create index idx_rating
    on review (rating);

create index idx_user
    on review (user_id);

create table user
(
    id                  bigint auto_increment
        primary key,
    username            varchar(50)                             not null comment '用户名',
    phone               varchar(20)                             not null comment '手机号',
    email               varchar(100)                            null comment '邮箱',
    password            varchar(255)                            not null comment '密码（加密）',
    avatar              varchar(500)                            null comment '头像URL',
    level               varchar(20)   default '普通会员'        null comment '会员等级',
    points              int           default 0                 null comment '积分',
    is_seller           tinyint(1)    default 0                 null comment '是否卖家',
    seller_level        varchar(20)   default '普通卖家'        null comment '卖家等级',
    seller_is_verified  tinyint(1)    default 0                 null comment '是否认证',
    created_at          datetime      default CURRENT_TIMESTAMP null,
    updated_at          datetime      default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    seller_description  varchar(255)                            null comment '卖家描述',
    seller_positiveRate decimal(5, 1) default 0.0               null,
    seller_rating       decimal(3, 1) default 0.0               null comment '卖家评分',
    seller_sales_count  int           default 0                 null comment '销售数量',
    constraint phone
        unique (phone),
    constraint username
        unique (username)
)
    comment '用户表' charset = utf8mb4;

create index idx_phone
    on user (phone);

create index idx_username
    on user (username);

