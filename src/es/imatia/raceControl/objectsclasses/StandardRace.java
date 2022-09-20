package es.imatia.raceControl.objectsclasses;

import java.util.ArrayList;

public class StandardRace extends Race {

	private static final int DEFAULT_STANDARD_RACE_DURATION = 180; // min
	private static final int PREVIEWS_RANDOM_SPEED = 60;
	private int duration;

	public StandardRace(String raceName) {
		super(raceName);
		this.setDuration(StandardRace.getDEFAULT_STANDARD_RACE_DURATION());
	}

	public StandardRace(String raceName, int duration) {
		super(raceName);
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
		this.sortCarByDistance(0, this.getCarList().size());
		return this.getCarList();
	}

	public ArrayList<CarCompetition> raceStartMinutes() {
		for (CarCompetition car : this.getCarList()) {
			car.getCar().accelerate();
			car.addMinuteDistance();
		}
		this.sortCarByDistance(0, this.getCarList().size());
		return this.getCarList();
	}

	public String totalRace() {
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
		ArrayList<CarCompetition> podium = new ArrayList<CarCompetition>();
		for(int i = 0; i < 3 && i < this.getCarList().size(); i++) {
			podium.add(this.getCarList().get(i));
		}
		this.setRacePodium(podium, true);
		this.setRacePodium(podium, true);
		for (CarCompetition car : this.getCarList()) {
			car.resetDistance();
		}
		
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

	@Override
	public String getDetails() {
		return ("\nCarrera: " + this.getRaceName() + "\n   Tipo: Estándar" + "\n   Duración: " + this.getDuration()
				+ " minutos\n");

	}

}
