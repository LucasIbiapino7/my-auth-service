INSERT INTO tb_product (name, price) VALUES ('TV', 10000);
INSERT INTO tb_product (name, price) VALUES ('Computer', 5000);

INSERT INTO tb_user (email, password) VALUES ('test@gmail.com', '$2a$10$bk.oayrMG8ubRa85zqqEoOmqbkqAjg40aQtdjxQ6rVfDuxwaUqQrS');

INSERT INTO tb_role (authority) VALUES ('ROLE_USER');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role(user_id, role_id) values (3, 1)




