package edu.eci.isib.sparkDocker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.util.JSON;

import org.json.JSONObject;

public class DBConnection {
    
    MongoClient mongoClient;
    MongoClientURI mongouri;
    DB database;
    DBCollection collection; 

    public DBConnection() {
        this.mongouri = new MongoClientURI("mongodb://34.228.185.76:27017");
        this.mongoClient = new MongoClient(this.mongouri);
        this.database = mongoClient.getDB("iotexperiment");
        this.collection = database.getCollection("times");
    }

    /**
     * Muestra todos los datos de la db
     */
    public void queryAll() {
        List<DBObject> consulta = collection.find().toArray();
        System.out.println(consulta);
    }

    /**
     * Inserta datos en la db
     * @param String de un json
     */
    public void insert(String data) {
        DBObject doc = (DBObject)JSON.parse(data);
        collection.insert(doc);
    }
}
