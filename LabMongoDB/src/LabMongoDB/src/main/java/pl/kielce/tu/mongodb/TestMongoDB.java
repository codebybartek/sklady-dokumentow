package pl.kielce.tu.mongodb;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.elemMatch;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.or;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Updates.inc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class TestMongoDB {
	public static void main(String[] args) {

		String user = "student01";
		String password = "student01";
		String host = "localhost";
		int port = 27017;
		String database = "database01";

		String clientURI = "mongodb://" + user + ":" + password + "@" + host + ":" + port + "/" + database;
		MongoClientURI uri = new MongoClientURI(clientURI);

		MongoClient mongoClient = new MongoClient(uri);

		MongoDatabase db = mongoClient.getDatabase(database);

		db.getCollection("people").drop();
		
		MongoCollection<Document> collection = db.getCollection("people");
	
		Document nowak = new Document("_id", 1)
				.append("lastname", "Nowak")
                .append("names", "Jan")
                .append("age", 21)
                .append("grades", Arrays.asList(new Document("programming", 5.0), new Document("mathematics", 4.0), new Document("physics", 3.0)));	
        collection.insertOne(nowak);
         
		Document polak = new Document("_id", 2)
				.append("lastname", "Polak")
                .append("names", Arrays.asList("Piotr","Adam"))
                .append("age", 22)
                .append("grades", new Document("programming", 4.5).append("mathematics", 4.0).append("physics", 3.5));
        collection.insertOne(polak);
		
 		List<Document> documents = new ArrayList<Document>();
 		for (int i = 0; i < 2; i++) 
 		    documents.add(new Document("_id", 10 + i));
 		collection.insertMany(documents);
 		 		
 		Document first = collection.find().first();
 		System.out.println("find().first() " + first.toJson());
 		 			
 		for (Document doc : collection.find())
 		    System.out.println("find() " + doc.toJson());
 		
 		Document myDoc = collection.find(lt("_id", 2)).first();
		System.out.println("lt(\"_id\", 2) " + myDoc.toJson());
 		
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
 		    System.out.println("deleteOne(eq(\"_i\", 11)) " + doc.toJson());		
		
		DeleteResult deleteResult = collection.deleteMany(gt("_id", 0));
		System.out.println(deleteResult.getDeletedCount());
 		for (Document doc : collection.find())
 		    System.out.println("deleteMany(gt(\"_id\", 0)) " + doc.toJson());	
 		
		mongoClient.close();
	}
}