package es.imatia.raceControl.utils;

import java.util.Scanner;

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
		return ("\n\n\t1. Ir a torneos\n\t2. Ir a escuderías\n\t"
				+ "3. Ir a carreras\n\t0. Salir\n¿Que acción desea realizar?: ");

	}

	public static int readPositiveInt() {
		int num = -1;
		Scanner sc = new Scanner(System.in);
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
}
