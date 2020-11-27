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
        this.mongouri = new MongoClientURI("mongodb://54.163.6.203:27017");
        this.mongoClient = new MongoClient(this.mongouri);
        this.database = mongoClient.getDB("iotexperiment");
        this.collection = database.getCollection("times");
    }

    /**
     * Muestra todos los datos de la db
     */
    public List<DBObject> queryAll() {
        List<DBObject> consulta = collection.find().toArray();
        return consulta;
    }

    /**
     * Inserta datos en la db
     * @param String de un json
     */
    public void insert(String data) {
        DBObject doc = (DBObject)JSON.parse(data);
        collection.insert(doc);
    }

    /**
     * Ultimos 10 datos
     * @return Arraylist con los datos
     */
    public ArrayList<JSONObject> queryTail() {
        List<DBObject> consulta = collection.find().toArray();
        ArrayList<JSONObject> data = new ArrayList<>();
        int lengthTail = 20,i = consulta.size()-1, count = 0;

        if(consulta.size()<lengthTail){lengthTail=consulta.size();} //Si hay menos de 10 mensajes

        while(count<lengthTail){
            JSONObject recover = new JSONObject();
            recover.put("id", consulta.get(i).get("id"));
            recover.put("address", consulta.get(i).get("address"));
            recover.put("date1f", consulta.get(i).get("date1f"));
            recover.put("date2f", consulta.get(i).get("date2f"));
            recover.put("recoil", consulta.get(i).get("recoil"));
            data.add(recover);
            count++;i--;
        }
        return data;
    }
}
