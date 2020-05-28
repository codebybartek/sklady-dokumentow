package pl.kielce.tu.mongodb;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.include;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDB {
    String user = "student01";
    String password = "student01";
    String host = "localhost";
    int port = 27017;
    String database = "database01";
    int counter = 1;

    String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
    MongoClientURI uri = new MongoClientURI(clientURI);

    MongoClient mongoClient = new MongoClient(uri);

    MongoDatabase db = mongoClient.getDatabase(database);

    MongoCollection<Document> collection = db.getCollection("trips");

    public void insert(){
        Document greta = new Document("_id", counter)
                .append("name", "Last minute Greece")
                .append("place", "Greta, Gracja")
                .append("date", "2020-02-03")
                .append("price", 2000)
                .append("clients", Arrays.asList(new Document("Tomasz Noawkowski", "85122344321"), new Document("Bartek Koziel", "93124344321"), new Document("Marian Tomczyk", "95122344321")));
        collection.insertOne(greta);
        counter++;

        Document madryt = new Document("_id", counter)
                .append("name", "Madryt")
                .append("place", "Madryt, Hiszpania")
                .append("date", "2021-05-03")
                .append("price", 2200)
                .append("clients", Arrays.asList(new Document("Arek Noawkowski", "85122344321"), new Document("Bartek Koziel", "93124344321"), new Document("Dariusz Tomaszewski", "95122344321")));
        collection.insertOne(madryt);
        counter++;

        Document malaga = new Document("_id", counter)
                .append("name", "Słoneczna Malaga")
                .append("place", "Malaga, Hiszpania")
                .append("date", "2020-07-07")
                .append("price", 1700)
                .append("clients", Arrays.asList(new Document("Wojciech Noawkowski", "85122344321"), new Document("Bartek Koziel", "93124344321"), new Document("Marian Tomczyk", "95122344321")));
        collection.insertOne(malaga);
        counter++;

        Document lisbona = new Document("_id", counter)
                .append("name", "Lisbona 2020")
                .append("place", "Lisbona, Portugalia")
                .append("date", "2020-08-02")
                .append("price", 2500)
                .append("clients", Arrays.asList(new Document("Artur Mak", "92122344321"), new Document("Karol Wozniak", "91124344321"), new Document("Damian Tomczyk", "96151357121")));
        collection.insertOne(lisbona);
        counter++;
    }

    public void showAll(){
        Document first = collection.find().first();
        System.out.println("find().first() " + first.toJson());

        for (Document doc : collection.find())
            System.out.println("find() " + doc.toJson());

        Document myDoc = collection.find(lt("_id", 2)).first();
        System.out.println("lt(\"_id\", 2) " + myDoc.toJson());
    }

    public void showTrip(int id){
        for (Document doc : collection.find(eq("_id", id))){
            System.out.println("Znaleziony wynik: " + doc.toJson());
        };
    }

    public void update(int id, String newName){
        collection.updateOne(eq("_id", id), new Document("$set", new Document("name", newName)));
    }

    public void showTripBetween(int price2){
        for (Document d : collection.find(lte("price", price2)))
            System.out.println(d.toJson());
    }

    public void addNew(String name, String place,  String date, int price, String[] clientNames, String[] clientPESELS ){
        long counterr = collection.countDocuments();
        Document client = new Document("_id", counterr)
                .append("name", name)
                .append("place", place)
                .append("date", date)
                .append("price", price)
                .append("clients", new Document(clientNames[0], clientPESELS[0])
                        .append(clientNames[1], clientPESELS[1])
                        .append(clientNames[2], clientPESELS[2]));
        collection.insertOne(client);
    }

    public void delete(int id){
        collection.deleteOne(eq("_id", id));
    }


/*

 		for (Document d : collection.find(or(
            eq("grades.programming", 5.0),
    eq("grades.programming", 4.5))))
            System.out.println("or(eq(\"grades.programming\", 5.0),eq(\"grades.programming\", 4.5)) " + d.toJson());

 		for (Document d : collection.find(or(
            eq("grades", Document.parse("{programming : 5.0}")),
    eq("grades", Document.parse("{programming : 4.5}")))))
            System.out.println("or(eq(\"grades\", Document.parse(\"{programming : 5.0}\")),eq(\"grades\", Document.parse(\"{programming : 4.5}\"))) " + d.toJson());

 		for (Document d : collection.find(or(
            elemMatch("grades", Document.parse("{programming : 5.0}")),
    elemMatch("grades", Document.parse("{programming : 4.5}")))))
            System.out.println("find(or(elemMatch(\"grades\", Document.parse(\"{programming : 5.0}\")),elemMatch(\"grades\", Document.parse(\"{programming : 4.5}\"))) " + d.toJson());

 		for (Document d : collection.find(exists("names.1")))
            System.out.println("find(exists(\"names.1\")) " + d.toJson());

 		for (Document d : collection.find(exists("grades.programming", false)))
            System.out.println("find(exists(\"grades.programming\", false)) " + d.toJson());

 		for (Document doc : collection.find().projection(include("firstname", "names")))
            System.out.println("find().projection(include(\"firstname\", \"names\")) " + doc.toJson());

 		for (Document doc : collection.find(and(exists("lastname", true), exists("names", true))).projection(include("firstname", "names")))
            System.out.println("find(and(exists(\"firstname\", true), exists(\"name\", true))).projection(include(\"firstname\", \"names\")) " + doc.toJson());

 		for (Document doc : collection.find().sort(new Document("_id", -1)))
            System.out.println("find().sort(new Document(\"_id\", -1))) " + doc.toJson());

 		for (Document doc : collection.find().sort(new Document("_id", -1)).limit(2))
            System.out.println("find().sort(new Document(\"_id\", -1)).limit(2) " + doc.toJson());

		collection.updateOne(eq("_id", 10), new Document("$set", new Document("lastname", "Kowal").append("firstName", "Adam")));
 		for (Document doc : collection.find())
            System.out.println("updateOne(eq(\"_id\", 10), new Document(\"$set\", new Document(\"lastname\", \"Kowal\").append(\"firstName\", \"Adam\")) " + doc.toJson());

    UpdateResult updateResult = collection.updateMany(exists("age"), inc("age", 1));
 		System.out.println(updateResult.getModifiedCount());
 		for (Document doc : collection.find())
            System.out.println("updateMany(exists(\"age\"), inc(\"age\", 1)) " + doc.toJson());

		collection.deleteOne(eq("_id", 11));
 		for (Document doc : collection.find())
            System.out.println("deleteOne(eq(\"_i\", 11)) " + doc.toJson());*/



}
