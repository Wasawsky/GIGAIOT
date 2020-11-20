package edu.eci.isib.sparkDocker;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import spark.Request;

public class Log {

    public static void main(String args[]) {
        port(getPort());
        get("/hello", (req, res) -> "Hello Docker");
        get("/data", (req, res) -> search());
        post("/input", (req, res) -> entrada(req));
    }

    public static String search() {
        DBConnection con = new DBConnection();
        con.queryAll();
        return "Done2";
    }

    public static String entrada(Request req) {

        String json = req.body();
        JSONObject data = new JSONObject(json);
        
        System.out.println(data);
        

        //DBConnection con = new DBConnection();
        //JSONObject data = new JSONObject();
        //data.put("id", req.body());
        //data.put("address", req.body());
        //data.put("date1", new Date());
        //con.insert(data.toString());
        /*
        "iotdevice":{
            "id": "Wzd\u0000",
            "address": "110881-MICHAEL-BALLESTEROS",
            "date1": "Tue Mar 31 14:37:23 IST 2015",
            "date2": "Tue Mar 31 14:37:23 IST 2015",
            "recoil": 1000
        }*/
        return "Done";
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
        return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
        }
}
