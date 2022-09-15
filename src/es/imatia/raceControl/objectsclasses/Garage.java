package es.imatia.raceControl.objectsclasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Garage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String garageName;
	private HashMap<String, Car> carList;

	public Garage(String garageName) {
		this.setGarageName(garageName);
		this.setCarList(new HashMap<String, Car>());
	}

	public String getGarageName() {
		return garageName;
	}

	public void setGarageName(String garageName) {
		this.garageName = garageName;
	}

	public HashMap<String, Car> getCarList() {
		return carList;
	}
	
	public void setCarList(List<Car> carList) {
		for(Car car: carList) {
			this.addCar(car);
		}
	}

	public void setCarList(HashMap<String, Car> carList) {
		this.carList = carList;
	}

	public boolean addCar(Car car) {
		boolean added = false;
		if (!this.getCarList().containsKey(car.getCarName())) {
			this.getCarList().put(car.getCarName(), car);
			added = true;
		}
		return added;
	}

}
