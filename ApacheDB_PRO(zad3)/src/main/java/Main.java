// Copyright (c) 2011-present, Facebook, Inc.  All rights reserved.
//  This source code is licensed under both the GPLv2 (found in the
//  COPYING file in the root directory) and Apache 2.0 License
//  (found in the LICENSE.Apache file in the root directory).

import net.minidev.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.*;
import java.lang.IllegalArgumentException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;



public class Main {


    public static void main(final String[] args) throws Exception {

        ApacheConf apacheConf = new ApacheConf();

        do {
            Scanner scanner=new Scanner(System.in);
            System.out.println("<--------------BIURO PODROZY-------------->");
            System.out.println("-- 1. Wyświetl wszystkich wycieczki");
            System.out.println("-- 2. Wyświetl wycieczke o podanym ID");
            System.out.println("-- 3. Wyszukaj wycieczke po cenie");
            System.out.println("-- 4. Usuń wybrana wycieczke");
            System.out.println("-- 5. Aktualizuj wycieczke");
            System.out.println("-- 6. Dodaj wycieczke");


            System.out.println("Podaj numer operacji do wykonania: ");
            String option=scanner.nextLine();
            switch (option) {
                case "1":
                    apacheConf.showAll();
                    break;

                case "2":
                    System.out.println("Podaj ID wycieczki do wyświetlenia: ");
                    int id=scanner.nextInt();
                    apacheConf.show(id);
                    break;

                case "3":
                    System.out.println("Cena od: ");
                    int pricee=scanner.nextInt();
                    System.out.println("Cena do: ");
                    int pricee2=scanner.nextInt();
                    apacheConf.showByPrice(pricee,pricee2);
                    break;

                case "4":
                    System.out.println("Którego klienta usunąć? Podaj ID: ");
                    int idd =scanner.nextInt();
                    apacheConf.delete(idd);
                    break;

                case "5":
                    System.out.println("Podaj id wycieczki do aktualizacji: ");
                    String tripId = scanner.nextLine();
                    System.out.println("Podaj nowa nazwe: ");
                    String newName = scanner.nextLine();
                    apacheConf.modify(Integer.parseInt(tripId),newName);
                    break;

                case "6":
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
                    apacheConf.addNew(name, place, date, Integer.parseInt(price), clientNames, clientPESELS);
                    break;

                default:
                    System.out.println("Błędna intrukcja");
            }
        } while (true);



    }


}

