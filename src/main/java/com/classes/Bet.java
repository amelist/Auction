package com.classes;

import java.util.GregorianCalendar;
public class Bet {
	
	private int betId;
	private Owner buyer;
	private Car auto;
	private GregorianCalendar betdate;
	private float cost;	
	
	@Override
	public int hashCode() {
		return 21*betId + Math.round(cost);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bet other = (Bet) obj;
		return auto.equals(other.auto) && buyer.equals(other.buyer)
				&& cost == other.cost;
	}
	
	public Bet() {
		betId = -1;
		buyer = null;
		auto = null;
		betdate = null;
		cost = 0;
	}
	public Bet(int betId, Owner buyer, Car auto, GregorianCalendar betdate, float cost) {
		this.betId = betId;
		this.buyer = buyer;
		this.auto = auto;
		this.betdate = betdate;
		this.cost = cost;
	}
	
	
	public int getBetId() {
		return betId;
	}
	public void setBetId(int betId) {
		this.betId = betId;
	}
	public GregorianCalendar getBetdate() {
		return betdate;
	}
	public void setBetdate(GregorianCalendar betdate) {
		this.betdate = betdate;
	}
	public Owner getBuyer() {
		return buyer;
	}
	public void setBuyer(Owner buyer) {
		this.buyer = buyer;
	}
	public Car getAuto() {
		return auto;
	}
	public void setAuto(Car auto) {
		this.auto = auto;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
}
