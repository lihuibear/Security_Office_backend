create table category
(
    id           bigint auto_increment comment '分类ID'
        primary key,
    categoryName varchar(256)                       not null comment '分类名称',
    description  text                               null comment '分类描述',
    coverUrl     varchar(1024)                      null comment '分类封面图URL',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    constraint uk_categoryName
        unique (categoryName)
)
    comment '内容分类' collate = utf8mb4_unicode_ci;

create table totalscore
(
    id         bigint auto_increment
        primary key,
    userId     bigint                              not null,
    allScore   int                                 not null,
    createTime timestamp default CURRENT_TIMESTAMP not null,
    updateTime timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint unique_user
        unique (userId)
);

create table user
(
    id            bigint auto_increment comment '用户ID'
        primary key,
    userName      varchar(256)                                        not null comment '用户名',
    userPassword  varchar(256)                                        not null comment '密码',
    userAvatar    varchar(1024)                                       null comment '用户头像',
    idNumber      varchar(18)                                         null comment '身份证号',
    studentNumber varchar(20)                                         null comment '学号/工号',
    dateOfBirth   date                                                null comment '出生日期',
    role          enum ('admin', 'student') default 'student'         not null comment '用户角色：admin/student',
    createTime    datetime                  default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime    datetime                  default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete      tinyint                   default 0                 not null comment '是否删除',
    constraint uk_idNumber
        unique (idNumber),
    constraint uk_studentNumber
        unique (studentNumber),
    constraint uk_userName
        unique (userName)
)
    comment '用户信息' collate = utf8mb4_unicode_ci;

create table content
(
    id           bigint auto_increment comment '内容ID'
        primary key,
    title        varchar(256)                       not null comment '内容标题',
    description  text                               null comment '内容描述',
    type         enum ('video', 'article')          not null comment '内容类型：video/article',
    url          varchar(1024)                      not null comment '内容URL',
    score        int                                not null comment '内容对应的分数',
    categoryId   bigint                             not null comment '分类ID',
    coverUrl     varchar(1024)                      null comment '内容封面图URL',
    createTime   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userId       bigint                             not null comment '上传内容的用户ID',
    learningTime int                                null comment '学习时长',
    constraint content_ibfk_category
        foreign key (categoryId) references category (id),
    constraint content_ibfk_user
        foreign key (userId) references user (id)
)
    comment '学习内容' collate = utf8mb4_unicode_ci;

create index categoryId_content
    on content (categoryId);

create index userId_content
    on content (userId);

create table score
(
    id               bigint auto_increment comment '分数记录ID'
        primary key,
    studentId        bigint                             not null comment '学生ID',
    contentId        bigint                             not null comment '内容ID',
    score            int                                not null comment '获得的分数',
    learningTime     int                                null comment '学习时长（分钟）',
    completionStatus int      default 0                 null comment '完成状态',
    createTime       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint score_ibfk_content
        foreign key (contentId) references content (id),
    constraint score_ibfk_student
        foreign key (studentId) references user (id)
)
    comment '学生分数' collate = utf8mb4_unicode_ci;

create index contentId_score
    on score (contentId);

create index studentId_score
    on score (studentId);

user数据:
1,李慧,cf7a6e3ef218a1d63c758ea0922ea9e7,http://imgs.lihuibear.cn/public/1902985669346693122/2025-03-21_U3icF0g80haOPaVv_thumbnail.jpg,130722200303047415,20221303057,2022-01-01,student,2025-03-28 09:49:38,2025-03-30 11:10:18,0
2,admin,cf7a6e3ef218a1d63c758ea0922ea9e7,"",130722200303047414,,2022-01-01,admin,2025-03-28 09:50:16,2025-03-28 21:09:24,0
3,ceshi,196000911bc85ac55f9b8a0ba65bb979,"",130722200303047413,1232312,1958-12-01,student,2025-03-29 16:31:24,2025-03-30 07:25:45,0
4,11,cf7a6e3ef218a1d63c758ea0922ea9e7,,130722200303047008,,,student,2025-03-29 16:31:24,2025-03-29 16:31:24,0
