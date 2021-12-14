package com.stonksco.minitramways.logic;

/**
 * Contient toutes les donn�es du joueur
 */
public class Player {

	private double satisfactionAvg;
	private int money = 0;

	public double getSatisfactionAvg() {
		return this.satisfactionAvg;
	}

	/**
	 * 
	 * @param satisfactionAvg
	 */
	public void setSatisfactionAvg(double satisfactionAvg) {
		this.satisfactionAvg = satisfactionAvg;
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


	public void addMoney(int nb) {
		money+=nb;
	}
}