package es.imatia.raceControl.utils;

import es.imatia.raceControl.objectsclasses.*;

import java.util.HashMap;
import java.util.Objects;

public class GarageUtils {

	public static void garageMenu(HashMap<String, Garage> garageList) {
		int option = -1;

		while (option != 0) {
			Garage actualGarage = null;
			System.out.print(Utils.secondaryHeader("Esuderías", '_'));
			System.out.print(("\n\t1. Listar escuderías\n\t2. Añadir coche a escudería\n\t3. Añadir nueva escudería"
					+ "\n\t4. Eliminar coche de escudería\n\t5. Eliminar escudería\n\t"
					+ "6. Mostrar detalle de escudería\n\t0. Volver a menú principal"));
			option = Utils.readPositiveInt("¿Que acción desea realizar?:");
			switch (option) {
			case 1:
				System.out.print(showGaragesList(garageList));
				break;
			case 2:
				actualGarage = selectGarage(garageList);
				if (!Objects.isNull(actualGarage)) {
					Car car = newCar(actualGarage);
					if (!actualGarage.getCarList().containsKey(car.getCarName())) {
						actualGarage.getCarList().put(car.getCarName(), car);
						System.out.print("\nCoche añadido correctamente\n");
					} else {
						System.out.print("\nEse coche ya existe en la escudería seleccionada\n");
					}
				} else {
					System.out.print("\nNo es posible realizar la acción\n");
				}
				break;
			case 3:
				if (newGarage(garageList)) {
					System.out.print("\nEscudería creada correctamente\n");
				} else {
					System.out.print("\nYa existe esa escudería en nuestra base de datos\n");
				}
				break;
			case 4:
				actualGarage = selectGarage(garageList);
				if (!Objects.isNull(garageList)) {
					if (!actualGarage.getCarList().isEmpty()) {
						if (removeCar(actualGarage)) {
							System.out.print("\nCoche eliminado correctamente\n");
						} else {
							System.out.print("\nNo ha sido posible completar la acción\n");
						}

					} else {
						System.out.print("\nLa escudería seleccionada no tiene ningún coche.\n");
					}
				} else {
					System.out.print("\nNo es posible realizar la acción\n");
				}
				break;
			case 5:
				actualGarage = selectGarage(garageList);
				if (!Objects.isNull(garageList)) {
					garageList.remove(actualGarage.getGarageName());
					System.out.print("\nEscudería eliminada correctamente\n");
				} else {
					System.out.print("\nNo existe la escudería seleccionada\n");
				}
				break;
			case 6:
				actualGarage = selectGarage(garageList);
				if (!Objects.isNull(actualGarage)) {
					if (!actualGarage.getCarList().isEmpty()) {
						System.out.print(showCarGarageList(actualGarage));
					} else {
						System.out.print("\nEsta escudería no tiene ningún coche todavía.\n");
					}
				} else {
					System.out.print("\nNo existe la escudería seleccionada\n");
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

	public static boolean newGarage(HashMap<String, Garage> garageList) {
		boolean created = false;
		String garageName = Utils.prettyPrint(Utils.readString("Indique el nombre de la nueva escudería:"));
		if (!garageList.containsKey(garageName)) {
			garageList.put(garageName, new Garage(garageName));
			created = true;
		}
		return created;
	}

	public static Garage selectGarage(HashMap<String, Garage> garageList) {
		Garage garage = null;
		if (!garageList.isEmpty()) {
			String garageName;
			System.out.print(showGaragesList(garageList));
			garageName = Utils.prettyPrint("Escriba el nombre de la escudería deseada:");
			if (garageList.containsKey(garageName)) {
				garage = garageList.get(garageName);
			}
		}
		return garage;
	}

	public static String showGaragesList(HashMap<String, Garage> garageList) {
		String garageString = "\nEscuderías: \n";
		if (!garageList.isEmpty()) {
			for (Garage garage : garageList.values()) {
				garageString += "\t- " + garage.getGarageName() + "\n";
			}
		} else {
			garageString += "\tTodavía no hay elementos para mostrar.\n";
		}
		return garageString;
	}

	public static String showCarGarageList(Garage garage) {
		String carString = "\nCoches de la escudería " + garage.getGarageName() + ": \n";
		if (!garage.getCarList().isEmpty()) {
			for (Car car : garage.getCarList().values()) {
				carString += "\t- "  + car.getCarName() + "\n";
			}
		} else {
			carString += "\tTodavía no hay elementos para mostrar.\n";
		}
		return carString;
	}

	public static boolean removeCar(Garage garage) {
		boolean removed = false;
		System.out.print(showCarGarageList(garage));
		String carName = Utils.prettyPrint(Utils.readString("Indique el nombre del coche a eliminar:"));
		if (garage.getCarList().containsKey(carName)) {
			garage.getCarList().remove(carName);
			removed = true;
		}
		return removed;
	}

	public static Car newCar(Garage garage) {
		String model, brand;
		brand = Utils.prettyPrint(Utils.readString("Indique la marca del coche:"));
		model = Utils.prettyPrint(Utils.readString("Indique el modelo del coche:"));
		return new Car(brand, model, garage);
	}
}
