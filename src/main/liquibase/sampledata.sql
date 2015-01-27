--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.2
-- Dumped by pg_dump version 9.3.2
-- Started on 2015-01-27 16:37:09 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

--
-- TOC entry 2272 (class 0 OID 49191586)
-- Dependencies: 173
-- Data for Name: user_accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO user_accounts (id, email, first_name, last_name, password, role, sign_in_provider, creation_time, modification_time, version) VALUES (50, 'user1@mail.com', 'Test1', 'User1', '$2a$10$HRSOZM/pQ7nyLwALOiSQy.iowCKroQv5asaqKVTOMrsX6H8zLgJBC', 'ROLE_USER', NULL, '2014-11-23 09:46:30.911', '2014-11-23 09:46:30.911', 0);
INSERT INTO user_accounts (id, email, first_name, last_name, password, role, sign_in_provider, creation_time, modification_time, version) VALUES (100, 'user2@mail.com', 'Test2', 'User2', NULL, 'ROLE_USER', 'FACEBOOK', '2014-12-06 13:28:39.175', '2014-12-06 13:28:39.175', 0);
INSERT INTO user_accounts (id, email, first_name, last_name, password, role, sign_in_provider, creation_time, modification_time, version) VALUES (200, 'user3@mail.com', 'Test3', 'User3', NULL, 'ROLE_USER', 'FACEBOOK', '2014-12-24 21:52:54.902', '2014-12-24 21:52:54.902', 0);


