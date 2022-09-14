package es.imatia.raceControl.objectsclasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Competition {

	public static final int DEFAULT_RACES = 10;

	private String competitionName;
	private int totalRaces;
	private int doneRaces = 0;

	private HashMap<String, Race> raceList = new HashMap<String, Race>();
	private ArrayList<CarCompetition> carList = new ArrayList<CarCompetition>();
	private HashMap<String, Garage> garageList = new HashMap<String, Garage>();
	private CarCompetition[] competitionPodium;

	public Competition(String competitionName, int totalRaces) {
		this.setCompetitionName(competitionName);
		this.setTotalRaces(totalRaces);
	}

	public Competition(String competitionName) {
		this.setCompetitionName(competitionName);
		this.setTotalRaces(Competition.getDEFAULT_RACES());
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
	}

	public int getTotalRaces() {
		return totalRaces;
	}

	public void setTotalRaces(int totalRaces) {
		this.totalRaces = totalRaces;
	}

	public int getDoneRaces() {
		return doneRaces;
	}

	public HashMap<String, Race> getRaceList() {
		return raceList;
	}

	public ArrayList<CarCompetition> getCarList() {
		return carList;
	}

	public HashMap<String, Garage> getGarageList() {
		return garageList;
	}

	public CarCompetition[] getCompetitionPodium() {
		return competitionPodium;
	}

	public static int getDEFAULT_RACES() {
		return DEFAULT_RACES;
	}

	public void addGarage(Garage garage) {
		if (!this.getGarageList().containsKey(garage.getGarageName())) {
			this.getGarageList().put(garage.getGarageName(), garage);
		}
	}

	public void addCar(CarCompetition car) {
		boolean done = false;
		for (int i = 0; i < this.getCarList().size(); i++) {
			if (this.getCarList().get(i).equals(car)) {
				done = true;
			}
		}
		if (!done) {
			this.getCarList().add(car);
		}
	}

	public void addDoneRace() {
		this.doneRaces += 1;
	}
	
	public boolean addRace(Race race) {
		boolean added = false;
		if (!this.getRaceList().containsKey(race.getRaceName())) {
			this.getRaceList().put(race.getRaceName(), race);
			added = true;
			for (CarCompetition car : race.getCarList()) {
				this.addCar(car);
			}
			for (Garage garage : race.getGarageList().values()) {
				this.addGarage(garage);
			}
		}
		return added;
	}

	public void sortCarByPunctuation() {
		ArrayList<CarCompetition> carList = this.getCarList(); // unnecesary
		CarCompetition auxCar = null;
		int moves = 1;
		while (moves > 0) {
			moves = 0;
			for (int i = 1; i < carList.size(); i++) {
				if (carList.get(i).getCarPunctuation() > carList.get(i - 1).getCarPunctuation()) {
					moves += 1;
					auxCar = carList.get(i);
					carList.set(i, carList.get(i - 1));
					carList.set(i - 1, auxCar);
				}
			}
		}
	}

	public String getCompetitionRanking() {
		String ranking = "\n___________" + this.getCompetitionName() + "___________\n";
		this.sortCarByPunctuation();
		for (int i = 0; i < this.getCarList().size(); i++) {
			CarCompetition car = this.getCarList().get(i);
			ranking += "\t" + (i + 1) + ". " + car.getCar().getCarName() + " (" + car.getCar().getGarageName()
					+ ") con " + car.getCarPunctuation() + " puntos)\n";
		}
		return ranking;
	}

	public boolean setCompetitionPodium() {
		boolean setted = false;
		if (this.getDoneRaces() == this.getTotalRaces()) {
			this.competitionPodium = new CarCompetition[3];
			this.sortCarByPunctuation();
			if (this.getCarList().size() > 2) {
				this.getCompetitionPodium()[2] = this.getCarList().get(2);
			}
			if (this.getCarList().size() > 1) {
				this.getCompetitionPodium()[1] = this.getCarList().get(1);
			}
			this.getCompetitionPodium()[0] = this.getCarList().get(0);
			setted = true;
		}
		return setted;
	}

	public String getStringCompetitionPodium() {
		String podium = "\n___________" + this.getCompetitionName() + "___________\n";
		for (int i = 0; i < this.getCompetitionPodium().length; i++) {
			if (!this.getCompetitionPodium()[i].equals(null)) {
				podium += "\t" + (i + 1) + ". " + this.getCompetitionPodium()[i].getCar().getCarName() + " ("
						+ this.getCompetitionPodium()[i].getCar().getGarageName() + ") con "
						+ this.getCompetitionPodium()[i].getCarPunctuation() + " puntos)\n";
			}
		}
		return podium;
	}

	public String getStringCompetitionGarages() {
		String garages = "\n___________" + this.getCompetitionName() + "___________\n";
		int i = 0;
		if (!this.getGarageList().isEmpty()) {
			for (Garage garage : this.getGarageList().values()) {
				i += 1;
				garages += "\t" + i + ". " + garage.getGarageName() + "\n";
			}
		} else {
			garages += "\nEste torneo todavía no tiene inscrita ninguna escudería\n";
		}
		return garages;
	}
}
