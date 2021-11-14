package com.stonksco.minitramways.logic;

/**
 * Contient toutes les donn�es du joueur
 */
public class Player {

	private double satisfaction;
	private int money;

	public double getSatisfaction() {
		return this.satisfaction;
	}

	/**
	 * 
	 * @param satisfaction
	 */
	public void setSatisfaction(double satisfaction) {
		this.satisfaction = satisfaction;
	}

	public int getMoney() {
		return this.money;
	}

	/**
	 * 
	 * @param money
	 */
	public void setMoney(int money) {
		this.money = money;
	}

}