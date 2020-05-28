import net.minidev.json.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

class ApacheConf {

    Connection connection = DriverManager.getConnection("jdbc:drill:drillbit=localhost");
    Statement st = connection.createStatement();
    JSONArray journeys_list = new JSONArray();

    public ApacheConf() throws Exception{

        // load the JDBC driver
        Class.forName("org.apache.drill.jdbc.Driver");

        // Query drill

        Generate();

        st.executeQuery("alter session set `store.format`='json'");
        st.executeQuery("use dfs.tmp");

    }

    public void showAll() throws SQLException {
        ResultSet rs2 = st.executeQuery("Select * from dfs.`D:/studia/apache-drill-1.17.0/apache-drill-1.17.0/clients.json`");

        while(rs2.next()){
            System.out.println("Date: " + rs2.getString(1));
            System.out.println("Clients: " + rs2.getString(2));
            System.out.println("Price: " + rs2.getString(3));
            System.out.println("Name: " + rs2.getString(4));
            System.out.println("Id: " + rs2.getString(5));
            System.out.println("Place: " + rs2.getString(6));
            System.out.println("--------------------------------------");
        }
    }

    public void delete(int id) throws SQLException {
        ResultSet rs = st.executeQuery("Select * from dfs.`D:/studia/apache-drill-1.17.0/apache-drill-1.17.0/clients.json`");
        int position=0;
        boolean change = false;
        while(rs.next()){
            if(id == Integer.parseInt(rs.getString(5))){
                change = true;
                break;
            }
            position++;
        }
        if(change) {
            journeys_list.remove(position);

            try (FileWriter file = new FileWriter("D:/studia/apache-drill-1.17.0/apache-drill-1.17.0/clients.json")) {

                file.write(journeys_list.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Nie znaleziono");
        }

        System.out.println("*----------After Delete-----------*");

        showAll();

    }

    public void modify(int id, String name) throws SQLException {

        for (int i = 0; i < journeys_list.size(); i++) {
            JSONObject object = (JSONObject)journeys_list.get(i);
            if(id == Integer.parseInt(object.get("id").toString())){
                journeys_list.remove(i);
                JSONObject journey1_clients = new JSONObject();
                journey1_clients.put("Bartek Koziel", "93124344321");
                journey1_clients.put("Marian Tomczyk", "95122344321");

                JSONObject journey = new JSONObject();
                journey.put("id",  object.get("id"));
                journey.put("name",  name);
                journey.put("place",  object.get("place"));
                journey.put("date",  object.get("date"));
                journey.put("price",  object.get("price"));
                journey.put("clients",  object.get("clients"));
                journeys_list.add(journey);

                try (FileWriter file = new FileWriter("D:/studia/apache-drill-1.17.0/apache-drill-1.17.0/clients.json")) {

                    file.write(journeys_list.toJSONString());
                    file.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Po modyfikacji");
            showAll();

        }
    }


    public void show(int id) throws SQLException {
        ResultSet rs2 = st.executeQuery("Select * from dfs.`D:/studia/apache-drill-1.17.0/apache-drill-1.17.0/clients.json` where id="+id+"");
        System.out.println("Show one");
        while(rs2.next()){
            System.out.println("Date: " + rs2.getString(1));
            System.out.println("Clients: " + rs2.getString(2));
            System.out.println("Price: " + rs2.getString(3));
            System.out.println("Name: " + rs2.getString(4));
            System.out.println("Id: " + rs2.getString(5));
            System.out.println("Place: " + rs2.getString(6));
            System.out.println("--------------------------------------");
        }
    }

    public void showByPrice(int price1, int price2) throws SQLException {
        ResultSet rs2 = st.executeQuery("Select * from dfs.`D:/studia/apache-drill-1.17.0/apache-drill-1.17.0/clients.json` where price>"+price1+" and price<"+price2+"");
        System.out.println("Show by price");
        while(rs2.next()){
            System.out.println("Date: " + rs2.getString(1));
            System.out.println("Clients: " + rs2.getString(2));
            System.out.println("Price: " + rs2.getString(3));
            System.out.println("Name: " + rs2.getString(4));
            System.out.println("Id: " + rs2.getString(5));
            System.out.println("Place: " + rs2.getString(6));
            System.out.println("--------------------------------------");
        }
    }

    public void addNew(String name, String place, String date, int price, String[] clientNames, String[] clientPESELS) throws SQLException {
        JSONObject journey_clients = new JSONObject();
        journey_clients.put(clientNames[0], clientPESELS[0]);
        journey_clients.put(clientNames[1], clientPESELS[1]);
        journey_clients.put(clientNames[2], clientPESELS[2]);

        JSONObject journey = new JSONObject();
        journey.put("id", 5);
        journey.put("name", name);
        journey.put("place", place);
        journey.put("date", date);
        journey.put("price", price);
        journey.put("clients", journey_clients);

        journeys_list.add(journey);

        try (FileWriter file = new FileWriter("D:/studia/apache-drill-1.17.0/apache-drill-1.17.0/clients.json")) {

            file.write(journeys_list.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Show after Insert");
        showAll();
    }

    public void Generate(){
        JSONObject journey1_clients = new JSONObject();
        journey1_clients.put("Bartek Koziel", "93124344321");
        journey1_clients.put("Marian Tomczyk", "95122344321");

        JSONObject journey1 = new JSONObject();
        journey1.put("id", 1);
        journey1.put("name", "Last minute Greece");
        journey1.put("place", "Greta, Gracja");
        journey1.put("date", "2020-02-03");
        journey1.put("price", 2000);
        journey1.put("clients", journey1_clients);

        JSONObject journey2_clients = new JSONObject();
        journey2_clients.put("Wojciech Nowakowski", "85122344321");
        journey2_clients.put("Artur Mak", "92122344321");

        JSONObject journey2 = new JSONObject();
        journey2.put("id", 2);
        journey2.put("name", "Madryt");
        journey2.put("place", "Madryt, Hiszpania");
        journey2.put("date", "2021-09-03");
        journey2.put("price", 2200);
        journey2.put("clients", journey2_clients);

        JSONObject journey3_clients = new JSONObject();
        journey3_clients.put("Arek Nowakowski", "85122344321");
        journey3_clients.put("Artur Mak", "92122344321");

        JSONObject journey3 = new JSONObject();
        journey3.put("id", 3);
        journey3.put("name", "Słoneczna Malaga");
        journey3.put("place", "Malaga, Hiszpania");
        journey3.put("date", "2020-06-03");
        journey3.put("price", 2100);
        journey3.put("clients", journey3_clients);

        JSONObject journey4_clients = new JSONObject();
        journey4_clients.put("Arek Noawkowski", "85122344321");
        journey4_clients.put("Karol Ptak", "91122344321");

        JSONObject journey4 = new JSONObject();
        journey4.put("id", 4);
        journey4.put("name", "Lisbona 2020");
        journey4.put("place", "Lisbona, Portugalia");
        journey4.put("date", "2020-08-02");
        journey4.put("price", 2500);
        journey4.put("clients", journey4_clients);

        journeys_list.add(journey1);
        journeys_list.add(journey2);
        journeys_list.add(journey3);
        journeys_list.add(journey4);

        try (FileWriter file = new FileWriter("D:/studia/apache-drill-1.17.0/apache-drill-1.17.0/clients.json")) {

            file.write(journeys_list.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}