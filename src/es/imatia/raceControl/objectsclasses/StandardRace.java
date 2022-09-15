package es.imatia.raceControl.objectsclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StandardRace extends Race {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
			car.getCar().randomSpeedChange();
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
		while (this.getCarList().get(this.getCarList().size() - 1).getCar().getCarSpeed() < PREVIEWS_RANDOM_SPEED
				&& consumedTime < this.getDuration()) {
			consumedTime++;
			ranking += this.roundString(raceStartMinutes()) + "\n";
		}
		for (int i = 0; i < (this.getDuration() - consumedTime); i++) {
			ranking += this.roundString(raceMinute()) + "\n";
		}
		this.setRacePodium(this.getCarList());
		competition.setCompetitionPodium();
		for (CarCompetition car : this.getCarList()) {
			car.resetDistance();
		}
		;
		ranking += "...........................................................";
		return ranking;
	}

	private String roundString(ArrayList<CarCompetition> carListt) {
		int carPosition = 0;
		String ranking = "";
		for (CarCompetition car : carList) {
			carPosition += 1;
			ranking += "\t" + carPosition + ". " + car.getCar().getCarName() + " (" + car.getCar().getGarageName()
					+ ") (" + car.getCarDistance() + " metros)\n";
		}
		return ranking;
	}

	public void setRacePodium(ArrayList<CarCompetition> carList) {
		this.podium = new CarCompetition[Math.min(carList.size(), 3)];
		sortCarByDistance();
		for (int i = 0; i < carList.size() && i < 3; i++) {
			if (i == 0) {
				carList.get(i).addPunctuation(FIRST_PLACE_POINTS);
			}
			if (i == 1) {
				carList.get(i).addPunctuation(SECOND_PLACE_POINTS);
			}
			if (i == 2) {
				carList.get(i).addPunctuation(THIRD_PLACE_POINTS);
			}
			this.podium[i] = carList.get(i);
		}

	}

}
