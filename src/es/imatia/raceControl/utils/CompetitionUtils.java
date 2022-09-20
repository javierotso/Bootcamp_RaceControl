package es.imatia.raceControl.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import es.imatia.raceControl.objectsclasses.*;

public class CompetitionUtils {
	public static void competitionMenu(HashMap<String, Competition> competitionList, HashMap<String, Race> raceList,
			HashMap<String, Garage> garageList) {
		int option = -1;
		while (option != 0) {
			System.out.print(Utils.secondaryHeader("Torneos", '_'));
			System.out.print(("\n\t1. Nuevo torneo\n\t2. Listar torneos"
					+ "\n\t3. Eliminar torneo\n\t4. Ir a torneo\n\t0. Volver a menú principal"));
			String selectedKey = null;
			option = Utils.readPositiveInt("¿Que acción desea realizar?:");
			switch (option) {
			case 1:
				String competitionName = Utils.prettyPrint(Utils.readString("Indique el nombre del torneo:"));
				Competition competition = new Competition(competitionName);
				if (!competitionList.containsKey(competitionName)) {
					addRace(competition, raceList);
					competitionList.put(competition.getCompetitionName(), competition);
					System.out.print("\nTorneo añadido correctamente\n");
				} else {
					System.out.print("\nYa existe un torneo con ese nombre en nuestra base de datos\n");
				}
				break;
			case 2:
				if (!competitionList.isEmpty()) {
					System.out.print(showCompetitionList(new ArrayList<Competition>(competitionList.values())));
				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}
				break;
			case 3:
				if (!competitionList.isEmpty()) {
					selectedKey = selectKeyCompetition(competitionList);
					if (!Objects.isNull(selectedKey)) {
						competitionList.remove(selectedKey);
						System.out.print("\nSe ha eliminado el torneo correctamente\n");
					} else {
						System.out.print("\nEl torneo seleccionado no existe\n");
					}

				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}
				break;
			case 4:
				if (!competitionList.isEmpty()) {
					selectedKey = selectKeyCompetition(competitionList);
					if (!Objects.isNull(selectedKey)) {
						competitionSubMenu(competitionList.get(selectedKey), raceList, garageList);
					} else {
						System.out.print("\nEl torneo seleccionado no existe\n");
					}
				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}
				break;
			case 0:
				System.out.print("\n.... Volviendo al menú principal ....\n");
				break;
			default:
				System.out.print("\nLa opción seleccionada no está disponible todavía\n");
			}

		}
	}

	public static String selectKeyCompetition(HashMap<String, Competition> competitionList) {
		String selectedCompt = null;
		ArrayList<Competition> competitionArray = new ArrayList<Competition>(competitionList.values());
		System.out.print(showCompetitionList(competitionArray));
		int selected = Utils.readPositiveInt("Indique el número de torneo a seleccionar:");
		if (selected > 0 && selected <= competitionArray.size()) {
			selectedCompt = competitionArray.get(selected - 1).getCompetitionName();
		}
		return selectedCompt;
	}

	public static String showCompetitionList(ArrayList<Competition> competitionArray) {
		String competitionString = "\nTorneos: \n";
		for (int i = 0; i < competitionArray.size(); i++) {
			competitionString += "\t" + (i + 1) + ". " + competitionArray.get(i).getCompetitionName();
			if (competitionArray.get(i).getDoneRaces() == competitionArray.get(i).getRaceList().size()) {
				competitionString += " <FINALIZADO>";
			}
			competitionString += "\n";
		}
		competitionString += "...................\n";
		return competitionString;
	}

	public static void addRace(Competition competition, HashMap<String, Race> raceList) {
		String next = "s";
		while (next.equalsIgnoreCase("s") || competition.getRaceList().isEmpty()) {
			System.out.print("\n...Vamos a añadir carreras al torneo (debe tener una como mínimo)...\n");
			Race race = RaceUtils.selectRace(RaceUtils.raceToArray(raceList));
			if (!Objects.isNull(race)) {
				if (competition.addRace(race)) {
					System.out.print("\nCarrera añadida correctamente\n");
				} else {
					System.out.print("\nLa carrera ya se había añdido previamente\n");
				}
			} else {
				System.out.print("\nNo ha seleccionado un carrera válida\n");
			}

			next = Utils.readString("¿Quiere añadir otra carrera? (s para continuar):");
		}
	}

