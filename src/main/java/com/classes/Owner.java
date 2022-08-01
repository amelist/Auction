package com.classes;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Owner implements Serializable{
	
	private static final long serialVersionUID = -3195841211759727509L;
	
	private int userId;
	private String login;
	private String password;
	private String name;
	private List<Car> ownedCars;
	private List<Bet> bets;
	
	public Owner() {
		userId = -1;
		login = "1";
		password = "1";
		name = "1";
		ownedCars = Collections.emptyList();
		bets = Collections.emptyList();
	}
	
	public Owner(int userId, String login, String password, String name) {
		this.setUserId(userId);
		this.login = login;
		this.password = password;
		this.name = name;
		ownedCars = Collections.emptyList();
		bets = Collections.emptyList();
	}

	public Owner(int userId, String login, String password, String name, List<Car> ownedCars, List<Bet> bets) {
		this.setUserId(userId);
		this.login = login;
		this.password = password;
		this.name = name;
		this.ownedCars = ownedCars;
		this.bets = bets;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Car> getSelledCars() {
		return ownedCars;
	}
	public void setSelledCars(List<Car> ownedCars) {
		this.ownedCars = ownedCars;
	}
	public List<Bet> getBets() {
		return bets;
	}
	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}
	@Override
	public String toString() {
		return userId + name;
	}
	
	@Override
	public int hashCode() {
		return Character.getNumericValue(login.charAt(1)) + 15 * Character.getNumericValue(name.charAt(1));
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        Owner toCompare = (Owner) o;
        if(this.login == toCompare.getLogin()) return true;
        return false;
        }
}
