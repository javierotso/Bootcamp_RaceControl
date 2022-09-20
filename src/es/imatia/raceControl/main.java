package es.imatia.raceControl;

import java.io.File;
import java.util.HashMap;
import es.imatia.raceControl.objectsclasses.*;
import es.imatia.raceControl.utils.*;

public class main {
	public static void main(String[] args) {

		final String FILE_PATH = "raceControlData.xml";

		HashMap<String, Garage> garageList = new HashMap<String, Garage>();
		HashMap<String, Race> raceList = new HashMap<String, Race>();
		HashMap<String, Competition> competitionList = new HashMap<String, Competition>();
		int option = -1;

	/*	File file = new File(FILE_PATH);
		if (file.exists()) {
			DataUtils.importXMLFile(garageList, raceList, competitionList, file);
		} else {
			if (!Utils
					.readString(
							"\nNo se ha encontrado fichero de datos, ¿desea continuar sin datos? (s para continuar): ")
					.equalsIgnoreCase("s")) {
				option = 0;
				System.out.print("\n........... S A L I E N D O ...........\n");
			}
			// Utils.startTask(garageList, raceList, competitionList);
		}
*/
		while (option != 0) {
			System.out.print(Utils.principalHeader("BIENVENIDO", '#'));
			System.out.print(Utils.principalMenu());
			option = Utils.readPositiveInt("¿Que acción desea realizar?:");
			switch (option) {
			case 1:
				CompetitionUtils.competitionMenu(competitionList, raceList, garageList);
				break;
			case 2:
				GarageUtils.garageMenu(garageList);
				break;
			case 3:
				RaceUtils.raceMenu(raceList);
				break;
			case 0:
				System.out.print("\n........... S A L I E N D O ...........\n");
				break;
			default:
				System.out.print("\nLa opción seleccionada no está disponible todavía\n");

			}
		}
	//	DataUtils.exportXMLFile(garageList, raceList, competitionList, file);
	}

}