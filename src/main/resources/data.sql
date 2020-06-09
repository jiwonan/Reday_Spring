INSERT INTO user VALUES (1, "asd123", "aa", "aa1", "asd@gmail.com");
INSERT INTO user VALUES (2, "fgh123", "ff", "bb2", "fgh@gmail.com");

INSERT INTO countries (id, country) VALUES (1, "Korea");
INSERT INTO countries (id, country) VALUES (2, "U.S.A.");
INSERT INTO countries (id, country) VALUES (3, "Japan");

INSERT INTO article (id, title, contents, heart, file_location, user_id, country_id) VALUES (1, "title", "content", 0, "asdf", 1, 1);
INSERT INTO article (id, title, contents, heart, file_location, user_id, country_id) VALUES (2, "title2", "content2", 0, "sdfv", 1, 1);

INSERT INTO article (id, title, contents, heart, file_location, user_id, country_id) VALUES (3, "title3", "content3", 0, "asdvdf", 2, 2);