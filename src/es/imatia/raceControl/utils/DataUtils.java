package es.imatia.raceControl.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.imatia.raceControl.objectsclasses.*;

public class DataUtils {
	public static void exportXMLFile(HashMap<String, Garage> garageList, HashMap<String, Race> raceList,
			HashMap<String, Competition> competitionList, File file) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// Previews steps
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "raceControl", null);
			document.setXmlVersion("1.0");

			// Start with garageList
			Element garages = document.createElement("garages");
			if (!garageList.isEmpty()) {
				for (Garage grg : garageList.values()) {
					Element garage = document.createElement("garage");
					garage.setAttribute("garageName", grg.getGarageName());
					garages.appendChild(garage);
					Element garageCarList = document.createElement("garageCarList");
					if (!grg.getCarList().isEmpty()) {
						for (Car cr : grg.getCarList().values()) {
							Element garageCar = document.createElement("garageCar");
							garageCar.setAttribute("carSpeed", String.valueOf(cr.getCarSpeed()));
							garageCar.setAttribute("carModel", String.valueOf(cr.getCarModel()));
							garageCar.setAttribute("carBrand", String.valueOf(cr.getCarBrand()));
							garageCarList.appendChild(garageCar);
						}
					}
					garage.appendChild(garageCarList);
				}
			}
			document.getDocumentElement().appendChild(garages);

			// Next, RaceList
			Element races = document.createElement("races");
			if (!raceList.isEmpty()) {
				for (Race rc : raceList.values()) {
					Element race = document.createElement("race");
					race.setAttribute("raceName", rc.getRaceName());
					if (rc instanceof EliminationRace) {
						race.setAttribute("previewsRound", String.valueOf(((EliminationRace) rc).getPreviewsRounds()));
					} else if (rc instanceof StandardRace) {
						race.setAttribute("duration", String.valueOf(((StandardRace) rc).getDuration()));
					}
					races.appendChild(race);
				}
			}
			document.getDocumentElement().appendChild(races);

			// Finally, CompetitionList
			Element competitions = document.createElement("competitions");
			if (!competitionList.isEmpty()) {
				for (Competition cmpt : competitionList.values()) {
					Element competition = document.createElement("competition");
					competition.setAttribute("competitionName", cmpt.getCompetitionName());
					competition.setAttribute("doneRaces", String.valueOf(cmpt.getDoneRaces()));

					Element competitionRaces = document.createElement("competitionRaces");
					if (!cmpt.getRaceList().isEmpty()) {
						for (Race rc : cmpt.getRaceList().values()) {
							Element competitionRace = document.createElement("competitionRace");
							competitionRace.setAttribute("raceName", rc.getRaceName());
							if (rc instanceof EliminationRace) {
								competitionRace.setAttribute("previewsRound",
										String.valueOf(((EliminationRace) rc).getPreviewsRounds()));
							} else if (rc instanceof StandardRace) {
								competitionRace.setAttribute("duration",
										String.valueOf(((StandardRace) rc).getDuration()));
							}
							Element competitionCars = document.createElement("competitionCars");
							if (!rc.getCarList().isEmpty()) {
								for (CarCompetition cr : rc.getCarList()) {
									Element competitionCar = document.createElement("competitionCar");
									competitionCar.setAttribute("carModel", String.valueOf(cr.getCar().getCarModel()));
									competitionCar.setAttribute("carBrand", String.valueOf(cr.getCar().getCarBrand()));
									competitionCar.setAttribute("carGarage",
											String.valueOf(cr.getCar().getGarageName()));
									competitionCar.setAttribute("competitionDistance",
											String.valueOf(cr.getCarDistance()));
									competitionCar.setAttribute("competitionPunctuation",
											String.valueOf(cr.getCarPunctuation()));
									competitionCars.appendChild(competitionCar);
								}
							}
							competitionRace.appendChild(competitionCars);
							Element racePodium = document.createElement("racePodium");
							if (rc.getRacePodium() != null) {
								for (int i = 0; i < rc.getRacePodium().size(); i++) {
									Element podiumCar = document.createElement("podiumCar");
									podiumCar.setAttribute("position", (i + 1) + "");
									podiumCar.setAttribute("carName", rc.getRacePodium().get(i).getCar().getCarName());
									podiumCar.setAttribute("carGarage",
											String.valueOf(rc.getRacePodium().get(i).getCar().getGarageName()));
									racePodium.appendChild(podiumCar);
								}
							}
							competitionRace.appendChild(racePodium);

							competitionRaces.appendChild(competitionRace);
						}
					}
					competition.appendChild(competitionRaces);
					competitions.appendChild(competition);
				}
			}

			document.getDocumentElement().appendChild(competitions);

			// Create document
			Source source = new DOMSource(document);
			Result result = new StreamResult(new File("raceControlData.xml"));

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);

		} catch (Exception e) {
			System.out.println("\nHa habido un problema exportando los datos.\n");
		}

	}

	public static void importXMLFile(HashMap<String, Garage> garageList, HashMap<String, Race> raceList,
			HashMap<String, Competition> competitionList, File file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			document.getDocumentElement().normalize();

			// Garages
			NodeList list = document.getElementsByTagName("garages");
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element garagesElement = (Element) list.item(i);
					NodeList garageListNode = garagesElement.getChildNodes();
					for (int j = 0; j < garageListNode.getLength(); j++) {
						if (garageListNode.item(j).getNodeType() == Node.ELEMENT_NODE) {
							Element garageElement = (Element) garageListNode.item(j);
							Garage garage = new Garage(garageElement.getAttribute("garageName"));
							NodeList garageCarListNode = garageElement.getChildNodes();
							for (int k = 0; k < garageCarListNode.getLength(); k++) {
								if (garageCarListNode.item(k).getNodeType() == Node.ELEMENT_NODE) {
									Element garageCarListElement = (Element) garageCarListNode.item(k);
									NodeList garageCarNode = garageCarListElement.getChildNodes();
									for (int z = 0; z < garageCarNode.getLength(); z++) {
										if (garageCarNode.item(z).getNodeType() == Node.ELEMENT_NODE) {
											Element garageCarElement = (Element) garageCarNode.item(z);
											garage.addCar(new Car(garageCarElement.getAttribute("carBrand"),
													garageCarElement.getAttribute("carModel"), garage));
										}
									}
								}
							}
							garageList.put(garage.getGarageName(), garage);
						}

					}

				}

			}

			// Races
			list = document.getElementsByTagName("races");
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element racesElement = (Element) list.item(i);
					NodeList raceNode = racesElement.getChildNodes();
					for (int j = 0; j < raceNode.getLength(); j++) {
						if (raceNode.item(j).getNodeType() == Node.ELEMENT_NODE) {
							Element raceElement = (Element) raceNode.item(j);
							Race race = null;
							if (raceElement.hasAttribute("duration")) {
								race = new StandardRace(raceElement.getAttribute("raceName"),
										Integer.parseInt(raceElement.getAttribute("duration")));
							} else if (raceElement.hasAttribute("previewsRound")) {
								race = new EliminationRace(raceElement.getAttribute("raceName"),
										Integer.parseInt(raceElement.getAttribute("previewsRound")));
							}
							raceList.put(race.getRaceName(), race);
						}
					}
				}
			}

			// Competitions
			list = document.getElementsByTagName("competitions");
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i).getNodeType() == Node.ELEMENT_NODE) {
					NodeList competitionNode = ((Element) list.item(i)).getElementsByTagName("competition");
					for (int j = 0; j < competitionNode.getLength(); j++) {
						Element competitionElement = (Element) competitionNode.item(j);
						Competition competition = new Competition(competitionElement.getAttribute("competitionName"),
								Integer.parseInt(competitionElement.getAttribute("doneRaces")));
						NodeList competitionRaceNode = competitionElement.getElementsByTagName("competitionRace");
						for (int k = 0; k < competitionRaceNode.getLength(); k++) {
							Element competitionRaceElement = (Element) competitionRaceNode.item(k);
							Race race = null;
							if (competitionRaceElement.hasAttribute("previewsRound")) {
								race = new EliminationRace(competitionRaceElement.getAttribute("raceName"),
										Integer.parseInt(competitionRaceElement.getAttribute("previewsRound")));
							} else if (competitionRaceElement.hasAttribute("duration")) {
								race = new EliminationRace(competitionRaceElement.getAttribute("raceName"),
										Integer.parseInt(competitionRaceElement.getAttribute("duration")));
							}
							NodeList competitionRaceCar = competitionRaceElement.getElementsByTagName("competitionCar");
							for (int z = 0; z < competitionRaceCar.getLength(); z++) {
								Element competitionCarElement = (Element) competitionRaceCar.item(z);
								String garageName = competitionCarElement.getAttribute("carGarage");								
								String carKey = competitionCarElement.getAttribute("carBrand") + " "
										+ competitionCarElement.getAttribute("carModel");
								Car car;
								Garage garage;
								if (garageList.containsKey(garageName)) {
									garage = garageList.get(garageName);
									if (garage.getCarList().containsKey(carKey)) {
										car = garage.getCarList().get(carKey);
									} else {
										car = new Car(competitionCarElement.getAttribute("carBrand"),
												competitionCarElement.getAttribute("carModel"), garage);
									}
								} else {
									garage = new Garage(garageName);
									car = new Car(competitionCarElement.getAttribute("carBrand"),
											competitionCarElement.getAttribute("carModel"), garage);
								}
								CarCompetition carCmpt = new CarCompetition(car);
								carCmpt.setCarPunctuation(
										Integer.parseInt(competitionCarElement.getAttribute("competitionPunctuation")));
								race.getCarList().add(carCmpt);
								competition.addCar(carCmpt, garageList);

							}
							
							NodeList competitionRacePodium = competitionRaceElement.getElementsByTagName("podiumCar");
							ArrayList<CarCompetition> podium = new ArrayList<CarCompetition>();
							for (int z = 0; z < competitionRacePodium.getLength(); z++) {
								Element racePodium = (Element) competitionRacePodium.item(z);
								boolean founded = false;
								for (int p = 0; p < competition.getCarList().size() && !founded; p++) {
									CarCompetition cr = competition.getCarList().get(p);
									if (racePodium.getAttribute("carGarage").equals(cr.getCar().getGarageName())
											&& racePodium.getAttribute("carName").equals(cr.getCar().getCarName())) {
																				founded = true;
										switch (racePodium.getAttribute("position")) {
										case "1":
											podium.add(0, cr);
											break;
										case "2":
											podium.add(1, cr);
											break;
										case "3":
											podium.add(2, cr);
											break;
										}
									}
									race.setRacePodium(podium, false);
								}
								
							}
							if (!competition.getRaceList().containsKey(race.getRaceName())) {
								competition.getRaceList().put(race.getRaceName(), race);
							}
						}
						competition.setCompetitionPodium();
						competitionList.put(competition.getCompetitionName(), competition);
					}
				}
			}

		} catch (Exception e) {
			e.setStackTrace(null);
		}
	}
}
