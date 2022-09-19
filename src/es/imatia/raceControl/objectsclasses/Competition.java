package es.imatia.raceControl.objectsclasses;

import java.util.ArrayList;
import java.util.HashMap;

public class Competition {

	private String competitionName;
	private int doneRaces = 0;

	private HashMap<String, Race> raceList = new HashMap<String, Race>();
	private ArrayList<CarCompetition> carList = new ArrayList<CarCompetition>();
	private HashMap<String, Garage> garageList = new HashMap<String, Garage>();
	private ArrayList<CarCompetition> competitionPodium = null;

	public Competition(String competitionName, int doneRaces) {
		this.setCompetitionName(competitionName);
		this.doneRaces = doneRaces;
	}

	public Competition(String competitionName) {
		this.setCompetitionName(competitionName);
	}

	public String getCompetitionName() {
		return competitionName;
	}

	public void setCompetitionName(String competitionName) {
		this.competitionName = competitionName;
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

	public ArrayList<CarCompetition> getCompetitionPodium() {
		return competitionPodium;
	}

	public void addCarList(ArrayList<CarCompetition> carList, HashMap<String, Garage> garageList) {
		for (CarCompetition car : carList) {
			this.addCar(car, garageList);
		}
	}

	public void addCar(CarCompetition car, HashMap<String, Garage> garageList) {
		if (!this.getCarList().contains(car)) {
			this.getCarList().add(car);
			if (!this.getGarageList().containsKey(car.getCar().getGarageName())) {
				this.getGarageList().put(car.getCar().getGarageName(), garageList.get(car.getCar().getGarageName()));
			}
		}
	}

	public void addDoneRace() {
		this.doneRaces += 1;
	}

	public boolean deleteRace(Race race) {
		boolean deleted = false;
		if (raceList.containsKey(race.getRaceName())) {
			if (raceList.get(race.getRaceName()).getRacePodium() == null) {
				raceList.remove(race.getRaceName());
				deleted = true;
			}
		}
		return deleted;
	}

	public boolean addRace(Race race) {
		boolean added = false;
		if (!this.getRaceList().containsKey(race.getRaceName())) {
			if (race instanceof EliminationRace) {
				raceList.put(race.getRaceName(),
						new EliminationRace(race.getRaceName(), ((EliminationRace) race).getPreviewsRounds()));
			} else if (race instanceof StandardRace) {
				raceList.put(race.getRaceName(),
						new StandardRace(race.getRaceName(), ((StandardRace) race).getDuration()));
			}
			added = true;
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
			ranking += (i + 1) + ". " + car.getCar().getCarName() + " (" + car.getCar().getGarageName() + ") con "
					+ car.getCarPunctuation() + " puntos\n";
		}
		ranking += "\n...............................\n";
		return ranking;
	}

	public boolean setCompetitionPodium() {
		boolean setted = false;
		if (this.getDoneRaces() == this.getRaceList().size()) {
			this.competitionPodium = new ArrayList<CarCompetition>();
			this.sortCarByPunctuation();
			for (int i = 0; i < this.getCarList().size() && i < 3; i++) {
				competitionPodium.add(i, this.getCarList().get(i));
			}
			setted = true;
		}
		return setted;
	}

	public String getStringCompetitionPodium() {
		String podium = "\n___________" + this.getCompetitionName() + "___________\n";
		for (int i = 0; i < this.getCompetitionPodium().size(); i++) {
			podium += "\t" + (i + 1) + ". " + this.getCompetitionPodium().get(i).getCar().getCarName() + " ("
					+ this.getCompetitionPodium().get(i).getCar().getGarageName() + ") con "
					+ this.getCompetitionPodium().get(i).getCarPunctuation() + " puntos)\n";

		}
		podium += "...........................\n";
		return podium;
	}
}
