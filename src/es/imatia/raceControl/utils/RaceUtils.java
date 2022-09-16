package es.imatia.raceControl.utils;

import java.util.HashMap;
import java.util.Objects;

import es.imatia.raceControl.objectsclasses.*;

public class RaceUtils {
	public static void raceMenu(HashMap<String, Race> raceList) {
		int option = -1;
		/*
		 * Menú Carreras: 1. Listar carreras 2. Ver detalle de carrera 4. Crear nueva
		 * carrera 5. Eliminar carrera 0. Volver a menú principal
		 */
		while (option != 0) {
			System.out.print(Utils.secondaryHeader("Carreras", '_'));

			System.out.print(("\n\t1. Listar carreras\n\t2. Ver detalle de carrera\n\t3. Crear nueva carrera"
					+ "\n\t4. Eliminar carrera\n\t0. Volver a menú principal\n"));

			Race actualRace = null;
			option = Utils.readPositiveInt("¿Que acción desea realizar?:");

			switch (option) {
			case 1:
				if (raceList.size() > 0) {
					showRaceList((Race[]) (raceList.values().toArray()));
				} else {
					System.out.print("\nTodavía no hay carreras\n");
				}
				break;
			case 2:
				if (raceList.size() > 0) {
					System.out.print(selectRace((Race[]) (raceList.values().toArray())).getDetails());
				} else {
					System.out.print("\nTodavía no hay carreras\n");
				}
				break;
			case 3:
				if (addRace(raceList)) {
					System.out.print("\nCarrera creada correctamente\n");
				} else {
					System.out.print("\nNo es posible completar la operación, ya existe una carrera con el nombre indicado\n");
				}
				break;
			case 4:
				if(raceList.size() > 0) {
					actualRace = selectRace((Race[]) (raceList.values().toArray()));
					if(!Objects.isNull(actualRace)) {
						raceList.remove(actualRace.getRaceName());
						System.out.print("\nCarrera eliminada correctamente\n");
					} else {
						System.out.print("\nLa carrera seleccionada no existe\n");
					}
				} else {
					System.out.print("\nTodavía no hay carreras\n");
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

	public static void showRaceList(Race[] raceList) {
		if (raceList.length > 0) {
			int raceCount = 0;
			for (Race race : raceList) {
				raceCount++;
				System.out.print("\n\t" + raceCount + race.getRaceName());
			}
		} else {
			System.out.print("\nTodavía no hay carreras para mostrar\n");
		}
	}

	public static Race selectRace(Race[] raceList) {
		Race selectedRace = null;
		showRaceList(raceList);
		int index = Utils.readPositiveInt("Seleccione el número de carrera:");
		if (index > 0 && index <= raceList.length) {
			selectedRace = raceList[index];
		}
		return selectedRace;
	}

	public static boolean addRace(HashMap<String, Race> raceList) {
		boolean added = false;
		String raceName = Utils.prettyPrint(Utils.readString("Indique el nombre de la carrera a crear:"));
		if(!raceList.containsKey(raceName)) {
			String type = " ";
			while(type.equalsIgnoreCase("eliminatoria") && type.equalsIgnoreCase("estandar")) {
				type = Utils.prettyPrint(Utils.readString("Indique el tipo de carrera (eliminatoria/estandar):"));
			}
			if(type.equalsIgnoreCase("eliminatoria")) {
				int previewsRounds = Utils.readPositiveInt("\nIndique el número de vueltas de calentamiento deseadas:");
				raceList.put(raceName, new EliminationRace(raceName, previewsRounds));
				added = true;
			} else {
				int duration = Utils.readPositiveInt("\nIndique ela duración de la carrera en minutos:");
				raceList.put(raceName, new StandardRace(raceName, duration));
				added = true;
			}
			
		}
		return added;
	}
	/*
	 * public static boolean addRace(Competition competition, HashMap<String,
	 * Garage> garageList) { boolean created = false; String raceName =
	 * Utils.prettyPrint(Utils.readString("Indique el nombre de la nueva carrera:"))
	 * ; if (!competition.getRaceList().containsKey(raceName)) { String moreGarage =
	 * "1"; HashMap<String, Garage> garageRaceList = new HashMap<String, Garage>();
	 * while (moreGarage.equals("1") || garageRaceList.isEmpty()) {
	 * System.out.print(GarageUtils.showGaragesList(garageList)); String garageKey =
	 * Utils .prettyPrint(Utils.
	 * readString("Indique el nombre de la escudería a incluír en la carrera:")); if
	 * (!garageRaceList.containsKey(garageKey) && garageList.containsKey(garageKey))
	 * { garageRaceList.put(garageKey, garageList.get(garageKey));
	 * System.out.print("\nEscudería añadida correctamente\n"); } else if
	 * (garageRaceList.containsKey(garageKey)) { System.out.
	 * print("\nLa escudería seleccionada ya se había añadido previamente\n"); }
	 * else { System.out.print("\nSeleccione una escudería válida\n"); } if
	 * (!garageRaceList.isEmpty()) { moreGarage = Utils .prettyPrint(Utils.
	 * readString("(pulse 1 para añadir más escuderías a la carrera)")); } } String
	 * type = Utils.prettyPrint("Indique el tipo (estandar/eliminatoria):"); if
	 * (type.equalsIgnoreCase("estandar")) { int duration; System.out.
	 * print("\n(en caso se indicar una duración inválida se tomará el valor por defecto ("
	 * + StandardRace.getDEFAULT_STANDARD_RACE_DURATION() + ")\n"); String
	 * durationString = Utils.readString("Indique la duración en minutos:").trim();
	 * if (durationString.matches("\\d*")) { duration =
	 * Integer.parseInt(durationString); } else { duration =
	 * StandardRace.getDEFAULT_STANDARD_RACE_DURATION(); } created =
	 * competition.addRace(new StandardRace(raceName, garageRaceList, duration)); }
	 * else if (type.equalsIgnoreCase("eliminatoria")) { int previews; System.out.
	 * print("(en caso se indicar una cantidad inválida se tomará el valor por defecto ("
	 * + EliminationRace.getDEFAULT_PREVIEWS_ROUNDS() + ")\n"); String
	 * durationString = Utils
	 * .readString("Indique la cantidad de rondas previas a la eliminación desea:").
	 * trim(); if (durationString.matches("\\d*")) { previews =
	 * Integer.parseInt(durationString); } else { previews =
	 * EliminationRace.getDEFAULT_PREVIEWS_ROUNDS(); } created =
	 * competition.addRace(new EliminationRace(raceName, garageRaceList, previews));
	 * } else { System.out.print("\nHa seleccionado una opción no válida\n"); } }
	 * return created; }
	 * 
	 * public static Race selectRace(HashMap<String, Competition> competitionList,
	 * String selectedCompt) { Race race = null; if (!Objects.isNull(selectedCompt))
	 * { String selectedRace = selectKeyRace(competitionList.get(selectedCompt)); if
	 * (!Objects.isNull(selectedRace)) { race =
	 * competitionList.get(selectedCompt).getRaceList().get(selectedRace); } }
	 * return race; }
	 * 
	 * public static Race selecPendingRace(HashMap<String, Competition>
	 * competitionList, String selectedCompt) { Race race = null; if
	 * (!Objects.isNull(selectedCompt)) { String selectedRace =
	 * selectKeyRace(competitionList.get(selectedCompt)); if
	 * (!Objects.isNull(selectedRace)) { race =
	 * competitionList.get(selectedCompt).getRaceList().get(selectedRace); } }
	 * return race; }
	 * 
	 * public static String showRaceDetails(HashMap<String, Competition>
	 * competitionList) { String raceDetails = ""; String selectedCompt =
	 * CompetitionUtils.selectKeyCompetition(competitionList); if
	 * (!Objects.isNull(selectedCompt)) { String selectedRace =
	 * selectKeyRace(competitionList.get(selectedCompt)); if
	 * (!Objects.isNull(selectedRace)) { raceDetails =
	 * competitionList.get(selectedCompt).getRaceList().get(selectedRace).
	 * showDetails(); } else { raceDetails = "\nCarrera seleccionada no válida\n"; }
	 * } else { raceDetails = "\nTorneo seleccionado no válido\n";
	 * 
	 * } return raceDetails; }
	 * 
	 * public static String selectKeyRace(Competition competition) { String
	 * selectedRace = null; if (!competition.getRaceList().isEmpty()) {
	 * ArrayList<Race> raceArray = new
	 * ArrayList<Race>(competition.getRaceList().values());
	 * System.out.print(showRaceList(raceArray)); int selected =
	 * Utils.readPositiveInt("Indique el número de carrera a seleccionar:"); if
	 * (selected > 0 && selected <= raceArray.size()) { selectedRace =
	 * raceArray.get(selected - 1).getRaceName(); } } return selectedRace; }
	 * 
	 * public static String selectUndoneKeyRace(Competition competition) { String
	 * selectedRace = null; if (!competition.getRaceList().isEmpty()) {
	 * ArrayList<Race> raceArray = new ArrayList<Race>(); for (Race race :
	 * competition.getRaceList().values()) { if (race.getRacePodium().equals(null))
	 * { raceArray.add(race); } } System.out.print(showRaceList(raceArray)); int
	 * selected =
	 * Utils.readPositiveInt("Indique el número de carrera a seleccionar:"); if
	 * (selected > 0 && selected <= raceArray.size()) { selectedRace =
	 * raceArray.get(selected - 1).getRaceName(); } } return selectedRace; }
	 * 
	 * public static String showRaceList(ArrayList<Race> raceList) { String
	 * raceString = "\n"; int raceCount = 0; if (raceList.size() > 0) { for (Race
	 * race : raceList) { raceCount += 1; raceString += "\t" + raceCount + ". " +
	 * race.getRaceName() + "\n"; } } else { raceString =
	 * "\nNo hay carreras que mostrar\n"; } return raceString; }
	 * 
	 * public static String showPendingRaceList(HashMap<String, Race> raceList) {
	 * String raceString = ""; if (!raceList.isEmpty()) { raceString +=
	 * "\n    Carreras pendientes:\n"; for (Race race : raceList.values()) { if
	 * (Objects.isNull(race.getRacePodium())) { raceString += "\t- " +
	 * race.getRaceName(); if (race instanceof StandardRace) { raceString +=
	 * " (Estándar)\n"; } else if (race instanceof EliminationRace) { raceString +=
	 * " (Eliminación)\n"; } } } } else { raceString +=
	 * "\nNo hay carreras para mostrar\n"; } return raceString; }
	 * 
	 * public static String showDoneRaceList(HashMap<String, Race> raceList) {
	 * String raceString = ""; if (!raceList.isEmpty()) { raceString +=
	 * "    Carreras realizadas:\n"; for (Race race : raceList.values()) { if
	 * (!Objects.isNull(race.getRacePodium())) { raceString += "\t- " +
	 * race.getRaceName(); if (race instanceof StandardRace) { raceString +=
	 * " (Estándar)\n"; } else if (race instanceof EliminationRace) { raceString +=
	 * " (Eliminación)\n"; } } } } else { raceString +=
	 * "\nNo hay carreras para mostrar\n"; } return raceString; }
	 * 
	 * public static String showTotalRaceList(HashMap<String, Competition>
	 * competitionList) { String raceString = "\nLista de Carreras por Torneo: \n";
	 * for (Competition competition : competitionList.values()) { if
	 * (!competition.getRaceList().isEmpty()) { raceString += "\n" +
	 * competition.getCompetitionName() + ": \n"; raceString +=
	 * showDoneRaceList(competition.getRaceList()); raceString +=
	 * showPendingRaceList(competition.getRaceList()); } } if
	 * (raceString.equals("\nLista de Carreras por Torneo: \n")) { raceString =
	 * "\nTodavía no hay carreras para mostrar\n"; } else { raceString +=
	 * "................................\n"; }
	 * 
	 * return raceString; }
	 * 
	 * public static String raceMenu() { return
	 * ("\n\t1. Listar carreras\n\t2. Ver detalle de carrera\n\t3. Empezar carrera"
	 * +
	 * "\n\t4. Añadir carrera a torneo\n\t5. Eliminar carrera (todavía no realizada) de torneo\n\t"
	 * + "0. Volver a menú principal\n"); }
	 */
}
