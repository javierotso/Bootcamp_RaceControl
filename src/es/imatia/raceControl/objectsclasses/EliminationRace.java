package es.imatia.raceControl.objectsclasses;

import java.util.ArrayList;

public class EliminationRace extends Race {

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
		return this.getCarList();
	}

	public String getDetails() {

		return ("\nCarrera: " + this.getRaceName() + "\n   Tipo: Eliminatoria" + "\n   Rondas de calentamiento: "
				+ this.getPreviewsRounds() + "\n");
	}

	public String totalRace() {
		String ranking = "\nCARRERA " + this.getRaceName().toUpperCase() + ":\n";
		int roundCount = 0;
		while (this.getCarList().size() - roundCount > 0) {
			this.raceRound();
			this.sortCarByDistance(0, this.getCarList().size() - roundCount);
			int carPosition = 0;
			if (this.getCarList().size() - roundCount > 3) {
				ranking += "\nVuelta " + (roundCount + 1) + "\n";
				for (int i = 0; i < this.getCarList().size() - roundCount; i++) {

					CarCompetition car = this.getCarList().get(i);
					if (i != (getCarList().size() - roundCount - 1)) {
						carPosition += 1;
						ranking += "\n\t" + carPosition + ". " + car.getCar().getCarName() + " ("
								+ car.getCar().getGarageName() + ")";
					} else {
						car.resetDistance();
						car.getCar().setCarSpeed(0);
						ranking += "\nELIMINADO: " + car.getCar().getCarName() + " (" + car.getCar().getGarageName()
								+ ")\n";
					}
				}
			}

			if (this.getCarList().size() - roundCount == 3) {
				ranking += "\nPRE FINAL ROUND: ";
				ranking += "\n\t1. " + this.getCarList().get(0).getCar().getCarName() + " ("
						+ this.getCarList().get(0).getCar().getGarageName() + ")\n";
				ranking += "\t2. " + this.getCarList().get(1).getCar().getCarName() + " ("
						+ this.getCarList().get(1).getCar().getGarageName() + ")";

				this.getCarList().get(2).resetDistance();
				this.getCarList().get(2).getCar().setCarSpeed(0);

				ranking += "\n BRONZE: " + this.getCarList().get(2).getCar().getCarName() + " ("
						+ this.getCarList().get(2).getCar().getGarageName() + ")\n";
			}

			if (this.getCarList().size() - roundCount == 2) {
				ranking += "\nFINAL ROUND: ";
				ranking += "\n GOLD:. " + this.getCarList().get(0).getCar().getCarName() + " ("
						+ this.getCarList().get(0).getCar().getGarageName() + ")";
				ranking += "\n SILVER: " + this.getCarList().get(1).getCar().getCarName() + " ("
						+ this.getCarList().get(1).getCar().getGarageName() + ")";
				this.getCarList().get(1).resetDistance();
				this.getCarList().get(1).getCar().setCarSpeed(0);
				this.getCarList().get(0).resetDistance();
				this.getCarList().get(0).getCar().setCarSpeed(0);
			}

			if (this.getCarList().size() - roundCount == 1) {
				ranking += "\n\nFINAL: ";
				ranking += "\n GOLD:. " + this.getCarList().get(0).getCar().getCarName() + " ("
						+ this.getCarList().get(0).getCar().getGarageName() + ")\n";
				this.getCarList().get(0).resetDistance();
				this.getCarList().get(0).getCar().setCarSpeed(0);
			}
			roundCount += 1;

		}
		ArrayList<CarCompetition> podium = new ArrayList<CarCompetition>();
		for(int i = 0; i < 3 && i < this.getCarList().size(); i++) {
			podium.add(this.getCarList().get(i));
		}
		this.setRacePodium(podium, true);

		return ranking;
	}
}
