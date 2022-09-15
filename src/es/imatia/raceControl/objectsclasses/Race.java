package es.imatia.raceControl.objectsclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class Race implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final int FIRST_PLACE_POINTS = 30;
	protected static final int SECOND_PLACE_POINTS = 20;
	protected static final int THIRD_PLACE_POINTS = 10;

	protected String raceName;
	protected HashMap<String, Garage> garageList = new HashMap<String, Garage>();
	protected ArrayList<CarCompetition> carList = new ArrayList<CarCompetition>();
	protected CarCompetition podium[];

	public Race(String raceName, Garage garage) {
		this.setRaceName(raceName);
		this.addGarage(garage);
	}

	public Race(String raceName, HashMap<String, Garage> garageList) {
		this.setRaceName(raceName);
		this.garageList = garageList;
		this.setCarList();
	}

	public Race(String raceName, List<Garage> garageList) {
		for (Garage garage : garageList) {
			this.garageList.put(garage.getGarageName(), garage);
		}
		this.setCarList();
	}

	public String getRaceName() {
		return raceName;
	}

	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}

	public HashMap<String, Garage> getGarageList() {
		return garageList;
	}

	public void setGarageList(HashMap<String, Garage> garageList) {
		this.garageList = garageList;
	}

	public void addGarage(Garage garage) {
		this.getGarageList().put(garage.getGarageName(), garage);
		this.setCarList();
	}

	public ArrayList<CarCompetition> getCarList() {
		return carList;
	}

	public String showDetails() {
		String details = "\n___" + this.getRaceName() + "___\n";
		if (!Objects.isNull(podium)) {
			for (int i = 0; i < this.getRacePodium().length; i++) {
				details += (i + 1) + ". " + this.getRacePodium()[i].getCar().getCarName() + " ("
						+ this.getRacePodium()[i].getCarPunctuation() + " puntos)\n";
			}
		} else {
			details += ".... pendiente de realizar ....\n";
		}
		return details;
	}

	public void setCarList(ArrayList<CarCompetition> carList) {
		this.carList = carList;
	}

	public void setCarList() {
		if (this.getGarageList().size() > 1) {
			for (Garage garage : this.getGarageList().values()) {
				ArrayList<Car> carGarage = new ArrayList<Car>(garage.getCarList().values());
				this.getCarList().add(new CarCompetition(carGarage.get((int) (Math.random() * carGarage.size()))));
			}
		} else if (this.getGarageList().size() == 1) {
			for(Garage garage : this.getGarageList().values()) {
				for(Car car : garage.getCarList().values()) {
					this.getCarList().add(new CarCompetition(car));
				}
			}
		}
	}

	public CarCompetition[] getRacePodium() {
		return podium;
	}

	public String podiumToString() {
		String podium = "\n" + this.getRaceName().toUpperCase() + "\n";
		if (this.getRacePodium()[0].equals(null)) {
			podium += "\t1. " + this.getRacePodium()[0].getCar().getCarName() + " ("
					+ this.getRacePodium()[0].getCar().getGarageName() + ")\n";
		}
		if (this.getRacePodium()[1].equals(null)) {
			podium += "\t1. " + this.getRacePodium()[1].getCar().getCarName() + " ("
					+ this.getRacePodium()[0].getCar().getGarageName() + ")\n";
		}
		if (this.getRacePodium()[2].equals(null)) {
			podium += "\t1. " + this.getRacePodium()[2].getCar().getCarName() + " ("
					+ this.getRacePodium()[0].getCar().getGarageName() + ")\n";
		}
		return podium;
	}

	public void sortCarByDistance() {
		ArrayList<CarCompetition> carList = this.getCarList(); // unnecesary
		CarCompetition auxCar = null;
		int moves = 1;
		while (moves > 0) {
			moves = 0;
			for (int i = 1; i < carList.size(); i++) {
				if (carList.get(i).getCarDistance() > carList.get(i - 1).getCarDistance()) {
					moves += 1;
					auxCar = carList.get(i);
					carList.set(i, carList.get(i - 1));
					carList.set(i - 1, auxCar);
				}
			}
		}
	}

	public abstract String totalRace(Competition competition);

}
