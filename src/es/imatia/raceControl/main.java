package es.imatia.raceControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import es.imatia.raceControl.objectsclasses.*;
import es.imatia.raceControl.utils.*;

public class main {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		HashMap<String, Garage> garageList = new HashMap<String, Garage>();
		HashMap<String, Competition> competitionList = new HashMap<String, Competition>();

		File dataFile = new File("data.bin");
		if (dataFile.exists()) {
			FileInputStream fis = new FileInputStream(dataFile);
			ObjectInputStream ois = new ObjectInputStream(fis);
			garageList = (HashMap<String, Garage>) ois.readObject();
			competitionList = (HashMap<String, Competition>) ois.readObject();
			ois.close();
		}

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
		if (dataFile.exists()) {
			dataFile.delete();
		}
		FileOutputStream fos = new FileOutputStream(dataFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(garageList);
		oos.writeObject(competitionList);
		oos.close();

		/* En caso de perder los datos: descomentar esto: */
		/*
		 * Garage escuderia1 = new Garage("Escuderia 1"); Garage escuderia2 = new
		 * Garage("Escuderia 2");
		 * 
		 * Car c1e1 = new Car("Coche", "1", escuderia1); Car c2e1 = new Car("Coche",
		 * "2", escuderia1); Car c3e1 = new Car("Coche", "3", escuderia1); Car c4e1 =
		 * new Car("Coche", "4", escuderia1); Car c1e2 = new Car("Coche", "1",
		 * escuderia2); Car c2e2 = new Car("Coche", "2", escuderia2); Car c3e2 = new
		 * Car("Coche", "3", escuderia2); Car c4e2 = new Car("Coche", "4", escuderia2);
		 * Car c5e2 = new Car("Coche", "5", escuderia2);
		 * 
		 * escuderia1.addCar(c1e1); escuderia1.addCar(c2e1); escuderia1.addCar(c3e1);
		 * escuderia1.addCar(c4e1);
		 * 
		 * escuderia2.addCar(c1e2); escuderia2.addCar(c2e2); escuderia2.addCar(c3e2);
		 * escuderia2.addCar(c4e2); escuderia2.addCar(c5e2);
		 * 
		 * garageList.put(escuderia1.getGarageName(), escuderia1);
		 * garageList.put(escuderia2.getGarageName(), escuderia2);
		 * 
		 * Competition torneo1 = new Competition("Torneo 1", 3);
		 * competitionList.put(torneo1.getCompetitionName(), torneo1);
		 * 
		 * Race c1t1 = new StandardRace("Carrera 1", escuderia2); Race c2t1 = new
		 * EliminationRace("Carrera 2", escuderia1);
		 * 
		 * Race c3t1 = new StandardRace("Carrera 3", garageList);
		 * 
		 * torneo1.addRace(c3t1); torneo1.addRace(c1t1); torneo1.addRace(c2t1);
		 */
	}
}