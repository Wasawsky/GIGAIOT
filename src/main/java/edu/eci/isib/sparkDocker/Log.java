package edu.eci.isib.sparkDocker;

import static spark.Spark.*;

import java.util.Date;

import org.json.JSONObject;

import spark.Request;

public class Log {
    
    public static void main(String args[]) { 
        port(getPort()); get("/hello", (req,res) -> "Hello Docker"); 
        get("/data", (req, res) -> search()); 
        post("/input",(req, res) -> entrada(req)); 
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

        String date1f =  "2020-11-21 00:00:00";//fecha iot
        String date2f =  App.getCurrentTime();//fecha actual

        Date date1 = App.getDate(date1f);
        Date date2 = App.getDate(date2f);

        int recoil = App.restarFechas(date1, date2);

        System.out.println(recoil);

        data.put("date1f", date1f);
        data.put("date1f", date2f);
        data.put("recoil", recoil);

        DBConnection con = new DBConnection();
        JSONObject iotdata = new JSONObject();
        iotdata.put("iotdevice", data);
        con.insert(data.toString());
        return "Done";
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
            return 4567;
    }
}
