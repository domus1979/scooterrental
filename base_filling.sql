USE scooter;

INSERT users(id, login, password, first_name, last_name, email, age)
VALUES
    (1, 'admin',   '$2a$12$KxUrnotEfXmtJzJ5UDLu3edo88jjgiacgNKQ.VEl//Nq9ZBgfXWT6', 'Kolas',   'Yakub',   'kolas@tut.by',   75), /* admin */
    (2, 'Kandrat', '$2a$12$PgzMqybJsm/8sVitccTYaeM9.nbDlvuPUFYJlGCFSfEjUWaTh0oPW', 'Krapiva', 'Kandrat', 'krapiva@tut.by', 64), /* Kandrat */
    (3, 'Maksim',  '$2a$12$IOuv.4sC7og5bx3XXTbxgessvK8cF8.NAnokHp6CwarpzUkqifZDC', 'Tank',    'Maksim',  'tank@tut.by',    81), /* Maksim */
    (4, 'Yanka',   '$2a$12$vUzIOXemRhGBFBxl1s0nl.a3hV81BmMwZd7W5e15IEiuuPPgwi8Xa', 'Kupala',  'Yanka',   'kupala@tut.by',  58), /* Kupala */
    (5, 'Mavr',    '$2a$12$5AZ4nSt0doGfxrIiZ1vUuemkE4I9OIWy4E65MYtBDzYqSdGPnh6nm', 'Mavr',    'Yanka',   'mavr@tut.by',    62); /* Yanka */

INSERT roles(id, name)
VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

INSERT roles_list(user_id, role_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (3, 2),
    (4, 2),
    (5, 2);

INSERT rental_points(name, parent_id)
VALUES
    ('Belarus', null),     /* 1 */
    ('Brest', 1),          /* 2 */
    ('Lenina', 2),         /* 3 */
    ('35', 3),             /* 4 */
    ('Moskovskaja', 2),    /* 5 */
    ('266', 5),            /* 6 */
    ('Kobrin', 1),         /* 7 */
    ('Suvorova', 7),       /* 8 */
    ('26', 8);             /* 9 */

INSERT scooter_models(name, max_speed, work_time, max_weight)
VALUES
    ('Mi electric scooter 1S', 60, 4, 120),
    ('Hiper triumph M88s', 50, 3, 100),
    ('Senator Ram SN 10Ram', 50, 3, 110);

INSERT scooters(name, scooter_model_id, manufacture_year, scooter_status, rental_point_id)
VALUES
    ('sn 0001', 1, 2020, 0, 4),  /* 1 */
    ('sn 0002', 1, 2021, 0, 4),  /* 2 */
    ('sn 0004', 1, 2021, 0, 6),  /* 3 */
    ('sn 0005', 1, 2022, 0, 9),  /* 4 */
    ('sn 0008', 2, 2020, 0, 4),  /* 5 */
    ('sn 0011', 2, 2021, 0, 4),  /* 6 */
    ('sn 0014', 2, 2021, 0, 6),  /* 7 */
    ('sn 0015', 2, 2021, 0, 9),  /* 8 */
    ('sn 0021', 3, 2020, 0, 4),  /* 9 */
    ('sn 0022', 3, 2021, 0, 4),  /* 10 */
    ('sn 0023', 3, 2022, 0, 6),  /* 11 */
    ('sn 0031', 3, 2022, 0, 9);  /* 12 */

INSERT price_types(name, duration_in_minutes, season_ticket)
VALUES
    ('30 minutes', 30, 0),
    ('1 hour', 60, 0),
    ('2 hour', 120, 0),
    ('3 hour', 180, 0),
    ('10 day', 864000, 1),
    ('20 day', 1728000, 1),
    ('30 day', 2592000, 1);

INSERT price_list(scooter_model_id, price_type_id, price)
VALUES
    (1, 1, 2.00),   /* 1 */
    (1, 2, 3.00),   /* 2 */
    (1, 3, 5.00),   /* 3 */
    (1, 4, 8.00),   /* 4 */
    (null, 5, 70.00),  /* 5 */
    (null, 6, 130.00), /* 6 */
    (null, 7, 190.00), /* 7 */
    (2, 1, 2.50),   /* 8 */
    (2, 2, 4.00),   /* 9 */
    (2, 3, 5.50),   /* 10 */
    (2, 4, 7.00),   /* 11 */
    (3, 1, 1.00),   /* 12 */
    (3, 2, 2.00),   /* 13 */
    (3, 3, 3.50),   /* 14 */
    (3, 4, 5.00);   /* 15 */

INSERT orders(start_rental_point_id, scooter_id, user_id, price_list_id, finish_rental_point_id, order_status,
 begin_time, actual_duration, end_time, price, discount, cost)
VALUES
    (4, 1,  2, 1,  4, 1, '2022-06-03 08:00:00', 30, '2022-06-03 08:30:00', 2.00, 0, 2.00),
    (4, 2,  3, 1,  4, 1, '2022-06-03 08:00:00', 25, '2022-06-03 08:25:00', 2.00, 0, 2.00),
    (4, 2,  3, 1,  4, 1, '2022-06-03 09:00:00', 20, '2022-06-03 09:20:00', 2.00, 0, 2.00),
    (4, 1,  2, 1,  4, 1, '2022-06-03 10:00:00', 15, '2022-06-03 10:15:00', 2.00, 0, 2.00),
    (4, 5,  3, 9,  4, 1, '2022-06-03 11:00:00', 20, '2022-06-03 11:20:00', 4.00, 0, 4.00),
    (6, 3,  4, 1,  6, 1, '2022-06-03 08:10:00', 35, '2022-06-03 08:45:00', 2.00, 0, 2.33),
    (6, 7,  4, 9,  6, 1, '2022-06-03 09:05:00', 40, '2022-06-03 09:45:00', 4.00, 0, 5.33),
    (6, 9,  4, 9,  6, 1, '2022-06-03 10:10:00', 25, '2022-06-03 10:35:00', 4.00, 0, 4.00),
    (6, 10, 5, 14, 6, 1, '2022-06-03 08:00:00', 30, '2022-06-03 08:30:00', 3.50, 0, 3.50),
    (9, 4,  1, 1,  9, 1, '2022-06-03 08:00:00', 30, '2022-06-03 08:30:00', 2.00, 0, 2.00),
    (9, 8,  2, 9,  9, 1, '2022-06-03 08:00:00', 20, '2022-06-03 08:20:00', 4.00, 0, 4.00),
    (9, 12, 3, 14, 9, 1, '2022-06-03 08:00:00', 10, '2022-06-03 08:10:00', 3.50, 0, 3.50),
    (9, 4,  1, 1,  9, 1, '2022-06-03 10:00:00', 15, '2022-06-03 10:15:00', 2.00, 0, 2.00),
    (9, 8,  2, 9,  9, 1, '2022-06-03 11:00:00', 40, '2022-06-03 11:40:00', 4.00, 0, 5.33);
