package es.imatia.raceControl.objectsclasses;

import java.io.Serializable;

public class CarCompetition implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Car car;
	private int carPunctuation = 0;
	private double carDistance = 0;

	public CarCompetition(Car car) {
		this.setCar(car);
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public int getCarPunctuation() {
		return carPunctuation;
	}

	public double getCarDistance() {
		return carDistance;
	}

	public void addMinuteDistance() {
		this.carDistance += ((double) car.getCarSpeed() * 50 / 3); // distance (meters) in a minute, assuming speed km/h
	}

	public void addPunctuation(int points) {
		this.carPunctuation += points;
	}

	public void resetDistance() {
		this.carDistance = 0;
	}

	public void resetPuctuation() {
		this.carPunctuation = 0;
	}
	
}
