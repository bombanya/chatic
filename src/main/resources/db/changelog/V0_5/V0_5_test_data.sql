--changeset user:13
insert into person values (
                              '0966480c-a94f-42dd-8a95-40ca402b7fd2',
                              'admin1',
                              'bio',
                              '$2a$10$jxJFIFe4dGPwUUXW6Xt/FeF8q1XzS8DYCuhRwteWrf67IqIKczAB.',
                              'ADMIN'
                          );
--changeset user:14
insert into person values (
                              '8df618ef-887f-410f-8608-3323c9ab71ae',
                              'user33',
                              'bio',
                              '$2a$10$CdyEHiaVonOLIysPKSAYO.raOKdsfcx5yratv58R9Tc.k5raT/cwy',
                              'USER'
                          ),
                          (
                              '54dbaf5b-101c-4958-8857-877cdd312b59',
                              'user32',
                              'bio',
                              '$2a$10$egKZO/oN6nve.76vbXvfDOeAbx1tY41.gTbQZXjj8nhss85U4YMdu',
                              'USER'
                          ),
                          (
                              '1ced10d0-a0b2-42c7-b9c6-d8e3b4946bcb',
                              'user31',
                              'bio',
                              '$2a$10$N58pnuVRauXEAr0w6jtauuYw.xL6D/W4nKVL267IYkl9v3.5CjEle',
                              'USER'
                          );

--changeset user:15
insert into chat values (
                               '4ae20120-419b-4679-8d13-68b9e917572c'
                        );

--changeset user:16
insert into personalchat values (
                              '4ae20120-419b-4679-8d13-68b9e917572c',
                              '54dbaf5b-101c-4958-8857-877cdd312b59',
                              '8df618ef-887f-410f-8608-3323c9ab71ae'
                          );

--changeset user:17
insert into message values (
                                    'cdf6d98f-e42d-41ce-be0c-6431af9f7dda',
                                    '4ae20120-419b-4679-8d13-68b9e917572c',
                                    '54dbaf5b-101c-4958-8857-877cdd312b59',
                                    '2022-09-14 12:23:26.295000',
                                     null
                                ),
                           (
                               'c2b57b8e-146a-4f7b-b207-a17b8d24744b',
                               '4ae20120-419b-4679-8d13-68b9e917572c',
                               '8df618ef-887f-410f-8608-3323c9ab71ae',
                               '2022-09-14 13:29:12.760000',
                               null
                           ),
                           (
                               'd03c2a38-210b-4792-9bb4-cd50de4d981c',
                               '4ae20120-419b-4679-8d13-68b9e917572c',
                               '8df618ef-887f-410f-8608-3323c9ab71ae',
                               '2022-09-14 13:58:07.858000',
                               null
                           );

--changeset user:18
insert into messagecontent values (
                               'f977ce39-b68e-436e-943b-fdf645eec9ec',
                               'd03c2a38-210b-4792-9bb4-cd50de4d981c',
                               'text2'
                           ),
                           (
                               'f977ce39-b68e-436e-943b-fdf645eec9ed',
                               'c2b57b8e-146a-4f7b-b207-a17b8d24744b',
                               'text2'
                           ),(
                               'f977ce39-b68e-436e-943b-fdf645eec9eb',
                               'cdf6d98f-e42d-41ce-be0c-6431af9f7dda',
                               'text2'
                           );

--changeset user:19
insert into reaction values (
                                      'cdf6d98f-e42d-41ce-be0c-6431af9f7dda',
                                      '54dbaf5b-101c-4958-8857-877cdd312b59',
                                      'CLOWN'
                                  ),
                            (
                                'c2b57b8e-146a-4f7b-b207-a17b8d24744b',
                                '8df618ef-887f-410f-8608-3323c9ab71ae',
                                'LIKE'
                            );