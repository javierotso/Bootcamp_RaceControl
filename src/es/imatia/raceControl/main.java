package es.imatia.raceControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Random;

import es.imatia.raceControl.objectsclasses.*;
import es.imatia.raceControl.utils.*;

public class main {
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		HashMap<String, Garage> garageList = new HashMap<String, Garage>();
		HashMap<String, Race> raceList = new HashMap<String, Race>();
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
			option = Utils.readPositiveInt("¿Que acción desea realizar?:");
			switch (option) {
			case 1:
				CompetitionUtils.competitionMenu(competitionList, raceList);
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
		if (dataFile.exists()) {
			dataFile.delete();
		}
		
		Garage escuderia = new Garage("Earnhardt Ganassi Racing");
		
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("David", "Adger", escuderia));
		escuderia.addCar(new Car("Arti", "Agrawal", escuderia));
		escuderia.addCar(new Car("Jack", "Andraka", escuderia));
		escuderia.addCar(new Car("Jacob", "Appelbaum", escuderia));
		escuderia.addCar(new Car("Polly", "Arnold", escuderia));
		
		escuderia = new Garage("Front Row Motorsports");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Cynthia", "Bauerle", escuderia));
		escuderia.addCar(new Car("Jesse", "Bering", escuderia));
		escuderia.addCar(new Car("Juani", "Bermejo Vega", escuderia));
		escuderia.addCar(new Car("Stefan", "Bond", escuderia));
		escuderia.addCar(new Car("Alex", "Bond", escuderia));
		
		escuderia = new Garage("Furniture Row Racing");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Ben", "Britton", escuderia));
		escuderia.addCar(new Car("Wendy", "Brown", escuderia));
		escuderia.addCar(new Car("Ron", "Buckmire", escuderia));
		escuderia.addCar(new Car("Ernst", "Buchard", escuderia));
		escuderia.addCar(new Car("Andrew", "Chael", escuderia));
		
		escuderia = new Garage("Germain Racing");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Angela", "Clayton", escuderia));
		escuderia.addCar(new Car("Alfredo", "Corell", escuderia));
		escuderia.addCar(new Car("Angelique", "Corthals", escuderia));
		escuderia.addCar(new Car("Allan", "Verne Cox", escuderia));
		escuderia.addCar(new Car("Kate", "Craig-Wood", escuderia));
		
		escuderia = new Garage("Hendrick Motorsports");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Martin", "Dannecker", escuderia));
		escuderia.addCar(new Car("Sonali", "Deraniyagala", escuderia));
		escuderia.addCar(new Car("Neil", "Divine", escuderia));
		escuderia.addCar(new Car("Danielle", "Dixson", escuderia));
		escuderia.addCar(new Car("Lauren", "Esposito", escuderia));
		
		escuderia = new Garage("Joe Gibbs Racing");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Francesc", "Gascó", escuderia));
		escuderia.addCar(new Car("Ruth", "Gates", escuderia));
		escuderia.addCar(new Car("Manuel", "González García", escuderia));
		escuderia.addCar(new Car("Lisa", "Graumlich", escuderia));
		escuderia.addCar(new Car("Magnus", "Hirschfeld", escuderia));
		
