CREATE TABLE users (
	id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(50) NOT NULL,
	phone VARCHAR(30) NOT NULL,
	email VARCHAR(30) NOT NULL,
	password VARCHAR(100) NOT NULL,
	location VARCHAR(4000) NOT NULL,
	role INT NOT NULL
);

CREATE TABLE categories (
	id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(4000) NOT NULL,
	description VARCHAR(4000) NOT NULL
);

CREATE TABLE products (
	id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	name VARCHAR(4000) NOT NULL,
	description VARCHAR(4000) NOT NULL,
	price FLOAT NOT NULL,
	category_id INT NOT NULL REFERENCES categories(id),
	user_id INT NOT NULL REFERENCES users(id),
	location VARCHAR(4000) NOT NULL,
	shippable INT NOT NULL
);

CREATE TABLE images (
	id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	product_id INT NOT NULL REFERENCES products(id),
	url VARCHAR(4000) NOT NULL
);

INSERT INTO users (name, phone, email, password, location, role) VALUES
('aaa', '94739875', 'a@a.com', 'abc', 'Tanger', 1),
('bbb', '1298748762', 'b@b.com', 'abc', 'Tangier', 0);

INSERT INTO categories (name, description) VALUES
('Ordinateurs portables', 'Acer, Lenovo, ASUS, Sony, Dell...'),
('Voitures', 'BMW, Mercedes-Benz, Audi, Peugeot, Renault, Fiat, Dacia...'),
('Téléphones', 'Samsung, Apple, LG, Huawei, Xiaomi, Oppo, Nokia...'),
('Immobilier', 'Appartements, Maisons, Bureaux, Magasins, Terrains...');

INSERT INTO products (name, description, price, category_id, user_id, location, shippable) VALUES
('Thinkpad T450', '', 3000, 1, 1, 'Tanger', 0),
('Dell XPS 13 ', '', 4000, 1, 1, 'Rabat', 0),
('BMW 535 2011', '', 170000, 2, 1, 'Casablanca', 0),
('Mercedes 240', '', 30000, 2, 1, 'Marrakech', 0),
('Samsung Galaxy S10', '', 4000, 3, 1, 'Fès', 0),
('Xiaomi Mi 9t', '', 2800, 3, 1, 'Meknès', 0),
('Appartement 120m²', '', 800000, 4, 1, 'Tanger', 0),
('terrain agricole titré', '', 9500000, 4, 1, 'Ait Ourir', 0);