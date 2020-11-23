CREATE TABLE IF NOT EXISTS tags
(
    tag_id    int         NOT NULL AUTO_INCREMENT,
    name      varchar(45) NOT NULL,
    operation varchar(45) NOT NULL,
    timestamp bigint      NOT NULL,
    PRIMARY KEY (tag_id),
    UNIQUE KEY Name_UNIQUE (name),
    UNIQUE KEY id_UNIQUE (tag_id)
);

CREATE TABLE IF NOT EXISTS certificates
(
    certificate_id   bigint      NOT NULL AUTO_INCREMENT,
    name             varchar(45) NOT NULL,
    description      tinytext,
    price            double      NOT NULL,
    create_date      timestamp   NOT NULL,
    last_update_date timestamp   NOT NULL,
    duration         int         NOT NULL,
    operation        varchar(45) NOT NULL,
    timestamp        bigint      NOT NULL,
    PRIMARY KEY (certificate_id),
    UNIQUE KEY certificate_id_UNIQUE (certificate_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id   bigint      NOT NULL AUTO_INCREMENT,
    name      varchar(45) NOT NULL,
    operation varchar(45) NOT NULL,
    timestamp bigint      NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY id_user_UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS orders
(
    order_id  bigint      NOT NULL AUTO_INCREMENT,
    date      timestamp   NOT NULL,
    price     double      NOT NULL,
    user_id   bigint      NOT NULL,
    operation varchar(45) NOT NULL,
    timestamp bigint      NOT NULL,
    PRIMARY KEY (order_id),
    UNIQUE KEY id_orders_UNIQUE (order_id)
);

CREATE TABLE IF NOT EXISTS orders_certificates
(
    certificate_id bigint NOT NULL,
    order_id       bigint NOT NULL,
    PRIMARY KEY (certificate_id, order_id),
    KEY fk_order_certificate_id_idx (order_id),
    CONSTRAINT fk_certificate_user_id FOREIGN KEY (certificate_id) REFERENCES certificates (certificate_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_order_certificate_id FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS tagged_certificates
(
    certificate_id bigint NOT NULL,
    tag_id         int    NOT NULL,
    PRIMARY KEY (certificate_id, tag_id),
    KEY fk_tag_id_idx (tag_id),
    PRIMARY KEY (certificate_id, tag_id),
    CONSTRAINT fk_certificate_id FOREIGN KEY (certificate_id) REFERENCES certificates (certificate_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_tag_id FOREIGN KEY (tag_id) REFERENCES tags (tag_id) ON DELETE CASCADE ON UPDATE CASCADE
);

