package es.imatia.raceControl.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import es.imatia.raceControl.objectsclasses.*;

public class CompetitionUtils {
	public static void competitionMenu(HashMap<String, Competition> competitionList) {
		int option = -1;
		Scanner sc = new Scanner(System.in);
		while (option != 0) {
			System.out.print(raceMenu());
			String selectedKey = null;
			option = Utils.readPositiveInt();
			switch (option) {
			case 1:
				String competitionName;
				int raceNumber = Competition.getDEFAULT_RACES();
				System.out.print("\nIndique el nombre del torneo: ");
				competitionName = Utils.prettyPrint(sc.nextLine());
				System.out.print("\n(en caso se indicar una cantidad inválida se tomará el valor por defecto ("
						+ Competition.getDEFAULT_RACES() + ")\n");
				System.out.print("\nIndique la cantidad de carreras que tendrá el torneo: ");
				String races = sc.nextLine().trim();
				if (races.matches("\\d+")) {
					raceNumber = Integer.parseInt(races);
				}
				if (!competitionList.containsKey(selectedKey)) {
					competitionList.put(competitionName, new Competition(competitionName, raceNumber));
					System.out.print("\nTorneo creado correctamente\n");
				} else {
					System.out.print("\nYa existe el torneo\n");
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
					if (!Objects.isNull(selectedKey)
							&& !Objects.isNull(competitionList.get(selectedKey).getCompetitionPodium())) {
						System.out.print(competitionList.get(selectedKey).getStringCompetitionPodium());
					} else {
						System.out.print("\nEl torneo seleccionado no existe o todavía no ha finalizado\n");
					}

				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}
				break;
			case 4:
				if (!competitionList.isEmpty()) {
					selectedKey = selectKeyCompetition(competitionList);
					if (!Objects.isNull(selectedKey)
							&& !competitionList.get(selectedKey).getCompetitionRanking().isEmpty()) {
						System.out.print(competitionList.get(selectedKey).getCompetitionRanking());
					} else {
						System.out.print("\nEl torneo seleccionado no existe o no tiene todavía ningún ránking\n");
					}

				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}
				break;
			case 5:
				if (!competitionList.isEmpty()) {
					selectedKey = selectKeyCompetition(competitionList);
					if (!Objects.isNull(selectedKey)) {
						System.out.print(competitionList.get(selectedKey).getStringCompetitionGarages());
					} else {
						System.out.print("\nEl torneo seleccionado no existe\n");
					}

				} else {
					System.out.print("\nTodavía no hay torneos\n");
				}
				break;
			case 6:
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
		System.out.print("\nIndique el número de torneo a seleccionar: ");
		int selected = Utils.readPositiveInt();
		if (selected > 0 && selected <= competitionArray.size()) {
			selectedCompt = competitionArray.get(selected - 1).getCompetitionName();
		}
		return selectedCompt;
	}

	public static String showCompetitionList(ArrayList<Competition> competitionArray) {
		String competitionString = "\nTorneos: \n";
		for (int i = 0; i < competitionArray.size(); i++) {
			competitionString += "\t" + (i + 1) + ". " + competitionArray.get(i).getCompetitionName() + "\n";
		}
		competitionString += "...................\n";
		return competitionString;
	}

	public static String raceMenu() {
		return ("\n\t1. Nuevo torneo\n\t2. Listar torneos\n\t3. Ver pódium de torneo\n\t4. Ver ránking de torneo\n\t5. Ver escuderías participantes"
				+ "\n\t6. Eliminar torneo\n\t0. Volver a menú principal\n¿Que acción desea realizar?: ");
	}
}
