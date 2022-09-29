insert into person
values ('0966480c-a94f-42dd-8a95-40ca402b7fd2',
        'admin',
        'bio',
        '$2a$10$lczEU1oglFRJfvV9mWnQreyCucOqdlqFtAu7.N76EHclBWHu0Wuga', --admin
        'ADMIN');

insert into person
values ('8df618ef-887f-410f-8608-3323c9ab71ae',
        'user1',
        'bio',
        '$2a$10$m6rWHhvJdnRl7SCSqRKF7eZohUSME/1j1pI38tC3okWdYTWzFr0fi', --user1
        'USER'),
       ('54dbaf5b-101c-4958-8857-877cdd312b59',
        'user2',
        'bio',
        '$2a$10$S0MmA7PEq9ZGNf34.Qb6Yuvw5D.MuvvU9eeLoEd/.psxksXruWXHy', --user2
        'USER'),
       ('1ced10d0-a0b2-42c7-b9c6-d8e3b4946bcb',
        'user3',
        'bio',
        '$2a$10$WiTU6YGY.ncV.Z66qnjVuuhaRoANmmMkqn8RHG7U6XoxMgY9z3RYS', --user3
        'USER');

insert into chat
values ('4ae20120-419b-4679-8d13-68b9e917572c');

insert into personalchat
values ('4ae20120-419b-4679-8d13-68b9e917572c',
        '54dbaf5b-101c-4958-8857-877cdd312b59', --user2
        '8df618ef-887f-410f-8608-3323c9ab71ae' --user1
       );

insert into message
values ('cdf6d98f-e42d-41ce-be0c-6431af9f7dda',
        '4ae20120-419b-4679-8d13-68b9e917572c',
        '54dbaf5b-101c-4958-8857-877cdd312b59',
        '2022-09-14 12:23:26.295000',
        null),
       ('c2b57b8e-146a-4f7b-b207-a17b8d24744b',
        '4ae20120-419b-4679-8d13-68b9e917572c',
        '8df618ef-887f-410f-8608-3323c9ab71ae',
        '2022-09-14 13:29:12.760000',
        null),
       ('d03c2a38-210b-4792-9bb4-cd50de4d981c',
        '4ae20120-419b-4679-8d13-68b9e917572c',
        '8df618ef-887f-410f-8608-3323c9ab71ae',
        '2022-09-14 13:58:07.858000',
        null);

insert into messagecontent
values ('f977ce39-b68e-436e-943b-fdf645eec9ec',
        'd03c2a38-210b-4792-9bb4-cd50de4d981c',
        'text1'),
       ('f977ce39-b68e-436e-943b-fdf645eec9ed',
        'c2b57b8e-146a-4f7b-b207-a17b8d24744b',
        'text2'),
       ('f977ce39-b68e-436e-943b-fdf645eec9eb',
        'cdf6d98f-e42d-41ce-be0c-6431af9f7dda',
        'text3');

insert into reaction
values ('cdf6d98f-e42d-41ce-be0c-6431af9f7dda',
        '54dbaf5b-101c-4958-8857-877cdd312b59', --user2
        'CLOWN'),
       ('c2b57b8e-146a-4f7b-b207-a17b8d24744b',
        '8df618ef-887f-410f-8608-3323c9ab71ae', --user1
        'LIKE');