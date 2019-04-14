DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TABLE public.users
(
    username text PRIMARY KEY,
    email text NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    date_of_birth date NOT NULL,
    height int NOT NULL,
    gender char NOT NULL,
    password_hash char(64) NOT NULL
);

CREATE TABLE public.groups
(
    id serial PRIMARY KEY,
    name text NOT NULL
);

CREATE TABLE public.users_groups
(
    username text NOT NULL,
    group_id int NOT NULL,
    CONSTRAINT users_groups_username_group_id_pk PRIMARY KEY (username, group_id),
		CONSTRAINT users_groups_users_id_fk FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE,
    CONSTRAINT users_groups_groups_id_fk FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE
);

CREATE TABLE public.users_weights
(
    username text NOT NULL,
    weight numeric NOT NULL,
    recorded date NOT NULL,
    CONSTRAINT users_weights_username_recorded_pk PRIMARY KEY (username, recorded),
    CONSTRAINT users_weights_users_username_fk FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE public.meals
(
    id serial PRIMARY KEY,
    name text NOT NULL,
    author text NOT NULL,
    public boolean DEFAULT false NOT NULL,
    CONSTRAINT meals_users_username_fk FOREIGN KEY (author) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE public.foods
(
		id serial PRIMARY KEY,
		name text NOT NULL,
		calories numeric NOT NULL,
		protein numeric NOT NULL,
		fat numeric NOT NULL,
		carbohydrates numeric NOT NULL,
		serving_size int NOT NULL,
		serving_name text NOT NULL,
		author text NOT NULL,
		public boolean DEFAULT false NOT NULL,
		CONSTRAINT foods_users_username_fk FOREIGN KEY (author) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE public.exercises
(
		id serial PRIMARY KEY,
		name text NOT NULL,
		multiplier numeric NOT NULL,
		adjustment numeric NOT NULL,
		measurable boolean NOT NULL
);

CREATE TABLE public.meals_foods
(
		meal int NOT NULL,
		food int NOT NULL,
		quantity int NOT NULL,
		CONSTRAINT meals_foods_meal_food_pk PRIMARY KEY (meal, food),
		CONSTRAINT meals_foods_meals_id_fk FOREIGN KEY (meal) REFERENCES meals (id) ON DELETE CASCADE,
		CONSTRAINT meals_foods_foods_id_fk FOREIGN KEY (food) REFERENCES foods (id) ON DELETE CASCADE
);

CREATE TABLE public.users_meals
(
		username text NOT NULL,
		meal int NOT NULL,
		quantity numeric NOT NULL,
		time int NOT NULL,
		eaten date NOT NULL,
		CONSTRAINT users_meals_users_username_fk FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE,
		CONSTRAINT users_meals_meals_id_fk FOREIGN KEY (meal) REFERENCES meals (id) ON DELETE CASCADE
);

CREATE TABLE public.activities
(
	 	username text NOT NULL,
		exercise int NOT NULL,
		duration int NOT NULL,
		intensity int NOT NULL,
		timestamp date NOT NULL
);

CREATE TABLE public.goals_weightloss
(
		username text NOT NULL,
		starting_date date NOT NULL,
		duration int NOT NULL,
		starting_weight numeric NOT NULL,
		target_weight numeric NOT NULL,
		CONSTRAINT goals_weightloss_users_username_fk FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

CREATE TABLE public.goals_distance
(
		username text NOT NULL,
		exercise int NOT NULL,
		starting_date date NOT NULL,
		duration int NOT NULL,
		starting_distance int NOT NULL,
		target_distance int NOT NULL,
		CONSTRAINT goals_distance_users_username_fk FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE,
		CONSTRAINT goals_distance_exercises_id_fk FOREIGN KEY (exercise) REFERENCES exercises (id) ON DELETE CASCADE
);

CREATE TABLE public.goals_time
(
		username text NOT NULL,
		exercise int NOT NULL,
		starting_date date NOT NULL,
		duration int NOT NULL,
		distance int NOT NULL,
		starting_time int NOT NULL,
		target_time int NOT NULL,
		CONSTRAINT goals_time_users_username_fk FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE,
		CONSTRAINT goals_time_exercises_id_fk FOREIGN KEY (exercise) REFERENCES exercises (id) ON DELETE CASCADE
);

INSERT INTO public.users (username, email, first_name, last_name, date_of_birth, height, gender, password_hash) VALUES ('JSmith', 'benjaminlwood@gmail.com', 'John', 'Smith', '1966-11-11', 172, 'M', '�e�J�K\7Nf�����                                                ');
INSERT INTO public.users (username, email, first_name, last_name, date_of_birth, height, gender, password_hash) VALUES ('demo', 'demo@example.com', 'Test', 'Account', '2000-01-01', 172, 'U', '�e�J�K\7Nf�����                                                ');
INSERT INTO public.users (username, email, first_name, last_name, date_of_birth, height, gender, password_hash) VALUES ('ActiFit', 'info@actifit.site', 'Acti', 'Fit', '2000-01-01', 200, 'U', '�e�J�K\7Nf�����                                                ');

INSERT INTO public.groups (id, name) VALUES (101, 'Half Marathon Crew');
INSERT INTO public.groups (id, name) VALUES (102, 'Eco-eating');

INSERT INTO public.users_groups (username, group_id) VALUES ('JSmith', 101);
INSERT INTO public.users_groups (username, group_id) VALUES ('JSmith', 102);

INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.9, '2018-04-03');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 87.1, '2018-04-04');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 87.8, '2018-04-05');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.8, '2018-04-06');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 87.4, '2018-04-07');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.5, '2018-04-08');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.7, '2018-04-09');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 87.2, '2018-04-10');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.2, '2018-04-11');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.4, '2018-04-12');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 87.2, '2018-04-13');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 85.3, '2018-04-14');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.0, '2018-04-15');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.5, '2018-04-16');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 85.7, '2018-04-17');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.2, '2018-04-18');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 86.0, '2018-04-19');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.7, '2018-04-20');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.5, '2018-04-21');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.6, '2018-04-22');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 85.3, '2018-04-23');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.6, '2018-04-24');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.0, '2018-04-25');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.7, '2018-04-26');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 85.2, '2018-04-27');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.8, '2018-04-28');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 85.0, '2018-04-29');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 83.7, '2018-04-30');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 83.8, '2018-05-01');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 83.4, '2018-05-02');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 83.2, '2018-05-03');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.5, '2018-05-04');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 85.0, '2018-05-05');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.2, '2018-05-06');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 83.2, '2018-05-07');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 83.1, '2018-05-08');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.2, '2018-05-09');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 83.7, '2018-05-10');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 82.8, '2018-05-11');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 84.1, '2018-05-12');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 83.1, '2018-05-13');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('demo', 82.6, '2018-05-14');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('JSmith', 83.7, '2018-05-10');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('JSmith', 82.8, '2018-05-11');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('JSmith', 84.1, '2018-05-12');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('JSmith', 83.1, '2018-05-13');
INSERT INTO public.users_weights (username, weight, recorded) VALUES ('JSmith', 82.6, '2018-05-14');

