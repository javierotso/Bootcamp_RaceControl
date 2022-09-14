package es.imatia.raceControl.objectsclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StandardRace extends Race {
	private static final int DEFAULT_STANDARD_RACE_DURATION = 180; // min
	private static final int PREVIEWS_RANDOM_SPEED = 60;
	private int duration;

	public StandardRace(String raceName, Garage garage) {
		super(raceName, garage);
		this.setDuration(StandardRace.getDEFAULT_STANDARD_RACE_DURATION());
	}

	public StandardRace(String raceName, Garage garage, int duration) {
		super(raceName, garage);
		this.setDuration(duration);
	}

	public StandardRace(String raceName, HashMap<String, Garage> garageList) {
		super(raceName, garageList);
		this.setDuration(StandardRace.getDEFAULT_STANDARD_RACE_DURATION());
	}

	public StandardRace(String raceName, HashMap<String, Garage> garageList, int duration) {
		super(raceName, garageList);
		this.setDuration(duration);
	}

	public StandardRace(String raceName, List<Garage> garageList) {
		super(raceName, garageList);
		this.setDuration(StandardRace.getDEFAULT_STANDARD_RACE_DURATION());
	}

	public StandardRace(String raceName, List<Garage> garageList, int duration) {
		super(raceName, garageList);
		this.setDuration(duration);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public static int getDEFAULT_STANDARD_RACE_DURATION() {
		return DEFAULT_STANDARD_RACE_DURATION;
	}

	public ArrayList<CarCompetition> raceMinute() {
		for (CarCompetition car : this.getCarList()) {
			car.getCar().speedChange();
			car.addMinuteDistance();
		}
		this.sortCarByDistance();
		return this.getCarList();
	}

	public ArrayList<CarCompetition> raceStartMinutes() {
		for (CarCompetition car : this.getCarList()) {
			car.getCar().accelerate();
			car.addMinuteDistance();
		}
		this.sortCarByDistance();
		return this.getCarList();
	}

	public String totalRace(Competition competition) {
		String ranking = "\nCARRERA " + this.getRaceName().toUpperCase() + ":\n";
		int consumedTime = 0;
		while (this.getCarList().get(this.getCarList().size() - 1).getCar()
				.getCarSpeed() < StandardRace.PREVIEWS_RANDOM_SPEED) {
			this.raceStartMinutes();
			consumedTime += 1;
			int carPosition = 0;
			for (CarCompetition car : this.getCarList()) {
				carPosition += 1;
				ranking += "\t" + carPosition + car.getCar().getCarName() + " (" + car.getCar().getGarageName() + ")\n";
			}
		}

		for (int i = 0; i < (this.getDuration() - consumedTime); i++) {
			this.raceMinute();
			int carPosition = 0;
			for (CarCompetition car : this.getCarList()) {
				carPosition += 1;
				ranking += "\t" + carPosition + car.getCar().getCarName() + " (" + car.getCar().getGarageName() + ")\n";
			}
		}
		/*
		 * End of race... inicializate distance and car speed to 0
		 */
		for (CarCompetition car : this.getCarList()) {
			car.resetDistance();
			car.getCar().setCarSpeed(0);
		}
		/*
		 * Set pódium
		 */
		this.setRacePodium();
		competition.addDoneRace();
		competition.setCompetitionPodium();
		return ranking;
	}

	public void setRacePodium() {
		this.podium = new CarCompetition[3];
		sortCarByDistance();
		if (!this.getCarList().get(0).equals(null)) {
			this.getCarList().get(0).addPunctuation(FIRST_PLACE_POINTS);
			this.podium[0] = this.getCarList().get(0);
		}
		if (!this.getCarList().get(1).equals(null)) {
			this.getCarList().get(1).addPunctuation(SECOND_PLACE_POINTS);
			this.podium[0] = this.getCarList().get(1);
		}
		if (!this.getCarList().get(2).equals(null)) {
			this.getCarList().get(2).addPunctuation(THIRD_PLACE_POINTS);
			this.podium[0] = this.getCarList().get(2);
		}

	}

}
