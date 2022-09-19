package es.imatia.raceControl.objectsclasses;

import java.util.Random;

public class Car {

	public static final int MAX_SPEED = 200;
	public static final int DEFAULT_SPEED_CHANGE = 10;

	private String carBrand;
	private String carModel;
	private String garageName;
	private int carSpeed = 0;

	public Car(String carBrand, String carModel, Garage carGarage) {
		this.setCarBrand(carBrand);
		this.setCarModel(carModel);
		this.setGarageName(carGarage);
		this.setCarSpeed(0);
	}

	public String getCarName() {
		return (this.getCarBrand() + " " + this.getCarModel());
	}

	public int getCarSpeed() {
		return carSpeed;
	}

	public void setCarSpeed(int carSpeed) {
		if (carSpeed >= 0 && carSpeed <= MAX_SPEED) {
			this.carSpeed = carSpeed;
		}
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getGarageName() {
		return garageName;
	}

	public void setGarageName(Garage carGarage) {
		this.garageName = carGarage.getGarageName();
	}

	public static int getMaxSpeed() {
		return MAX_SPEED;
	}

	public void randomSpeedChange() {
		Random random = new Random();
		if (random.nextBoolean()) {
			this.setCarSpeed(this.getCarSpeed() + Car.DEFAULT_SPEED_CHANGE);
		} else {
			this.setCarSpeed(this.getCarSpeed() - Car.DEFAULT_SPEED_CHANGE);
		}
	}

	public void accelerate() {
		this.setCarSpeed(this.getCarSpeed() + Car.DEFAULT_SPEED_CHANGE);
	}
}