		escuderia = new Garage("JTG Daugherty Racing");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("James David", "Jentsch", escuderia));
		escuderia.addCar(new Car("Sophia", "Jex-Blake", escuderia));
		escuderia.addCar(new Car("Ferdinand", "Karsch-Haack", escuderia));
		escuderia.addCar(new Car("John", "Maynard Keynes", escuderia));
		escuderia.addCar(new Car("Alfred Charles", "Kinsey", escuderia));
		
		escuderia = new Garage("Michael Waltrip Racing");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Fritz", "Klein", escuderia));
		escuderia.addCar(new Car("Sofia", "Kovalévskaya", escuderia));
		escuderia.addCar(new Car("Peter", "Landin", escuderia));
		escuderia.addCar(new Car("Marina", "Logares Jiménez", escuderia));
		escuderia.addCar(new Car("Eduardo", "López Collazo", escuderia));
		
		escuderia = new Garage("NEMCO Motorsports");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Katie", "Mack", escuderia));
		escuderia.addCar(new Car("Anson William", "Mackay", escuderia));
		escuderia.addCar(new Car("Nate", "Silver", escuderia));
		escuderia.addCar(new Car("Esther", "Mayoko Ortega", escuderia));
		escuderia.addCar(new Car("José Ignacio", "Pichardo", escuderia));
		
		escuderia = new Garage("Penske Racing");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Alexandre", "Quintanilha", escuderia));
		escuderia.addCar(new Car("Pío del Río", "Hortega", escuderia));
		escuderia.addCar(new Car("Susana", "Rodríguez Navarro", escuderia));
		escuderia.addCar(new Car("Carmen", "Romero Bachiller", escuderia));
		escuderia.addCar(new Car("Oliver", "Sacks", escuderia));
		
		escuderia = new Garage("Phoenix Racing");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Sam", "Giles", escuderia));
		escuderia.addCar(new Car("Julia", "Serrano", escuderia));
		escuderia.addCar(new Car("Christopher", "Strachey", escuderia));
		escuderia.addCar(new Car("Gracia", "Trujillo", escuderia));
		escuderia.addCar(new Car("Mary Lea", "Trump", escuderia));
		
		escuderia = new Garage("Prism Motorsports");
		garageList.put(escuderia.getGarageName(), escuderia);
		escuderia.addCar(new Car("Alan", "Turing", escuderia));
		escuderia.addCar(new Car("Agnes Ermina", "Wells", escuderia));
		escuderia.addCar(new Car("Elisabeth", "Winterhalter", escuderia));
		
		Race carrera = new StandardRace("Sport Clips Haircuts VFW 200", 200);
		
		raceList.put(carrera.getRaceName(), carrera);
		carrera = new StandardRace("Kansas Lottery 300", 300);
		raceList.put(carrera.getRaceName(), carrera);
		carrera = new StandardRace("Drive for the Cure 250", 250);
		raceList.put(carrera.getRaceName(), carrera);
		carrera = new StandardRace("Pennzoil 150 At The Brickyard", 150);
		raceList.put(carrera.getRaceName(), carrera);
		
		carrera = new EliminationRace("Lti Printing", 50);
		raceList.put(carrera.getRaceName(), carrera);
		carrera = new EliminationRace("Xfinity Series Race At New Hampshire", 60);
		raceList.put(carrera.getRaceName(), carrera);
		carrera = new EliminationRace("Henry 180", 20);
		raceList.put(carrera.getRaceName(), carrera);
		carrera = new EliminationRace("Hollywood Casino", 10);
		raceList.put(carrera.getRaceName(), carrera);
		
		Competition torneo = new Competition("Xfinity Series");
		for(int i = 0; i < 5; i++) {
			Random rndm = new Random();
			torneo.addRace((Race)(raceList.values().toArray()[rndm.nextInt(raceList.size())]));
		}
		competitionList.put(torneo.getCompetitionName(), torneo);
		
		
		torneo = new Competition("Cup Series");
		for(int i = 0; i < 3; i++) {
			Random rndm = new Random();
			torneo.addRace((Race)(raceList.values().toArray()[rndm.nextInt(raceList.size())]));
		}
		competitionList.put(torneo.getCompetitionName(), torneo);
		
		torneo = new Competition("Camping World Truck Series");
		for(int i = 0; i < 15; i++) {
			Random rndm = new Random();
			torneo.addRace((Race)(raceList.values().toArray()[rndm.nextInt(raceList.size())]));
		}
		competitionList.put(torneo.getCompetitionName(), torneo);
		
		FileOutputStream fos = new FileOutputStream(dataFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(garageList);
		oos.writeObject(competitionList);
		oos.close();


	}
}