	public static void addRaceGarage(Race race, HashMap<String, Garage> garageList) {
		boolean moreGarage = true;
		if (!Utils.readString("\nIndique 'si' si desea añadir todas las escuderías disponibles: ").trim()
				.equalsIgnoreCase("si")) {
			while (moreGarage || race.getGarageList().isEmpty()) {
				System.out.print("\n... Escuderías disponibles para participar ...\n");
				Garage selected = GarageUtils.selectGarage(garageList);
				if (selected != null && !race.getGarageList().containsKey(selected.getGarageName())) {
					race.addGarage(selected);
					System.out.print("\nEscudería añadida correctamente");
				} else {
					System.out.print("\nLa escudería ya se había añadido previamente");
				}
				if (!race.getGarageList().isEmpty()) {
					moreGarage = Utils
							.prettyPrint(Utils.readString("\n¿Desea añadir otra escudería? (s para continuar): "))
							.equalsIgnoreCase("s");
				} else {
					System.out.print("\nDebe añadir al menos una escudería válida\n");
				}
			}
		} else {
			for (Garage garage : garageList.values()) {
				race.addGarage(garage);
			}
		}
	}

	public static void competitionSubMenu(Competition competition, HashMap<String, Race> raceList,
			HashMap<String, Garage> garageList) {
		int option = -1;
		while (option != 0) {
			Race selectedRace = null;
			System.out.print(Utils.secondaryHeader(competition.getCompetitionName(), '*'));
			System.out.print("\n\t1. Ver ránking\n\t2. Ver pódium de carrera\n\t3. Ver escuderías participantes");
			if (!Objects.isNull(competition.getCompetitionPodium())) {
				System.out.print("\n\t4. Ver pódium del torneo");
			} else {
				System.out.print("\n\t4. Añadir carrera a torneo\n\t5. Empezar carrera\n\t6. Eliminar carrera");
			}
			System.out.print("\n\t0. Volver\n");
			option = Utils.readPositiveInt("¿Que acción desea realizar?:");
			switch (option) {
			case 1:
				if (!competition.getCarList().isEmpty()) {
					System.out.print(competition.getCompetitionRanking());
				} else {
					System.out.print("\nTodavía no hay participantes inscritos en este torneo\n");
				}
				break;
			case 2:
				if (!competition.getRaceList().isEmpty()) {
					selectedRace = RaceUtils.selectRace(RaceUtils.raceToArray(competition.getRaceList()));
					if (!Objects.isNull(selectedRace)) {
						if (selectedRace.getRacePodium() != null) {
							System.out.print(selectedRace.podiumToString());
						} else {
							System.out.print("\nTodavía no se ha realizado la carrera\n");
						}

					}
				} else {
					System.out.print("\nEste torneo todavía no tiene ninguna carrera\n");
				}
				break;
			case 3:
				if(!competition.getGarageList().isEmpty()) {
					System.out.print(GarageUtils.showGaragesList(competition.getGarageList()));
				} else {
					System.out.print("\nEste torneo todavía no tiene ninguna escudería inscrita\n");
				}
				break;
			case 4:
				if (!Objects.isNull(competition.getCompetitionPodium())) {
					System.out.print(competition.getStringCompetitionPodium());
				} else {
					addRace(competition, raceList);
				}

				break;
			case 5:
				if (Objects.isNull(competition.getCompetitionPodium())) {
					if (!competition.getRaceList().isEmpty() && !garageList.isEmpty()) {
						selectedRace = RaceUtils.selectRace(RaceUtils.raceToArray(competition.getRaceList()));
						if (!Objects.isNull(selectedRace)) {
							if (selectedRace.getRacePodium() == null) {
								addRaceGarage(selectedRace, garageList);
								competition.addCarList(selectedRace.getCarList(), garageList);
								System.out.print(selectedRace.totalRace());
								competition.addDoneRace();
								competition.setCompetitionPodium();
							} else {
								System.out.println("\nLa carrera seleccionada ya se ha realizado");
							}
						} else {
							System.out.println("\nOpción seleccionada no válida");
						}
					} else {
						System.out.print("\nEste torneo todavía no tiene ninguna carrera");
					}
				}
				break;

			case 6:
				if (Objects.isNull(competition.getCompetitionPodium())) {
					if (competition
							.deleteRace(RaceUtils.selectRace(RaceUtils.raceToArray(competition.getRaceList())))) {
						System.out.print("\nCarrera eliminada correctamente\n");
					} else {
						System.out.print("\nLa carrera ya se ha realizado, no es posible eliminarla\n");
					}
				}
			case 0:
				System.out.print("\n.... Volviendo a torneos ....\n");
				break;
			default:
				System.out.print("\nLa opción seleccionada no está disponible todavía\n");
			}
		}
	}
}
