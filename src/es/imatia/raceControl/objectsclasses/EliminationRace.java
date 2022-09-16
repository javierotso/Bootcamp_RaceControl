package es.imatia.raceControl.objectsclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EliminationRace extends Race {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_PREVIEWS_ROUNDS = 10;
	private int previewsRounds;

	public EliminationRace(String raceName) {
		super(raceName);
		this.setPreviewsRounds(EliminationRace.getDEFAULT_PREVIEWS_ROUNDS());
	}

	public EliminationRace(String raceName, int previews) {
		super(raceName);
		this.setPreviewsRounds(previews);
	}

	public int getPreviewsRounds() {
		return previewsRounds;
	}

	public void setPreviewsRounds(int previewsRounds) {
		this.previewsRounds = previewsRounds;
	}

	public static int getDEFAULT_PREVIEWS_ROUNDS() {
		return DEFAULT_PREVIEWS_ROUNDS;
	}

	public void previewsRoundRace() {
		for (int i = 0; i < this.getPreviewsRounds(); i++) {
			for (CarCompetition car : this.getCarList()) {
				car.getCar().randomSpeedChange();
			}
		}
	}

	public ArrayList<CarCompetition> raceRound() {
		for (CarCompetition car : this.getCarList()) {
			car.getCar().accelerate();
			car.addMinuteDistance();
		}
		this.sortCarByDistance();
		return this.getCarList();
	}
	
	public String getDetails() {
		
		return ("\nCarrera: " + this.getRaceName() + "\n   Tipo: Eliminatoria" + "\n   Rondas de calentamiento: " + this.getPreviewsRounds() + "\n");
	}

	public String totalRace(Competition competition) {
		String ranking = "\nCARRERA " + this.getRaceName().toUpperCase() + ":\n";
		int roundCount = 0;
		while (this.getCarList().size() > 3) {
			roundCount += 1;
			ranking += "\nVuelta " + roundCount + "\n";
			this.raceRound();
			int carPosition = 0;
			for (int i = 0; i < this.getCarList().size(); i++) {
				CarCompetition car = this.getCarList().get(i);
				if (i != (getCarList().size() - 1)) {
					carPosition += 1;
					ranking += "\t" + carPosition + ". " + car.getCar().getCarName() + " ("
							+ car.getCar().getGarageName() + ")\n";
				} else {
					car.resetDistance();
					car.getCar().setCarSpeed(0);
					ranking += "\nELIMINADO: " + car.getCar().getCarName() + " (" + car.getCar().getGarageName()
							+ ")\n";
				}
			}
			this.sortCarByDistance();
			this.getCarList().remove(this.getCarList().size() - 1);
		}

		if (this.getCarList().size() == 3) {
			ranking += "\nPRE FINAL ROUND: ";
			ranking += "\n\t1. " + this.getCarList().get(0).getCar().getCarName() + " ("
					+ this.getCarList().get(0).getCar().getGarageName() + ")\n";
			ranking += "\t2. " + this.getCarList().get(1).getCar().getCarName() + " ("
					+ this.getCarList().get(1).getCar().getGarageName() + ")\n";

			this.getCarList().get(2).resetDistance();
			this.getCarList().get(2).getCar().setCarSpeed(0);

			ranking += "\n BRONZE: " + this.getCarList().get(2).getCar().getCarName() + " ("
					+ this.getCarList().get(2).getCar().getGarageName() + ")\n";

			this.setBronze(this.getCarList().get(2));
			this.getCarList().remove(2);
		}

		if (this.getCarList().size() == 2) {
			ranking += "\nFINAL ROUND: ";
			ranking += "\n GOLD:. " + this.getCarList().get(0).getCar().getCarName() + " ("
					+ this.getCarList().get(0).getCar().getGarageName() + ")";
			ranking += "\n SILVER: " + this.getCarList().get(1).getCar().getCarName() + " ("
					+ this.getCarList().get(1).getCar().getGarageName() + ")";
			this.setSilver(this.getCarList().get(1));
			this.setGold(this.getCarList().get(0));
		}

		if (this.getCarList().size() == 1) {
			ranking += "\nFINAL: ";
			ranking += "\n GOLD:. " + this.getCarList().get(0).getCar().getCarName() + " ("
					+ this.getCarList().get(0).getCar().getGarageName() + ")\n";
			this.setGold(this.getCarList().get(0));
		}
		ranking += "\n*********************************\n";
		competition.addDoneRace();
		competition.setCompetitionPodium();
		return ranking;
	}

	public void setBronze(CarCompetition car) {
		this.podium = new CarCompetition[3];
		car.addPunctuation(THIRD_PLACE_POINTS);
		this.podium[2] = car;
	}

	public void setSilver(CarCompetition car) {
		if (Objects.isNull(this.podium)) {
			this.podium = new CarCompetition[2];
		}
		car.addPunctuation(SECOND_PLACE_POINTS);
		this.podium[1] = car;
	}

	public void setGold(CarCompetition car) {
		if (Objects.isNull(this.podium)) {
			this.podium = new CarCompetition[1];
		}
		car.addPunctuation(FIRST_PLACE_POINTS);
		this.podium[0] = car;
	}
}
