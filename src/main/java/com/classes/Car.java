package com.classes;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Car implements Serializable{
	

	private static final long serialVersionUID = 3564782670542534183L;
	
	private int carId;
	private String model;
	private double minPrice;
	private int year;
	private Owner owner;
	private boolean isActive;
	private GregorianCalendar dateActive;
	
	public Car() {
		setCarId(-1);
		model = "0";
		minPrice = 0.0;
		year = 1000;
		owner = null;
		isActive = false;
		setDateActive(null);
	}
	
	public Car(int carId, String model, double minPrice, int year, Owner owner, boolean isActive, GregorianCalendar dateActive) {
		this.setCarId(carId);
		this.model = model;
		this.minPrice = minPrice;
		this.year = year;
		this.owner = owner;
		this.isActive = isActive;
		this.setDateActive(dateActive);
	}

	@Override
	public String toString() {
		return "Car [model=" + model + ", price=" + minPrice + ", year=" + year + ", owner=" + owner + "]";
	}
	
	@Override
	public int hashCode() {
		return year;
	}

	@Override
	public boolean equals(Object ob) {
		if (this == ob)
			return true;
		if (ob == null)
			return false;
		if (getClass() != ob.getClass())
			return false;
		Car other = (Car) ob;
		return Objects.equals(model, other.model) && Objects.equals(owner, other.owner)
				 && year == other.year;
	}

	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getPrice() {
		return minPrice;
	}
	public void setPrice(double price) {
		this.minPrice = price;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Owner getOwner() {
		return owner;
	}
	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public GregorianCalendar getDateActive() {
		return dateActive;
	}
	public void setDateActive(GregorianCalendar dateActive) {
		this.dateActive = dateActive;
	}
	
	public static String getUsersSQL() {
		return "SELECT * FROM USERS";
	}
}
