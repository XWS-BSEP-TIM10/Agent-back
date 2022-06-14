INSERT INTO users (ID, PASSWORD, EMAIL, ACTIVATED, IS_USING_2FA, SECRET)
VALUES ('d12602fd-b7af-4da1-b1ca-bad8166d1fb2', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'jankovicapoteka@gmail.com', true, false, 'QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK');

INSERT INTO users (ID, PASSWORD, EMAIL, ACTIVATED, IS_USING_2FA, SECRET)
VALUES ('d12602fd-b7af-4da1-b1ca-bad8166d1fb3', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'peraperic4200@gmail.com', true, false, 'QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK');

INSERT INTO users (ID, PASSWORD, EMAIL, ACTIVATED, IS_USING_2FA, SECRET)
VALUES ('d12602fd-b7af-4da1-b1ca-bad8166d1fb4', '$2a$10$28MUwyYgna28OIxoUnE7VOpjby0JRJUU0WQV0UZdMX5XA46XAvBCK', 'admin@gmail.com', true, false, 'QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK');


INSERT INTO role (ID, NAME)
VALUES (1, 'ROLE_USER');

INSERT INTO role (ID, NAME)
VALUES (2, 'ROLE_COMPANY_OWNER');

INSERT INTO role (ID, NAME)
VALUES (3, 'ROLE_ADMIN');


INSERT INTO permission(id, name)
VALUES (1, 'ADD_API_TOKEN_PERMISSION');

INSERT INTO permission(id, name)
VALUES (2, 'CREATE_COMPANY_PERMISSION');

INSERT INTO permission(id, name)
VALUES (3, 'UPDATE_COMPANY_PERMISSION');

INSERT INTO permission(id, name)
VALUES (4, 'ACTIVATE_COMPANY_PERMISSION');

INSERT INTO permission(id, name)
VALUES (5, 'REMOVE_COMPANY_PERMISSION');

INSERT INTO permission(id, name)
VALUES (6, 'CREATE_INTERVIEW_PERMISSION');

INSERT INTO permission(id, name)
VALUES (7, 'CREATE_JOB_AD_PERMISSION');

INSERT INTO permission(id, name)
VALUES (8, 'DELETE_JOB_AD_PERMISSION');

INSERT INTO permission(id, name)
VALUES (9, 'SHARE_JOB_AD_PERMISSION');

INSERT INTO permission(id, name)
VALUES (10, 'CREATE_REVIEW_PERMISSION');

INSERT INTO permission(id, name)
VALUES (11, 'CREATE_SALARY_PERMISSION');

INSERT INTO permission(id, name)
VALUES (12, 'UPDATE_2FA_STATUS');


INSERT INTO roles_permissions(role_id, permission_id)
VALUES (2, 1);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (1, 2);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (2, 3);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (3, 4);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (3, 5);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (1, 6);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (2, 7);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (2, 8);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (2, 9);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (1, 10);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (1, 11);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (1, 12);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (2, 12);

INSERT INTO roles_permissions(role_id, permission_id)
VALUES (3, 12);



INSERT INTO user_role (ROLE_ID, USER_ID)
VALUES (1, 'd12602fd-b7af-4da1-b1ca-bad8166d1fb2');

INSERT INTO user_role (ROLE_ID, USER_ID)
VALUES (2, 'd12602fd-b7af-4da1-b1ca-bad8166d1fb3');

INSERT INTO user_role (ROLE_ID, USER_ID)
VALUES (3, 'd12602fd-b7af-4da1-b1ca-bad8166d1fb4');

INSERT INTO company (ID, ACTIVATED, ADDRESS, DESCRIPTION, EMAIL, NAME, PHONE_NUMBER, RATING, WEBSITE, OWNER_ID)
VALUES ('930163c8-995d-41d4-82fc-0c5a35e55d02', true, 'Ulica sajma 32', 'Good Company', 'vega@it.com', 'Vega IT', '+438943893', 0, 'www.vegait.rs', 'd12602fd-b7af-4da1-b1ca-bad8166d1fb3');