INSERT INTO public.exercises (id, name, multiplier, adjustment, measurable) VALUES (1, 'Running', 1.06, -0.8, true);
INSERT INTO public.exercises (id, name, multiplier, adjustment, measurable) VALUES (2, 'Cycling', 0.45, -0.5, true);
INSERT INTO public.exercises (id, name, multiplier, adjustment, measurable) VALUES (3, 'Press-ups', 2.5, 0.5, false);

INSERT INTO public.meals (id, name, author, public) VALUES (101, 'Chips and pasta', 'ActiFit', true);
INSERT INTO public.meals (id, name, author, public) VALUES (102, 'Belvita', 'ActiFit', true);
INSERT INTO public.meals (id, name, author, public) VALUES (103, 'Pasta and tomato sauce', 'ActiFit', true);
-- INSERT INTO public.meals (name, author, public) VALUES ('Chicken nuggets and chips', 'ActiFit', true);

INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (101, 'Belvita Chocolate Chip Biscuits', 433, 7.7, 14, 65, 11, 'biscuit', 'ActiFit', true);
INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (102, 'Kelloggs Corn Flakes Cereal', 378, 7, 0.9, 84, 30, 'portion', 'ActiFit', true);
INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (103, 'McCain Home Chips', 196, 3, 5, 34.8, 150, 'portion', 'ActiFit', true);
INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (104, 'Napolina Fusilli Pasta', 356, 12, 1.5, 72, 75, 'portion', 'ActiFit', true);
INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (105, 'Oasis Citrus Punch', 18, 0, 0, 4.1, 250, '1/2 a bottle', 'ActiFit', true);
INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (106, 'Propercorn Sweet And Salty', 451, 5.4, 18, 63.2, 30, 'packet', 'ActiFit', true);
INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (107, 'Tesco British Semi Skimmed Milk', 49.8, 3.6, 1.8, 4.8, 125, 'small glass', 'ActiFit', true);
INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (108, 'Tesco Chunky Vegetable Pasta Sauce', 39.5, 1.2, 0.5, 6.7, 125, '1/4 of a jar', 'ActiFit', true);
INSERT INTO public.foods (id, name, calories, protein, fat, carbohydrates, serving_size, serving_name, author, public) VALUES (109, 'Tesco Roast Chicken Bacon And Stuffing Sandwich', 256.4, 14.6, 11.2, 22.9, 208, 'pack', 'ActiFit', true);
-- INSERT INTO public.foods (name, calories, fat, carbohydrates, serving_size, serving_name, author, public, protein) VALUES ('Birds Eye Chicken Nuggets', 251, 12, 21, 102, '5 chicken nuggets', 'ActiFit', true, 14);

