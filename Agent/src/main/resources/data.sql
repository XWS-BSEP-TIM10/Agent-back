INSERT INTO users (ID, PASSWORD, EMAIL, ACTIVATED)
VALUES ('d12602fd-b7af-4da1-b1ca-bad8166d1fb2', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'username1', true);


INSERT INTO role (ID, NAME)
VALUES (1, 'ROLE_USER');

INSERT INTO role (ID, NAME)
VALUES (2, 'ROLE_COMPANY_OWNER');

INSERT INTO role (ID, NAME)
VALUES (3, 'ROLE_ADMIN');


INSERT INTO user_role (ROLE_ID, USER_ID)
VALUES (1, 'd12602fd-b7af-4da1-b1ca-bad8166d1fb2');

