package pl.kielce.tu.mongodb;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.inc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class TestMongoDB {
	public static int counter;
	public static void main(String[] args) {

		MongoDB mongoDB = new MongoDB();

		do {
			Scanner scanner=new Scanner(System.in);
			System.out.println("<--------------BIURO PODROZY-------------->");
			System.out.println("-- 1. Dodaj wycieczki z listy");
			System.out.println("-- 2. Wyświetl wszystkich wycieczki");
			System.out.println("-- 3. Wyświetl wycieczke o podanym ID");
			System.out.println("-- 4. Wyszukaj wycieczke po cenie");
			System.out.println("-- 5. Usuń wybrana wycieczke");
			System.out.println("-- 6. Aktualizuj wycieczke");
			System.out.println("-- 7. Dodaj wycieczke");


			System.out.println("Podaj numer operacji do wykonania: ");
			String option=scanner.nextLine();
			switch (option) {
				case "1":
					mongoDB.insert();
					break;

				case "2":
					mongoDB.showAll();
					break;

				case "3":
					System.out.println("Podaj ID wycieczki do wyświetlenia: ");
					long id=scanner.nextLong();
					mongoDB.showTrip((int) id);
					break;

				case "4":
					System.out.println("Cena niższa od: ");
					String pricee=scanner.nextLine();
					mongoDB.showTripBetween(Integer.parseInt(pricee));
					break;

				case "5":
					System.out.println("Którego klienta usunąć? Podaj ID: ");
					String idd =scanner.nextLine();
					mongoDB.delete(Integer.parseInt(idd));
					break;

				case "6":
					System.out.println("Podaj id wycieczki do aktualizacji: ");
					String tripId = scanner.nextLine();
					System.out.println("Podaj nowa nazwe: ");
					String newName = scanner.nextLine();
					mongoDB.update(Integer.parseInt(tripId), newName);
					break;

				case "7":
					String[] clientNames = new String[3];
					String[] clientPESELS = new String[3];
					System.out.println("Podaj nazwe");
					String name = scanner.nextLine();
					System.out.println("Podaj miejsce: ");
					String place = scanner.nextLine();
					System.out.println("Podaj date: ");
					String date = scanner.nextLine();
					System.out.println("Podaj cene: ");
					String price = scanner.nextLine();
					System.out.println("Podaj imie/nazwisko klienta1: ");
					clientNames[0] = scanner.nextLine();
					System.out.println("Podaj klienta1(PESEL): ");
					clientPESELS[0] = scanner.nextLine();
					System.out.println("Podaj imie/nazwisko klienta2: ");
					clientNames[1] = scanner.nextLine();
					System.out.println("Podaj klienta2(PESEL): ");
					clientPESELS[1] = scanner.nextLine();
					System.out.println("Podaj imie/nazwisko klienta3: ");
					clientNames[2] = scanner.nextLine();
					System.out.println("Podaj klienta3(PESEL): ");
					clientPESELS[2] = scanner.nextLine();
					mongoDB.addNew(name, place, date, Integer.parseInt(price), clientNames, clientPESELS);
					break;

				default:
					System.out.println("Błędna intrukcja");
			}
		} while (true);


	}
}