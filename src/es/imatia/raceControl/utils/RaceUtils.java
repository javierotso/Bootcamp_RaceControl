package es.imatia.raceControl.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import es.imatia.raceControl.objectsclasses.*;

public class RaceUtils {
	public static void raceMenu(HashMap<String, Competition> competitionList, HashMap<String, Garage> garageList) {
		int option = -1;
		/*
		 * Menú Carreras: 1. Listar carreras 2. Ver detalle de carrera 3. Empezar
		 * carrera 4. Crear nueva carrera (asociada a torneo) 5. Eliminar carrera 6. Ver podium carrera 0.
		 * Volver a menú principal
		 */
		while (option != 0) {
			Competition actualCompetition = null;
			Race actualRace = null;
			String sel = null;
			System.out.print(Utils.secondaryHeader("Carreras", '_'));
			System.out.print(raceMenu());
			option = Utils.readPositiveInt();
			switch (option) {
			case 1:
				if (competitionList.size() > 0) {
					System.out.print(showTotalRaceList(competitionList));
				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}

				break;
			case 2:
				if (competitionList.size() > 0) {
					System.out.print(showRaceDetails(competitionList));
				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}
				break;
			case 3:
				if (competitionList.size() > 0) {
					String comptKey = CompetitionUtils.selectKeyCompetition(competitionList);
					actualCompetition = competitionList.get(comptKey);
					actualRace = selecPendingRace(competitionList, comptKey);
					if (!Objects.isNull(actualRace)) {
						if (Objects.isNull(actualRace.getRacePodium())) {
							System.out.print(actualRace.totalRace(actualCompetition));
						} else {
							System.out.print("\nLa carrera seleccionada ya se ha realizado\n");
						}
					} else {
						System.out.print("\nOpción seleccionada no válida\n");
					}
				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}

				break;
			case 4:
				if (competitionList.size() > 0 && garageList.size() > 0) {
					sel = CompetitionUtils.selectKeyCompetition(competitionList);
					if (!Objects.isNull(sel)) {
						actualCompetition = competitionList.get(sel);
						if (Objects.isNull(actualCompetition.getCompetitionPodium())) {
							if (addRace(competitionList.get(sel), garageList)) {
								System.out.print("\nCarrera añadida correctamente\n");
							} else {
								System.out.print("\nNo ha sido posible completar la operación\n");
							}
						} else {
							System.out.print("\nNo se pueden añadir carreras a una competición terminada\n");
						}
					} else {
						System.out.print("\nTorneo seleccionado no válida\n");
					}
				} else {
					System.out.print("\nTodavía no hay torneos o escuderías\n");
				}

				break;
			case 5:
				if (competitionList.size() > 0) {
					sel = CompetitionUtils.selectKeyCompetition(competitionList);
					if (!Objects.isNull(sel)) {
						String selectedRace = selectKeyRace(competitionList.get(sel));
						if (!Objects.isNull(selectedRace)) {
							actualRace = competitionList.get(sel).getRaceList().get(selectedRace);
							if (Objects.isNull(actualRace.getRacePodium())) {
								competitionList.get(sel).getRaceList().remove(selectedRace);
								System.out.print("\nCarrera eliminada correctamente\n");
							} else {
								System.out.print("\nNo es posible eliminar una carrera que ya se ha eliminado\n");
							}
						}
					} else {
						System.out.print("\nTorneo seleccionado no válida\n");
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

	public static boolean addRace(Competition competition, HashMap<String, Garage> garageList) {
		Scanner sc = new Scanner(System.in);
		boolean created = false;
		System.out.print("\nIndique el nombre de la nueva carrera: ");
		String raceName = Utils.prettyPrint(sc.nextLine());
		if (!competition.getRaceList().containsKey(raceName)) {
			int moreGarage = 1;
			HashMap<String, Garage> garageRaceList = new HashMap<String, Garage>();
			while (moreGarage == 1 || garageRaceList.isEmpty()) {
				System.out.print(GarageUtils.showGaragesList(garageList));
				System.out.print("\nIndique el nombre de la escudería a incluír en la carrera: ");
				String garageKey = Utils.prettyPrint(sc.nextLine());
				if (!garageRaceList.containsKey(garageKey) && garageList.containsKey(garageKey)) {
					garageRaceList.put(garageKey, garageList.get(garageKey));
					System.out.print("\nEscudería añadida correctamente\n");
				} else if (garageRaceList.containsKey(garageKey)) {
					System.out.print("\nLa escudería seleccionada ya se había añadido previamente\n");
				} else {
					System.out.print("\nSeleccione una escudería válida\n");
				}
				if (!garageRaceList.isEmpty()) {
					System.out.print("(pulse 1 para añadir más escuderías a la carrera)");
					moreGarage = Utils.readPositiveInt();
				}
			}
			System.out.print("\nIndique el tipo (estandar/eliminatoria): ");
			String type = Utils.prettyPrint(sc.nextLine());
			if (type.equalsIgnoreCase("estandar")) {
				int duration;
				System.out.print("\n(en caso se indicar una duración inválida se tomará el valor por defecto ("
						+ StandardRace.getDEFAULT_STANDARD_RACE_DURATION() + ")\n");
				System.out.print("\nIndique la duración en minutos: ");
				String durationString = sc.nextLine().trim();
				if (durationString.matches("\\d*")) {
					duration = Integer.parseInt(durationString);
				} else {
					duration = StandardRace.getDEFAULT_STANDARD_RACE_DURATION();
				}
				created = competition.addRace(new StandardRace(raceName, garageRaceList, duration));
			} else if (type.equalsIgnoreCase("eliminatoria")) {
				int previews;
				System.out.print("\n(en caso se indicar una cantidad inválida se tomará el valor por defecto ("
						+ EliminationRace.getDEFAULT_PREVIEWS_ROUNDS() + ")\n");
				System.out.print("\nIndique la cantidad de rondas previas a la eliminación desea: ");
				String durationString = sc.nextLine().trim();
				if (durationString.matches("\\d*")) {
					previews = Integer.parseInt(durationString);
				} else {
					previews = EliminationRace.getDEFAULT_PREVIEWS_ROUNDS();
				}
				created = competition.addRace(new EliminationRace(raceName, garageRaceList, previews));
			} else {
				System.out.print("\nHa seleccionado una opción no válida\n");
			}
		}
		return created;
	}

	public static Race selectRace(HashMap<String, Competition> competitionList, String selectedCompt) {
		Race race = null;
		if (!Objects.isNull(selectedCompt)) {
			String selectedRace = selectKeyRace(competitionList.get(selectedCompt));
			if (!Objects.isNull(selectedRace)) {
				race = competitionList.get(selectedCompt).getRaceList().get(selectedRace);
			}
		}
		return race;
	}
	
	public static Race selecPendingRace(HashMap<String, Competition> competitionList, String selectedCompt) {
		Race race = null;
		if (!Objects.isNull(selectedCompt)) {
			String selectedRace = selectKeyRace(competitionList.get(selectedCompt));
			if (!Objects.isNull(selectedRace)) {
				race = competitionList.get(selectedCompt).getRaceList().get(selectedRace);
			}
		}
		return race;
	}


	public static String showRaceDetails(HashMap<String, Competition> competitionList) {
		String raceDetails = "";
		String selectedCompt = CompetitionUtils.selectKeyCompetition(competitionList);
		if (!Objects.isNull(selectedCompt)) {
			String selectedRace = selectKeyRace(competitionList.get(selectedCompt));
			if (!Objects.isNull(selectedRace)) {
				raceDetails = competitionList.get(selectedCompt).getRaceList().get(selectedRace).showDetails();
			} else {
				raceDetails = "\nCarrera seleccionada no válida\n";
			}
		} else {
			raceDetails = "\nTorneo seleccionado no válido\n";

		}
		return raceDetails;
	}

	public static String selectKeyRace(Competition competition) {
		String selectedRace = null;
		if (!competition.getRaceList().isEmpty()) {
			ArrayList<Race> raceArray = new ArrayList<Race>(competition.getRaceList().values());
			System.out.print(showRaceList(raceArray));
			System.out.print("Indique el número de carrera a seleccionar: ");
			int selected = Utils.readPositiveInt();
			if (selected > 0 && selected <= raceArray.size()) {
				selectedRace = raceArray.get(selected - 1).getRaceName();
			}
		}
		return selectedRace;
	}
	
	public static String selectUndoneKeyRace(Competition competition) {
		String selectedRace = null;
		if (!competition.getRaceList().isEmpty()) {
			ArrayList<Race> raceArray = new ArrayList<Race>();
			for(Race race : competition.getRaceList().values()) {
				if(race.getRacePodium().equals(null)) {
					raceArray.add(race);
				}
			}
			System.out.print(showRaceList(raceArray));
			System.out.print("Indique el número de carrera a seleccionar: ");
			int selected = Utils.readPositiveInt();
			if (selected > 0 && selected <= raceArray.size()) {
				selectedRace = raceArray.get(selected - 1).getRaceName();
			}
		}
		return selectedRace;
	}

	public static String showRaceList(ArrayList<Race> raceList) {
		String raceString = "\n";
		int raceCount = 0;
		if (raceList.size() > 0) {
			for (Race race : raceList) {
				raceCount += 1;
				raceString += "\t" + raceCount + ". " + race.getRaceName() + "\n";
			}
		} else {
			raceString = "\nNo hay carreras que mostrar\n";
		}
		return raceString;
	}

	public static String showPendingRaceList(HashMap<String, Race> raceList) {
		String raceString = "";
		if (!raceList.isEmpty()) {
			raceString += "\n    Carreras pendientes:\n";
			for (Race race : raceList.values()) {
				if (Objects.isNull(race.getRacePodium())) {
					raceString += "\t- " + race.getRaceName();
					if (race instanceof StandardRace) {
						raceString += " (Estándar)\n";
					} else if (race instanceof EliminationRace) {
						raceString += " (Eliminación)\n";
					}
				}
			}
		} else {
			raceString += "\nNo hay carreras para mostrar\n";
		}
		return raceString;
	}

	public static String showDoneRaceList(HashMap<String, Race> raceList) {
		String raceString = "";
		if (!raceList.isEmpty()) {
			raceString += "    Carreras realizadas:\n";
			for (Race race : raceList.values()) {
				if (!Objects.isNull(race.getRacePodium())) {
					raceString += "\t- " + race.getRaceName();
					if (race instanceof StandardRace) {
						raceString += " (Estándar)\n";
					} else if (race instanceof EliminationRace) {
						raceString += " (Eliminación)\n";
					}
				}
			}
		} else {
			raceString += "\nNo hay carreras para mostrar\n";
		}
		return raceString;
	}

	public static String showTotalRaceList(HashMap<String, Competition> competitionList) {
		String raceString = "\nLista de Carreras por Torneo: \n";
		for (Competition competition : competitionList.values()) {
			if (!competition.getRaceList().isEmpty()) {
				raceString += "\n" + competition.getCompetitionName() + ": \n";
				raceString += showDoneRaceList(competition.getRaceList());
				raceString += showPendingRaceList(competition.getRaceList());
			}
		}
		if (raceString.equals("\nLista de Carreras por Torneo: \n")) {
			raceString = "\nTodavía no hay carreras para mostrar\n";
		} else {
			raceString += "................................\n";
		}

		return raceString;
	}

	public static String raceMenu() {
		return ("\n\t1. Listar carreras\n\t2. Ver detalle de carrera\n\t3. Empezar carrera"
				+ "\n\t4. Añadir carrera a torneo\n\t5. Eliminar carrera (todavía no realizada) de torneo\n\t"
				+ "0. Volver a menú principal\n" + "¿Que acción desea realizar?: ");
	}
}
