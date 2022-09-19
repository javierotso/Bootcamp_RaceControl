package es.imatia.raceControl.objectsclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class Race {

	protected static final int FIRST_PLACE_POINTS = 30;
	protected static final int SECOND_PLACE_POINTS = 20;
	protected static final int THIRD_PLACE_POINTS = 10;

	protected String raceName;
	protected HashMap<String, Garage> garageList = new HashMap<String, Garage>();
	protected ArrayList<CarCompetition> carList = new ArrayList<CarCompetition>();
	protected ArrayList<CarCompetition> podium = null;

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

	public Race(String raceName) {
		this.setRaceName(raceName);
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
			for (int i = 0; i < this.getRacePodium().size(); i++) {
				details += (i + 1) + ". " + this.getRacePodium().get(i).getCar().getCarName() + " ("
						+ this.getRacePodium().get(i).getCarPunctuation() + " puntos)\n";
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
		this.carList = new ArrayList<CarCompetition>();
		if (this.getGarageList().size() > 1) {
			for (Garage garage : this.getGarageList().values()) {
				ArrayList<Car> carGarage = new ArrayList<Car>(garage.getCarList().values());
				this.getCarList().add(new CarCompetition(carGarage.get((int) (Math.random() * carGarage.size()))));
			}
		} else if (this.getGarageList().size() == 1) {
			for (Garage garage : this.getGarageList().values()) {
				for (Car car : garage.getCarList().values()) {
					this.getCarList().add(new CarCompetition(car));
				}
			}
		}
	}

	public ArrayList<CarCompetition> getRacePodium() {
		return podium;
	}

	public void setRacePodium(ArrayList<CarCompetition> podium, boolean addPoints) {
		this.podium = new ArrayList<CarCompetition>(podium);
		for (int i = 0; i < this.podium.size() && addPoints && i < 3; i++) {
			if (i == 0) {
				carList.get(i).addPunctuation(FIRST_PLACE_POINTS);
			}
			if (i == 1) {
				carList.get(i).addPunctuation(SECOND_PLACE_POINTS);
			}
			if (i == 2) {
				carList.get(i).addPunctuation(THIRD_PLACE_POINTS);
			}
		}

	}

	public String podiumToString() {
		String podium = "\n" + this.getRaceName().toUpperCase() + "\n";
		if (this.getRacePodium() != null) {
			if ((this.getRacePodium().size() >= 1)) {
				podium += "\t1. " + this.getRacePodium().get(0).getCar().getCarName() + " ("
						+ this.getRacePodium().get(0).getCar().getGarageName() + ")\n";
			}
			if ((this.getRacePodium().size() >= 2)) {
				podium += "\t2. " + this.getRacePodium().get(1).getCar().getCarName() + " ("
						+ this.getRacePodium().get(1).getCar().getGarageName() + ")\n";
			}
			if ((this.getRacePodium().size() >= 3)) {
				podium += "\t3. " + this.getRacePodium().get(2).getCar().getCarName() + " ("
						+ this.getRacePodium().get(2).getCar().getGarageName() + ")\n";
			}
		} else {
			podium += "La carrera está pendiente de ser realizada";
		}

		return podium;
	}

	public void sortCarByDistance(int iniIndex, int maxIndex) {
		ArrayList<CarCompetition> carList = this.getCarList(); // unnecesary
		CarCompetition auxCar = null;
		int moves = 1;
		while (moves > 0) {
			moves = 0;
			for (int i = iniIndex + 1; i < maxIndex && i < carList.size(); i++) {
				if (carList.get(i).getCarDistance() > carList.get(i - 1).getCarDistance()) {
					moves += 1;
					auxCar = carList.get(i);
					carList.set(i, carList.get(i - 1));
					carList.set(i - 1, auxCar);
				}
			}
		}
	}

	public abstract String totalRace();

	public abstract String getDetails();
}
