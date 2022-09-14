package es.imatia.raceControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import es.imatia.raceControl.objectsclasses.*;
import es.imatia.raceControl.utils.*;

public class main {
	public static void main(String[] args) {
		HashMap<String, Garage> garageList = new HashMap<String, Garage>();
		HashMap<String, Competition> competitionList = new HashMap<String, Competition>();
/*
		File file = new File("data.bin");
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fileInput);
			boolean nextObjt = true;
			while (nextObjt) {
				Object o1 = ois.readObject();
				if (o1 != null) {
					nextObjt = false;
				} else if (o1.getClass().equals(garageList.getClass())) {
					garageList = (HashMap<String, Garage>) o1;
				} else if (o1.getClass().equals(competitionList.getClass())) {
					competitionList = (HashMap<String, Competition>) o1;
				}
			}
			fileInput.close();
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.print("\nNo se ha encontrado fichero de datos, empezamos el programa sin datos\n");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}*/
		/*
		 * Menú Carreras: 1. Listar carreras 2. Ver detalle de carrera 3. Empezar
		 * carrera 4. Crear nueva carrera (asociada a torneo) 5. Eliminar carrera 6.
		 * Editar carrera 0. Volver a menú principal
		 *
		 * Menú Torneos: 1. Ir a torneos finalizados 2. Ir a torneos activos 0. Volver a
		 * menú principal
		 * 
		 * Finalizados -> 1. Listar 2. Listar ranking 3. Ver podium 0. Volver Activos ->
		 * 1. Listar 2. Ver ranking 3. Ver podium 0. Volver
		 */
		int option = -1;

		while (option != 0) {
			System.out.print(Utils.principalHeader("BIENVENIDO", '#'));
			System.out.print(Utils.principalMenu());
			option = Utils.readPositiveInt();
			switch (option) {
			case 1:
				CompetitionUtils.competitionMenu(competitionList);
				break;
			case 2:
				GarageUtils.garageMenu(garageList);
				break;
			case 3:
				RaceUtils.raceMenu(competitionList, garageList);
				break;
			case 0:
				System.out.print("\n........... S A L I E N D O ...........\n");
				break;
			default:
				System.out.print("\nLa opción seleccionada no está disponible todavía\n");

			}
		}
/*
		try {
			FileOutputStream fileOutput = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fileOutput);
			oos.writeObject(garageList);
			oos.writeObject(competitionList);
			fileOutput.close();
			oos.close();
		} catch (FileNotFoundException e) {
			System.out.print("\nNo se ha encontrado fichero de datos, empezamos el programa sin datos\n");
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}*/

	}

}
