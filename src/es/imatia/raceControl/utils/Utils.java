package es.imatia.raceControl.utils;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import es.imatia.raceControl.objectsclasses.Car;
import es.imatia.raceControl.objectsclasses.Competition;
import es.imatia.raceControl.objectsclasses.EliminationRace;
import es.imatia.raceControl.objectsclasses.Garage;
import es.imatia.raceControl.objectsclasses.Race;
import es.imatia.raceControl.objectsclasses.StandardRace;

public class Utils {

	public static String principalHeader(String title, char symbol) {
		String header = "\n";
		for (int i = 0; i < 3; i++) {
			if (i == 0 || i == 2) {
				for (int j = 0; j < (title.length() * 3); j++) {
					header += symbol;
				}
			} else {
				header += "\n" + symbol;
				for (int j = 1; j < title.length(); j++) {
					header += " ";
				}
				header += title;
				for (int j = 1; j < title.length(); j++) {
					header += " ";
				}
				header += symbol + "\n";
			}
		}
		return header;
	}

	public static String secondaryHeader(String title, char symbol) {
		String header = "\n";
		for (int j = 0; j < title.length(); j++) {
			header += symbol;
		}
		header += title;
		for (int j = 0; j < title.length(); j++) {
			header += symbol;
		}
		header += "\n";

		return header;
	}

	public static String principalMenu() {
		return ("\n\n\t1. Ir a torneos\n\t2. Ir a escuderías\n\t" + "3. Ir a carreras\n\t0. Salir");

	}

	public static int readPositiveInt(String msg) {
		int num = -1;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n" + msg + " ");
		while (num < 0) {
			try {
				num = sc.nextInt();
			} catch (Exception e) {
				num = -1;
			}
			if (num < 0) {
				System.out.print("\n¡Error! Introduzca un número entero positivo: ");
			}
			sc.nextLine();
		}
		return num;
	}

	public static String readString(String msg) {
		Scanner sc = new Scanner(System.in);
		String answer = "";
		while (answer.isEmpty()) {
			System.out.print("\n" + msg + " ");
			answer = sc.nextLine();
		}
		return answer;
	}

	public static String prettyPrint(String a) {
		StringBuilder newString = new StringBuilder("");
		if (a.length() > 0) {
			newString = new StringBuilder(a.trim().toLowerCase());
			newString.setCharAt(0, Character.toUpperCase(newString.charAt(0)));
			for (int i = 1; i < newString.length() - 1; i++) {
				if (newString.charAt(i - 1) == ' ') {
					newString.setCharAt(i, Character.toUpperCase(newString.charAt(i)));
				}
			}
		}
		return newString.toString();
	}

	public static void startTask(HashMap<String, Garage> garageList, HashMap<String, Race> raceList,
			HashMap<String, Competition> competitionList) {
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

		escuderia = new Garage("Jtg Daugherty Racing");
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

		escuderia = new Garage("Nemco Motorsports");
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

		Race carrera = new StandardRace("Sport Clips Haircuts Vwf 200", 200);

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
		for (int i = 0; i < 5; i++) {
			Random rndm = new Random();
			torneo.addRace((Race) (raceList.values().toArray()[rndm.nextInt(raceList.size())]));
		}
		competitionList.put(torneo.getCompetitionName(), torneo);

		torneo = new Competition("Cup Series");
		for (int i = 0; i < 3; i++) {
			Random rndm = new Random();
			torneo.addRace((Race) (raceList.values().toArray()[rndm.nextInt(raceList.size())]));
		}
		competitionList.put(torneo.getCompetitionName(), torneo);

		torneo = new Competition("Camping World Truck Series");
		for (int i = 0; i < 15; i++) {
			Random rndm = new Random();
			torneo.addRace((Race) (raceList.values().toArray()[rndm.nextInt(raceList.size())]));
		}
		competitionList.put(torneo.getCompetitionName(), torneo);
	}

}
