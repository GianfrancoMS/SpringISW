INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO users (username, password, enabled, role_id)
VALUES ('admin', '$2a$08$wgwoMKfYl5AUE9QtP4OjheNkkSDoqDmFGjjPE2XTPLDe9xso/hy7u', TRUE, 1);

INSERT INTO cities (name) VALUES ('Lima');
INSERT INTO cities (name) VALUES ('Arequipa');
INSERT INTO cities (name) VALUES ('Cuzco');
INSERT INTO cities (name) VALUES ('La Libertad');
INSERT INTO cities (name) VALUES ('Piura');

INSERT INTO hobbies (name) VALUES ('Videogames');
INSERT INTO hobbies (name) VALUES ('Sports');
INSERT INTO hobbies (name) VALUES ('Dancing');
INSERT INTO hobbies (name) VALUES ('Partying');
INSERT INTO hobbies (name) VALUES ('Reading');
INSERT INTO hobbies (name) VALUES ('Netflix');

INSERT INTO clients (dni, first_name, last_name, sex, description, city_id)
VALUES ('72192339', 'Henry', 'Wong', 1, 'Professor at UPC', 1);
INSERT INTO clients (dni, first_name, last_name, sex, description, city_id)
VALUES ('72192338', 'Gianfranco', 'Monzon', 1, 'XD', 1);

INSERT INTO hobby_client (hobby_id, client_id) VALUES (1, 1);
INSERT INTO hobby_client (hobby_id, client_id) VALUES (2, 2);
INSERT INTO hobby_client (hobby_id, client_id) VALUES (3, 2);
