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
					showRaceList(raceToArray(raceList));
				} else {
					System.out.print("\nTodavía no hay carreras\n");
				}
				break;
			case 2:
				if (raceList.size() > 0) {
					System.out.print(selectRace(raceToArray(raceList)).getDetails());
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
					actualRace = selectRace(raceToArray(raceList));
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

	private static void showRaceList(Race[] raceList) {
		if (raceList.length > 0) {
			int raceCount = 0;
			for (Race race : raceList) {
				raceCount++;
				System.out.print("\n\t" + raceCount + ". " + race.getRaceName());
				if(race instanceof EliminationRace) {
					System.out.print(" (Eliminatoria con " + ((EliminationRace)race).getPreviewsRounds() + " rondas de calentamiento)");
				} else if(race instanceof StandardRace) {
					System.out.print(" (Estándar con una duración de " + ((StandardRace)race).getDuration() + " minutos)");

				}
				if(race.getRacePodium() != null) {
					System.out.print("  <REALIZADA>   ");
				}
			}
			System.out.print("\n");
		} else {
			System.out.print("\nTodavía no hay carreras para mostrar\n");
		}
	}

	public static Race selectRace(Race[] raceList) {
		Race selectedRace = null;
		showRaceList(raceList);
		int index = Utils.readPositiveInt("Seleccione el número de carrera:");
		if (index > 0 && index <= raceList.length) {
			selectedRace = raceList[index - 1];
		}
		return selectedRace;
	}

	private static boolean addRace(HashMap<String, Race> raceList) {
		boolean added = false;
		String raceName = Utils.prettyPrint(Utils.readString("Indique el nombre de la carrera a crear:"));
		if(!raceList.containsKey(raceName)) {
			String type = " ";
			while(!type.equalsIgnoreCase("eliminatoria") && !type.equalsIgnoreCase("estandar")) {
				type = Utils.prettyPrint(Utils.readString("Indique el tipo de carrera (eliminatoria/estandar):"));
			}
			if(type.equalsIgnoreCase("eliminatoria")) {
				int previewsRounds = Utils.readPositiveInt("Indique el número de vueltas de calentamiento deseadas:");
				raceList.put(raceName, new EliminationRace(raceName, previewsRounds));
				added = true;
			} else {
				int duration = Utils.readPositiveInt("Indique ela duración de la carrera en minutos:");
				raceList.put(raceName, new StandardRace(raceName, duration));
				added = true;
			}
			
		}
		return added;
	}
	
	public static Race[] raceToArray(HashMap<String,Race> raceList) {
		Race[] raceArray = new Race[raceList.size()];
		int i = 0;
		for(Race race : raceList.values()) {
			raceArray[i++] = race;
		}
		return raceArray;
	}
}
