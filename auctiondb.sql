CREATE TABLE Users(
    user_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(20) UNIQUE NOT NULL,
    us_pass VARCHAR(20) NOT NULL,
    username VARCHAR(20) NOT NULL
);

CREATE TABLE Cars(
	car_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    model VARCHAR(20) UNIQUE NOT NULL,
    min_price DOUBLE NOT NULL,
    c_year YEAR NOT NULL,
    is_active BOOL,
    date_active DATETIME,
    c_owner INT NOT NULL,
    CONSTRAINT own_car FOREIGN KEY (c_owner) REFERENCES Users (user_id)
);

CREATE TABLE Bets(
	bet_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    betdate DATETIME NOT NULL,
    cost DOUBLE NOT NULL,
    bet_user INT NOT NULL,
    bet_car INT NOT NULL,
    CONSTRAINT place_bet FOREIGN KEY (bet_user) REFERENCES Users (user_id),
    CONSTRAINT choose_car FOREIGN KEY (bet_car) REFERENCES Cars (car_id)
);