INSERT INTO public.meals_foods (meal, food, quantity) VALUES (101, 103, 100);
INSERT INTO public.meals_foods (meal, food, quantity) VALUES (101, 104, 75);
INSERT INTO public.meals_foods (meal, food, quantity) VALUES (102, 101, 44);
INSERT INTO public.meals_foods (meal, food, quantity) VALUES (103, 104, 75);
INSERT INTO public.meals_foods (meal, food, quantity) VALUES (103, 108, 125);

INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1.5, 1, '2018-05-01');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 103, 0.5, 2, '2018-05-01');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 0.5, 3, '2018-05-01');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 0.75, 1, '2018-05-02');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1.5, 2, '2018-05-02');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 2, 3, '2018-05-02');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 1.5, 1, '2018-05-03');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 2, 2, '2018-05-03');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 0.75, 3, '2018-05-03');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 0.5, 1, '2018-05-04');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 2, 2, '2018-05-04');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 0.5, 3, '2018-05-04');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 2, 1, '2018-05-05');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 0.5, 2, '2018-05-05');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 0.75, 3, '2018-05-05');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 1, 1, '2018-05-06');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 0.5, 2, '2018-05-06');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1, 3, '2018-05-06');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 0.75, 1, '2018-05-07');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 1, 2, '2018-05-07');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 103, 1, 3, '2018-05-07');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 2, 1, '2018-05-08');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1.5, 2, '2018-05-08');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 1.5, 3, '2018-05-08');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 0.75, 1, '2018-05-09');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1.5, 2, '2018-05-09');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 1.5, 3, '2018-05-09');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 103, 2, 1, '2018-05-10');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 2, 2, '2018-05-10');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 0.5, 3, '2018-05-10');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1, 1, '2018-05-11');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1.5, 2, '2018-05-11');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 103, 1, 3, '2018-05-11');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 103, 2, 1, '2018-05-12');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 0.75, 2, '2018-05-12');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1.5, 3, '2018-05-12');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 1.5, 1, '2018-05-13');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 1.5, 2, '2018-05-13');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 2, 3, '2018-05-13');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 103, 2, 1, '2018-05-14');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 103, 2, 2, '2018-05-14');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 1, 3, '2018-05-14');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 101, 2, 1, '2018-05-15');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 102, 1, 2, '2018-05-15');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('demo', 103, 0.5, 3, '2018-05-15');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 2, 1, '2018-05-01');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 0.5, 2, '2018-05-01');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 2, 3, '2018-05-01');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 0.5, 1, '2018-05-02');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 0.75, 2, '2018-05-02');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 0.75, 3, '2018-05-02');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 0.5, 1, '2018-05-03');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 1, 2, '2018-05-03');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 0.75, 3, '2018-05-03');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1.5, 1, '2018-05-04');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 0.75, 2, '2018-05-04');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 2, 3, '2018-05-04');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 1.5, 1, '2018-05-05');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1.5, 2, '2018-05-05');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 1, 3, '2018-05-05');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 0.75, 1, '2018-05-06');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 1.5, 2, '2018-05-06');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 1.5, 3, '2018-05-06');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 2, 1, '2018-05-07');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 1, 2, '2018-05-07');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 1, 3, '2018-05-07');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 0.75, 1, '2018-05-08');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 0.5, 2, '2018-05-08');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 2, 3, '2018-05-08');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 0.75, 1, '2018-05-09');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 0.5, 2, '2018-05-09');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 0.75, 3, '2018-05-09');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1, 1, '2018-05-10');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1.5, 2, '2018-05-10');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 0.75, 3, '2018-05-10');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 1.5, 1, '2018-05-11');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 2, 2, '2018-05-11');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1.5, 3, '2018-05-11');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1, 1, '2018-05-12');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1.5, 2, '2018-05-12');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 0.75, 3, '2018-05-12');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 1.5, 1, '2018-05-13');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1.5, 2, '2018-05-13');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 1, 3, '2018-05-13');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 0.5, 1, '2018-05-14');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 1.5, 2, '2018-05-14');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 0.5, 3, '2018-05-14');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 101, 1.5, 1, '2018-05-15');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 102, 0.75, 2, '2018-05-15');
INSERT INTO public.users_meals (username, meal, quantity, time, eaten) VALUES ('JSmith', 103, 2, 3, '2018-05-15');

INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1344, 3472, '2018-05-01');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1880, 3428, '2018-05-02');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1355, 3706, '2018-05-03');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1113, 3251, '2018-05-04');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1450, 5246, '2018-05-05');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1031, 5806, '2018-05-06');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1868, 4894, '2018-05-07');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1202, 4810, '2018-05-08');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1448, 3874, '2018-05-09');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1936, 4131, '2018-05-10');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1342, 3722, '2018-05-11');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1619, 5836, '2018-05-12');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1547, 4383, '2018-05-13');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1731, 3853, '2018-05-14');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('demo', 1, 1317, 5194, '2018-05-15');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1245, 3294, '2018-05-01');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1616, 5099, '2018-05-02');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1299, 5110, '2018-05-03');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1515, 3923, '2018-05-04');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1754, 3718, '2018-05-05');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1292, 3648, '2018-05-06');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1310, 3785, '2018-05-07');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1403, 4791, '2018-05-08');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1367, 5134, '2018-05-09');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1394, 4907, '2018-05-10');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1338, 5386, '2018-05-11');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1879, 4755, '2018-05-12');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1857, 3460, '2018-05-13');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1138, 4644, '2018-05-14');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1217, 4309, '2018-05-15');
INSERT INTO public.activities (username, exercise, duration, intensity, timestamp) VALUES ('JSmith', 1, 1515, 3923, '2018-05-16');