--
-- TOC entry 2275 (class 0 OID 49191691)
-- Dependencies: 176
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO course (id, title, summary, description, status, last_update_time, published_version_id, author, creation_time, modification_time, version) VALUES (1450, 'МОХЕРОВО-ТЕКСТИЛЬНАЯ ПЛАСТИКА', 'Курс Мохерово- текстильной пластики состоит из 5 занятий по 3 ак. часа и позволяет создавать трогательные, пушистые авторские игрушки. Занятия включают в себя большой теоретический и практический материал. В процессе обучения Вы изготовите игрушку по собственному эскизу, сделаете и распишите глаза, оформите веки и ресницы. Вы создадите новое существо, образ, наделите его характером и настроением!', NULL, 'DRAFT', NULL, NULL, 50, NULL, '2015-01-08 21:50:27.229', 3);
INSERT INTO course (id, title, summary, description, status, last_update_time, published_version_id, author, creation_time, modification_time, version) VALUES (1550, 'Живопись для начинающих', 'Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'DRAFT', NULL, 1351, 50, NULL, NULL, 0);
INSERT INTO course (id, title, summary, description, status, last_update_time, published_version_id, author, creation_time, modification_time, version) VALUES (1351, 'Не очень длинное название', 'Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'PUBLISHED', NULL, NULL, 50, NULL, '2015-01-04 20:33:27.094', 2);
INSERT INTO course (id, title, summary, description, status, last_update_time, published_version_id, author, creation_time, modification_time, version) VALUES (1400, 'Курс из двух уроков', 'Курс из двух уроков: краткое описание', NULL, 'DRAFT', NULL, NULL, 50, NULL, '2015-01-05 21:45:47.548', 3);
INSERT INTO course (id, title, summary, description, status, last_update_time, published_version_id, author, creation_time, modification_time, version) VALUES (1350, 'Космонавтика для чайников. Единственный урок. ', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'PUBLISHED', NULL, NULL, 50, NULL, '2015-01-06 23:12:00.84', 6);


--
-- TOC entry 2286 (class 0 OID 0)
-- Dependencies: 175
-- Name: course_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('course_id_seq', 31, true);


--
-- TOC entry 2279 (class 0 OID 49192420)
-- Dependencies: 185
-- Data for Name: location; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (400, NULL, -81.6556473, 30.3321838, '2015-01-04 09:30:40.249+07', '2015-01-04 09:30:40.249+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (401, NULL, 30.3261471, 59.9557495, '2015-01-04 09:33:44.008+07', '2015-01-04 09:33:44.008+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (402, NULL, 30.3156815, 59.9653664, '2015-01-04 09:36:07.691+07', '2015-01-04 09:36:07.691+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (403, NULL, 30.3390503, 59.9189529, '2015-01-04 09:38:52.888+07', '2015-01-04 09:38:52.888+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (404, NULL, 30.2775974, 59.9261589, '2015-01-04 09:38:52.897+07', '2015-01-04 09:38:52.897+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (405, NULL, 30.3084526, 59.9567871, '2015-01-04 09:40:35.87+07', '2015-01-04 09:40:35.87+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (406, NULL, 30.3202419, 59.9332657, '2015-01-04 09:45:06.561+07', '2015-01-04 09:45:06.561+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (407, NULL, 30.3481789, 59.9339943, '2015-01-04 09:45:06.564+07', '2015-01-04 09:45:06.564+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (501, NULL, NULL, NULL, '2015-01-05 01:41:26.155+07', '2015-01-05 01:41:26.155+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (502, NULL, NULL, NULL, '2015-01-05 01:41:26.158+07', '2015-01-05 01:41:26.158+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (503, NULL, NULL, NULL, '2015-01-05 01:41:26.16+07', '2015-01-05 01:41:26.16+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (504, NULL, NULL, NULL, '2015-01-05 01:41:26.162+07', '2015-01-05 01:41:26.162+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (505, NULL, NULL, NULL, '2015-01-05 01:41:26.167+07', '2015-01-05 01:41:26.167+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (150, NULL, NULL, NULL, '2015-01-02 05:39:07.565+07', '2015-01-02 05:39:07.565+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (200, NULL, NULL, NULL, '2015-01-03 06:42:17.977+07', '2015-01-03 06:42:17.977+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (201, NULL, NULL, NULL, '2015-01-03 06:42:17.979+07', '2015-01-03 06:42:17.979+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (350, NULL, NULL, NULL, '2015-01-04 09:16:26.991+07', '2015-01-04 09:16:26.991+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (351, NULL, NULL, NULL, '2015-01-04 09:16:56.6+07', '2015-01-04 09:16:56.6+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (450, NULL, 8.57236195, 50.0513, '2015-01-04 20:29:52.029+07', '2015-01-04 20:29:52.029+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (454, NULL, 30.3577938, 59.9134598, '2015-01-04 21:20:53.071+07', '2015-01-04 21:20:53.071+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (455, NULL, NULL, NULL, '2015-01-04 21:27:43.787+07', '2015-01-04 21:27:43.787+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (506, NULL, 30.3132896, 59.9675865, '2015-01-05 02:40:08.36+07', '2015-01-05 02:40:08.36+07', 0, 'https://plus.google.com/112701028457011716778/about?hl=en-US', 'Петроградская Сторона, Творческое Объединение, Россия, город Санкт-Петербург, Санкт-Петербург', 'Большой пр-т, П.С., д. 106, Санкт-Петербург', 'Большой пр-т, П.С., д. 106, <span class="locality">Санкт-Петербург</span>, <span class="country-name">Russia</span>, <span class="postal-code">197022</span>', 'http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png', 'Petrogradskaya Storona, Tvorcheskoye Obyedineniye');
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (550, NULL, NULL, NULL, '2015-01-05 20:45:20.604+07', '2015-01-05 20:45:20.604+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (600, NULL, NULL, NULL, '2015-01-06 01:12:11.418+07', '2015-01-06 01:12:11.418+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (601, 'Код домофона - 749', 6.4585824, 52.6000748, '2015-01-06 04:55:30.914+07', '2015-01-06 04:55:30.914+07', 0, 'https://maps.google.com/maps/place?q=Dedemsvaart,+Netherlands&ftid=0x47c8068a3b2e33af:0xd5756fa0f29e10dd', 'Dedemsvaart, Netherlands', 'Dedemsvaart', '<span class="locality">Dedemsvaart</span>, <span class="country-name">Netherlands</span>', 'http://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png', 'Dedemsvaart');
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (602, NULL, NULL, NULL, '2015-01-06 04:55:30.914+07', '2015-01-06 04:55:30.914+07', 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (500, 'Код домофона - 749', 30.2818985, 59.9400063, '2015-01-05 01:07:57.607+07', '2015-01-05 01:07:57.607+07', 0, 'https://plus.google.com/101320036865678604600/about?hl=en-US', 'Васильевский Остров, Россия, Санкт-Петербург', 'Санкт-Петербург', '7-я Линия Васильевского острова, 26, <span class="locality">Санкт-Петербург</span>, <span class="country-name">Russia</span>, <span class="postal-code">199004</span>', 'http://maps.gstatic.com/mapfiles/place_api/icons/lodging-71.png', 'Vasilyevskiy Ostrov');
INSERT INTO location (id, description, longitude, latitude, creation_time, modification_time, version, url, address, vicinity, html, icon, name) VALUES (650, 'студия Юлии Хабаровой "Юлианна", ул.Орбели, 11 
www.felt-club.ru', 30.34132, 60.0004692, '2015-01-09 04:38:55.619+07', '2015-01-09 04:38:55.619+07', 0, 'https://maps.google.com/maps/place?q=ulitsa+Orbeli,+11,+Sankt-Peterburg,+Russia,+194156&ftid=0x4696338c119687d3:0xf8597880d09859b4', 'Россия, город Санкт-Петербург, Санкт-Петербург, ул. Орбели 11', 'Sankt-Peterburg', '<span class="street-address">ulitsa Orbeli, 11</span>, <span class="locality">Sankt-Peterburg</span>, <span class="country-name">Russia</span>, <span class="postal-code">194156</span>', 'http://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png', 'ul. Orbeli, 11');


--
-- TOC entry 2277 (class 0 OID 49192308)
-- Dependencies: 183
-- Data for Name: schedule; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-02 16:33:00+07', 1100, '2015-01-02 20:26:00+07', NULL, '2015-01-02 05:39:07.587+07', '2015-01-02 05:39:07.587+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-09 16:00:00+07', 1151, '2015-01-09 17:26:00+07', NULL, '2015-01-03 06:42:17.979+07', '2015-01-03 06:42:17.979+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-03 06:42:17.957+07', 1150, '2015-01-03 08:23:17.957+07', NULL, '2015-01-03 06:42:17.978+07', '2015-01-04 01:06:46.262+07', 1);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-04 09:38:47.27+07', 1200, '2015-01-04 13:21:47.27+07', NULL, '2015-01-04 09:38:52.889+07', '2015-01-04 09:38:52.889+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-04 09:38:50.11+07', 1201, '2015-01-04 09:45:50.11+07', NULL, '2015-01-04 09:38:52.897+07', '2015-01-04 09:38:52.897+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-04 09:40:33.467+07', 1202, '2015-01-04 10:24:33.467+07', NULL, '2015-01-04 09:40:35.87+07', '2015-01-04 09:40:35.87+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-04 09:45:04.699+07', 1203, '2015-01-04 10:51:04.699+07', NULL, '2015-01-04 09:45:06.564+07', '2015-01-04 09:45:06.564+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-04 22:39:55.984+07', 1252, '2015-01-05 00:39:55.984+07', NULL, '2015-01-04 22:39:56.016+07', '2015-01-04 22:39:56.016+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-02-21 00:00:00+07', 1302, '2015-02-21 03:00:00+07', NULL, '2015-01-05 01:41:26.172+07', '2015-01-05 20:44:35.039+07', 4);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-02-25 23:30:00+07', 1303, '2015-02-26 02:30:00+07', NULL, '2015-01-05 01:41:26.172+07', '2015-01-05 20:44:35.039+07', 2);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-20 23:30:00+07', 1406, '2015-01-21 02:30:00+07', NULL, '2015-01-05 20:45:20.608+07', '2015-01-06 04:43:35.725+07', 2);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-19 23:30:00+07', 1550, '2015-01-20 05:03:00+07', NULL, '2015-01-06 01:11:48.432+07', '2015-01-06 04:43:48.015+07', 1);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-06 04:52:49.779+07', 1552, '2015-01-06 06:52:49.779+07', NULL, '2015-01-06 04:31:50.541+07', '2015-01-06 04:52:49.786+07', 1);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-08 01:07:00+07', 1300, '2015-01-08 03:07:00+07', NULL, '2015-01-05 01:07:57.654+07', '2015-01-06 14:33:04.61+07', 1);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-02-25 23:30:00+07', 1600, '2015-02-26 02:30:00+07', NULL, '2015-01-06 14:48:18.591+07', '2015-01-06 14:48:18.591+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-02-21 00:00:00+07', 1602, '2015-02-21 03:00:00+07', NULL, '2015-01-06 14:48:18.592+07', '2015-01-06 14:48:18.592+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-02-27 04:52:00+07', 1601, '2015-02-27 06:52:00+07', NULL, '2015-01-06 14:48:18.592+07', '2015-01-06 14:50:04.152+07', 1);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-02-23 23:30:00+07', 1603, '2015-02-24 02:30:00+07', NULL, '2015-01-06 14:48:18.593+07', '2015-01-06 14:48:18.593+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-02-23 23:30:00+07', 1301, '2015-02-24 02:40:00+07', NULL, '2015-01-05 01:41:26.171+07', '2015-01-05 20:44:35.039+07', 5);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-17 14:00:00+07', 1551, '2015-01-17 23:00:00+07', NULL, '2015-01-06 01:12:11.419+07', '2015-01-09 04:38:55.669+07', 1);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-17 14:00:00+07', 1650, '2015-01-17 14:22:00+07', NULL, '2015-01-09 04:49:50.549+07', '2015-01-09 04:49:50.549+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-17 14:00:00+07', 1651, '2015-01-17 14:22:00+07', NULL, '2015-01-09 04:49:50.55+07', '2015-01-09 04:49:50.55+07', 0);
INSERT INTO schedule (start_time, id, end_time, repeat, creation_time, modification_time, version) VALUES ('2015-01-17 14:00:00+07', 1652, '2015-01-17 14:22:00+07', NULL, '2015-01-09 04:49:50.551+07', '2015-01-09 04:49:50.551+07', 0);


--
-- TOC entry 2281 (class 0 OID 49216938)
-- Dependencies: 187
-- Data for Name: lesson; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (50, 1350, 1300, 500, 'Курс из одного урока [edit 4]', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'dddddddd', '2015-01-05 01:07:57.653+07', '2015-01-07 06:12:00.844+07', 6);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (400, 1550, 1600, 601, 'III. Основы композиции', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', NULL, '2015-01-06 14:48:18.59+07', '2015-01-06 14:48:55+07', 1);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (401, 1550, 1601, 602, '[4] Красный натюрморт', NULL, NULL, '2015-01-06 14:48:18.592+07', '2015-01-06 14:50:04.151+07', 1);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (52, 1351, 1302, 500, 'I. Вводная часть', 'dfdfdfdfПрограмма рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', NULL, '2015-01-05 01:41:26.172+07', '2015-01-05 20:42:23.036+07', 4);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (402, 1550, 1602, 506, 'I. Вводная часть [2]', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', NULL, '2015-01-06 14:48:18.592+07', '2015-01-06 17:04:55.904+07', 2);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (53, 1351, 1303, 500, 'III. Основы композиции', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', NULL, '2015-01-05 01:41:26.172+07', '2015-01-06 04:55:30.917+07', 6);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (301, 1450, 1551, 650, 'мастер-класс', NULL, NULL, '2015-01-06 01:12:11.418+07', '2015-01-09 04:38:55.669+07', 1);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (156, 1400, 1406, 506, 'Лепка 1', 'Лепка 1 описание', '2 descr', '2015-01-05 20:45:20.608+07', '2015-01-06 04:46:08.328+07', 6);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (302, 1351, 1552, 602, 'IV. Красный натюрморт', NULL, NULL, '2015-01-06 04:31:50.541+07', '2015-01-06 04:55:30.917+07', 1);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (300, 1400, 1550, 506, 'Лепка 2', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'детальное описание 2', '2015-01-06 01:11:48.427+07', '2015-01-06 04:46:56.291+07', 3);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (51, 1351, 1301, 500, 'II. Акварельный натюрморт', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'Хороший способ улучшить восприятие цвета', '2015-01-05 01:41:26.171+07', '2015-01-06 04:33:05.698+07', 5);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (403, 1550, 1603, 500, 'II. Акварельный натюрморт', 'Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве. Программа рассчитана на обучение с нуля и базируется на классической русской школе академического рисунка. Курс поможет раскрыть внутренние возможности слушателя в изобразительном искусстве.', 'Хороший способ улучшить восприятие цвета', '2015-01-06 14:48:18.593+07', '2015-01-06 14:48:18.593+07', 0);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (450, 1450, 1650, 650, '1. Основы сухого валяния шерсти', 'http://www.felt-club.ru/kursy/20-mohair-textile-plastic
Обзор видов шерсти, пряжи, игл, сопутствующих материалов и приспособлений. Создание образа. Отрисовка эскиза.
Практика: Создание первоначальных объёмов', NULL, '2015-01-09 04:49:50.546+07', '2015-01-09 04:49:50.546+07', 0);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (451, 1450, 1651, 650, '2. Правила соединения валяных элементов.', 'Виды соединений. Каркасы. Виды проволоки. Принцип наращивания объёма на каркасах. Крепление к основным формам.
Практика: Крепление элементов. Изготовление каркасов.', NULL, '2015-01-09 04:49:50.55+07', '2015-01-09 04:49:50.55+07', 0);
INSERT INTO lesson (id, course_id, schedule_id, location_id, name, summary, description, creation_time, modification_time, version) VALUES (452, 1450, 1652, 650, '3. Техника вышивки.', 'Правила наложения стежков.
Практика: Вышивка.', NULL, '2015-01-09 04:49:50.551+07', '2015-01-09 04:49:50.551+07', 0);


--
-- TOC entry 2287 (class 0 OID 0)
-- Dependencies: 186
-- Name: lesson_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('lesson_id_seq', 9, true);


--
-- TOC entry 2288 (class 0 OID 0)
-- Dependencies: 184
-- Name: location_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('location_id_seq', 13, true);


--
-- TOC entry 2289 (class 0 OID 0)
-- Dependencies: 182
-- Name: schedule_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('schedule_id_seq', 33, true);


--
-- TOC entry 2290 (class 0 OID 0)
-- Dependencies: 172
-- Name: user_accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_accounts_id_seq', 4, true);


--
-- TOC entry 2273 (class 0 OID 49191597)
-- Dependencies: 174
-- Data for Name: userconnection; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO userconnection (userid, providerid, provideruserid, rank, displayname, profileurl, imageurl, accesstoken, secret, refreshtoken, expiretime) VALUES ('compartia3@gmail.com', 'facebook', '662639272', 1, 'zaborskiy', 'http://facebook.com/profile.php?id=662639272', 'http://graph.facebook.com/v1.0/662639272/picture', 'CAAK9N6r8a2QBANuRNt9xG9ufZBHZC8xy8yV3PPrHNPeIjM0vjMkg64JtZC5x14HKWURtmsWtdZCGQiric4KivZCoExZA1q5BsZAoknZAem86ZC4uRNmvS0TFS8xCYLTELNmffVjNAvl72DVJgwEnrXqAWI3Y83cC9FDdmV4nbuBgR85ZBcEEbtOgF8WLRXtYPZBWdBnb2qXJly9RgZDZD', NULL, NULL, 1425514435108);


-- Completed on 2015-01-27 16:37:09 MSK

--
-- PostgreSQL database dump complete
--

