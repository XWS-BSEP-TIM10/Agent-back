INSERT INTO users (ID, PASSWORD, EMAIL, ACTIVATED)
VALUES ('d12602fd-b7af-4da1-b1ca-bad8166d1fb2', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'jankovicapoteka@gmail.com', true);


INSERT INTO users (ID, PASSWORD, EMAIL, ACTIVATED)
VALUES ('d12602fd-b7af-4da1-b1ca-bad8166d1fb3', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'peraperic4200@gmail.com', true);

INSERT INTO users (ID, PASSWORD, EMAIL, ACTIVATED)
VALUES ('d12602fd-b7af-4da1-b1ca-bad8166d1fb4', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'admin@gmail.com', true);


INSERT INTO role (ID, NAME)
VALUES (1, 'ROLE_USER');

INSERT INTO role (ID, NAME)
VALUES (2, 'ROLE_COMPANY_OWNER');

INSERT INTO role (ID, NAME)
VALUES (3, 'ROLE_ADMIN');


INSERT INTO user_role (ROLE_ID, USER_ID)
VALUES (1, 'd12602fd-b7af-4da1-b1ca-bad8166d1fb2');

INSERT INTO user_role (ROLE_ID, USER_ID)
VALUES (2, 'd12602fd-b7af-4da1-b1ca-bad8166d1fb3');


INSERT INTO user_role (ROLE_ID, USER_ID)
VALUES (3, 'd12602fd-b7af-4da1-b1ca-bad8166d1fb4');

INSERT INTO company (ID, ACTIVATED, ADDRESS, DESCRIPTION, EMAIL, NAME, PHONE_NUMBER, RATING, WEBSITE, OWNER_ID)
VALUES ('930163c8-995d-41d4-82fc-0c5a35e55d02', true, 'Ulica sajma 32', 'Good Company', 'vega@it.com', 'Vega IT', '+438943893', 0, 'www.vegait.rs', 'd12602fd-b7af-4da1-b1ca-bad8166d1fb3');

