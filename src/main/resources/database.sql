drop table if exists t_article;
CREATE TABLE `t_article`
(
    `id`              bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `article_id`      varchar(64)         NOT NULL DEFAULT '' COMMENT '文章ID，UUID',
    `title`           varchar(1024)       NOT NULL DEFAULT '' COMMENT '文章标题',
    `thumbnail`       varchar(1024)       NOT NULL DEFAULT '' COMMENT '文章缩略图',
    `article_content` text                NOT NULL COMMENT '文章内容',
    `author`          varchar(50)         NOT NULL DEFAULT '0' COMMENT '作者',
    `visits`          int(10)             NOT NULL DEFAULT 0 COMMENT '阅览数量',
    `likes`           int(10)             NOT NULL DEFAULT 0 COMMENT '点赞数量',
    `create_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE `uniq_article_id` (`article_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  CHARSET = utf8mb4 COMMENT '文章表';

INSERT INTO t_article (id, article_id, title, thumbnail, article_content, author, visits, likes, create_time,
                       update_time)
VALUES (1, 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxxx', '卫健委提醒返程高峰做好个人防护',
        'https://res1.eqh5.com/o_1e4cqkheepj9cra1hqo16kus0b2h.jpg?imageMogr2/auto-orient/thumbnail/480x930%3E/format/webp',
        '5日，国家卫生健康委新闻发言人、宣传司副司长米锋表示，5月4日，全国连续两天无新增本土确诊和疑似病例，新增治愈出院病例87例，占上一日现有确诊病例的近五分之一。今天是五一假期最后一天，各地迎来返程高峰，应始终做好个人防护，关注身体状况，保持适当距离，减少人员聚集。',
        'shang', 6, 0, '2020-05-05 13:25:03', '2020-05-05 16:33:15');
INSERT INTO t_article (id, article_id, title, thumbnail, article_content, author, visits, likes, create_time,
                       update_time)
VALUES (2, 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx1', '经济活动重启太快，美国每日将新增20万病例？',
        'https://res1.eqh5.com/o_1e4cro1fj1hu418191lm1f0ng7m3g.png?imageMogr2/auto-orient/thumbnail/480x930%3E/format/webp',
        '进入5月以来，美国各州逐步放宽新冠疫情期间的限制措施，越来越多的商家恢复营业。但多个模型预测称，过早放松警惕将导致确诊病例和死亡人数激增。', 'shang', 7, 5, '2020-05-05 13:25:03',
        '2020-05-05 16:31:49');
INSERT INTO t_article (id, article_id, title, thumbnail, article_content, author, visits, likes, create_time,
                       update_time)
VALUES (3, 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx2', '黑龙江近3千公里边境防疫：2850余名护边员投入一线巡防',
        'https://res1.eqh5.com/o_1e4cqkheepj9cra1hqo16kus0b2h.jpg?imageMogr2/auto-orient/thumbnail/480x930%3E/format/webp', '面对境外新冠肺炎疫情输入，黑龙江省在近3000公里的边境线上构建了多层次“防火墙”。

“连日来，黑龙江出入境边防检查总站深化边境封控措施，强化通道设卡、驻点执勤、边境巡逻，查处违边行政案件36起，处罚45人；清查辖区各类船只2400余艘，纳入管控视线，有力维护了边境生产作业秩序；组织2850余名护边员投入一线巡防，增强了边境封控力量，确保了边境地区的安全稳定。”

5月5日，国家移民管理局官方微信公众号刊文披露了黑龙江出入境边防检查总站（以下简称“黑龙江边检总站”）在这场“外防输入攻坚战”中的具体工作。

位于东北亚区域腹地的黑龙江省北、东部与俄罗斯隔江相望，全省边境线长2981.26千米，是亚洲与太平洋地区陆路通往俄罗斯和欧洲大陆的重要通道，也是中国沿边开放的重要窗口。黑龙江省边境线北至“神州北极”北极村，东至“华夏东极”黑瞎子岛，黑龙江、乌苏里江、松花江在边境交汇，水陆交错。

截至5月4日24时，黑龙江省累计报告境外输入确诊病例386例，现有境外输入确诊病例159例；追踪到境外密切接触者2104人，已解除医学观察2094人，尚有10人正在接受医学观察；现有境外输入无症状感染者4例。

微信公众号“国家移民管理局”文章称，防范境外疫情输入战役打响后，黑龙江边检总站抵边部署运行边境警务室、执勤点等前出点位，强化边境巡逻、设卡堵截勤务，把警力全部拉到一线去，坚决防范打击疫情期间偷越国界、跨境走私等涉边违法犯罪活动。

各边境管理支队采取抵边部署运行警务室、执勤点、边境检查站卡点等前出点位100余个，严密组织勤务，突出可疑人员、重点船只、重点通道和重点水域，强化通道设卡、驻点执勤、边境巡逻、所队联勤等措施，严格执行查缉流程，切实增强防控能力，确保边境辖区安全。其中，作为防范境外疫情输入的重点方向，牡丹江边境管理支队通过警力前移、靠前调度，搭建起指挥顺畅、运行高效的指挥体系。在绥芬河市，执勤警力达到平时的2倍。

文章称，为做好边境疫情防输入工作，黑龙江边检总站借助新闻发布会、建立警民微信群、“百万警进千万家”等方式宣传边境管理政策法规，引导边民群众和外来人员守法护边。一个月来，黑龙江边检总站通过新闻发布会发布信息3场次，网络宣传100余场次，出动警力3万余人次走访宣传，凝聚了线上线下防范境外疫情输入的强大合力。

此外，带领和发动辖区治安联防力量，协助民警开展边境管理工作，提升涉边问题预警防范能力，是黑龙江边检总站在防范境外疫情输入工作中的又一举措。

文章提到，为进一步整合军警地涉边力量，黑龙江8个边境地市均发布了有奖征集边境地区非法入境线索的实施办法，发动群众提供信息。鸡西、黑河、佳木斯等边境管理支队结合边境特点，推动边境派出所与驻地解放军、渔政等涉边部门成立边境巡防小组，对辖区重点江段进行反复清理检查，集中清理劝返外来人员，严厉打击违法违边行为。牡丹江、大兴安岭边境管理支队打造“民警+护边员”巡防模式，积极开展警民联合护边工作。

此前，4月20日，受黑龙江省委书记张庆伟委托，省委副书记、省长、省应对新型冠状病毒感染肺炎疫情工作领导小组指挥部指挥长王文涛在绥芬河主持召开视频例会，专题部署边境管控工作。

王文涛当时强调，要切实承担起习近平总书记赋予黑龙江省维护国家“五大安全”特别是国防安全、边境安全的重要职责，立足点线面各环节有效结合、各方面协调联动，党政军警民共同携手，建立起与疫情防控常态化相适应的边境管控机制，合力做好防范境外疫情输入和管边控边工作。

而黑龙江省应对新冠肺炎疫情工作领导小组指挥部4月13日正式向各市（地）指挥部下发的通知要求，充分发动人民群众检举揭发相关问题线索，此次举报范围是通过陆路、水路非法偷越国（边）境进入黑龙江省的人员。中，对非法越境犯罪线索一经查实，将给予举报人人民币3000元奖励；如边民自行抓获并交由有关部门处置的，一次性奖励人民币5000元。',
        'shang', 6, 25, '2020-05-05 13:25:03', '2020-05-05 16:33:21');



CREATE TABLE `t_comment`
(
    `id`              bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `comment_id`      varchar(64)         NOT NULL DEFAULT '' COMMENT '评论ID，UUID',
    `article_id`      varchar(64)         NOT NULL DEFAULT '' COMMENT '文章ID，UUID',
    `comment_content` varchar(1024)       NOT NULL default '' COMMENT '文章内容',
    `author`          varchar(50)         NOT NULL DEFAULT '' COMMENT '作者',
    `author_avatar`   varchar(256)        NOT NULL DEFAULT '' COMMENT '作者头像url',
    `create_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE `uniq_comment_id` (`comment_id`),
    KEY `idx_article_id` (`article_id`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  CHARSET = utf8mb4 COMMENT '文章评论表';

INSERT INTO t_comment (id, comment_id, article_id, comment_content, author, author_avatar, create_time, update_time)
VALUES (1, '90f450c5-ea8f-4807-8779-66dec870ce16', 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx2', 'ddddddddddddddddddd', '商商-77',
        '', '2020-05-05 14:58:49', '2020-05-05 14:58:49');
INSERT INTO t_comment (id, comment_id, article_id, comment_content, author, author_avatar, create_time, update_time)
VALUES (2, '5b930b8a-245d-47d6-b811-356679547022', 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx2', 'ssssssssssssssssssss',
        '商商-77',
        'https://wx.qlogo.cn/mmopen/vi_32/6LkInzF55Pemkiam4O5ywCc56ib7RDwbXXPqyYAp5ZRiad1ULtvSLK3iao0sOpBKeEL8eQQ0k669aGyIwDibiaEmPT0g/132',
        '2020-05-05 14:59:25', '2020-05-05 14:59:25');
INSERT INTO t_comment (id, comment_id, article_id, comment_content, author, author_avatar, create_time, update_time)
VALUES (3, 'b250edae-b585-40f2-b260-d2d6e1d98315', 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx2', 'xxxxxxxxxxxxxxxxxxxxx',
        '商商-77',
        'https://wx.qlogo.cn/mmopen/vi_32/6LkInzF55Pemkiam4O5ywCc56ib7RDwbXXPqyYAp5ZRiad1ULtvSLK3iao0sOpBKeEL8eQQ0k669aGyIwDibiaEmPT0g/132',
        '2020-05-05 15:02:00', '2020-05-05 15:02:00');
INSERT INTO t_comment (id, comment_id, article_id, comment_content, author, author_avatar, create_time, update_time)
VALUES (4, '094e6802-add1-418c-9a60-3cc9a933a1a9', 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx2', 'xxxxxxxxxxxxxxxxxxxx',
        '商商-77',
        'https://wx.qlogo.cn/mmopen/vi_32/6LkInzF55Pemkiam4O5ywCc56ib7RDwbXXPqyYAp5ZRiad1ULtvSLK3iao0sOpBKeEL8eQQ0k669aGyIwDibiaEmPT0g/132',
        '2020-05-05 15:02:33', '2020-05-05 15:02:33');
INSERT INTO t_comment (id, comment_id, article_id, comment_content, author, author_avatar, create_time, update_time)
VALUES (5, '1703e4e9-67c6-48e0-943e-00eb7ebc4e22', 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx1', 'cccccccccccccccccccccccc',
        '商商-77',
        'https://wx.qlogo.cn/mmopen/vi_32/6LkInzF55Pemkiam4O5ywCc56ib7RDwbXXPqyYAp5ZRiad1ULtvSLK3iao0sOpBKeEL8eQQ0k669aGyIwDibiaEmPT0g/132',
        '2020-05-05 15:12:40', '2020-05-05 15:12:40');
INSERT INTO t_comment (id, comment_id, article_id, comment_content, author, author_avatar, create_time, update_time)
VALUES (6, 'f2b41cf9-c58f-4621-a7c8-e8d866a5fa22', 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx1', 'ddddddddddddddddddd', '商商-77',
        'https://wx.qlogo.cn/mmopen/vi_32/6LkInzF55Pemkiam4O5ywCc56ib7RDwbXXPqyYAp5ZRiad1ULtvSLK3iao0sOpBKeEL8eQQ0k669aGyIwDibiaEmPT0g/132',
        '2020-05-05 16:27:14', '2020-05-05 16:27:14');
INSERT INTO t_comment (id, comment_id, article_id, comment_content, author, author_avatar, create_time, update_time)
VALUES (7, '0ab8b9df-56e3-4fbf-be9d-c3aacb6055e7', 'xxxx-xxxxxxxxx-xxxxxxxxxx-xxxxxxx2', 'Thisisatest', '商商-77',
        'https://wx.qlogo.cn/mmopen/vi_32/6LkInzF55Pemkiam4O5ywCc56ib7RDwbXXPqyYAp5ZRiad1ULtvSLK3iao0sOpBKeEL8eQQ0k669aGyIwDibiaEmPT0g/132',
        '2020-05-05 16:33:38', '2020-05-05 16:33:38');


CREATE TABLE `t_user`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    varchar(64)         NOT NULL DEFAULT '' COMMENT '用户名',
    `password`    varchar(64)         NOT NULL DEFAULT '' COMMENT '密码密文',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE `uniq_username` (`username`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  CHARSET = utf8mb4 COMMENT '用户表';
  insert into t_user (username, password) values ('shangyidong', 'xxxx');

CREATE TABLE `t_role`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_name`   varchar(64)         NOT NULL DEFAULT '' COMMENT '角色名',
    `role_desc`   varchar(64)         NOT NULL DEFAULT '' COMMENT '角色描述',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE `uniq_role_name` (`role_name`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  CHARSET = utf8mb4 COMMENT '角色表';
    insert into t_role (role_name, role_desc) values ('ROLE_ADMIN', '管理员角色');


CREATE TABLE `t_user_role_config`
(
    `id`          bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    varchar(64)         NOT NULL DEFAULT '' COMMENT '用户名',
    `role_name`   varchar(64)         NOT NULL DEFAULT '' COMMENT '角色名',
    `create_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE `uniq_username_role_name` (`username`, `role_name`),
    KEY `idx_role_name` (`role_name`),
    KEY `idx_create_time` (`create_time`),
    KEY `idx_update_time` (`update_time`)
) ENGINE = InnoDB
  CHARSET = utf8mb4 COMMENT '用户角色配置表';
      insert into t_user_role_config (username, role_name) values ('shangyidong', 'ROLE_ADMIN');
