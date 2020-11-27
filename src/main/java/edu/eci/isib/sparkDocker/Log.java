package edu.eci.isib.sparkDocker;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;

import spark.Request;
import spark.Response;

public class Log {

    public static void main(String args[]) {
        port(getPort());
        get("/hello", (req, res) -> "Hello Docker");
        get("/data", (req, res) -> search());
        get("/index", (req, res) -> indexDataPage(req));
        get("/info", (req, res) -> infoDataPage(req));
        get("/processdata", (req, res) -> processDataPage(req, res));
        post("/input", (req, res) -> entrada(req));
    }

    private static String processDataPage(Request req, Response res) {
        String msg = req.queryParams("value");
        //RESTinsert(msg,serviceREST);
        res.redirect("/info");
        return "";
    }

    public static String search() {
        DBConnection con = new DBConnection();
        ArrayList<JSONObject> list = con.queryTail();
        return list.toString();
    }

    public static boolean entrada(Request req) {

        String json = req.body();
        JSONObject data = new JSONObject(json);

        String date1f =  data.getString("date");//fecha iot
        String date2f =  App.getCurrentTime();//fecha actual

        Date date1 = App.getDate(date1f);
        Date date2 = App.getDate(date2f);

        int recoil = App.restarFechas(date1, date2);

        System.out.println(recoil);

        data.put("date1f", date1f);
        data.put("date2f", date2f);
        data.put("recoil", recoil);

        DBConnection con = new DBConnection();
        JSONObject iotdata = new JSONObject();
        iotdata.put("iotdevice", data);

        System.out.println(iotdata);
        con.insert(data.toString());

        boolean response = false;
        if (recoil>3){
            response=true;
        }
        return response;
    }

    private static String indexDataPage(Request req) {
        String table = fillTable();

        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                + "   <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">"
                + "   <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>"
                + "   <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>"
                + "</head>"
                + "<body>"
                + "<div class=\"container\">"
                + "<h2>GIGAIOT</h2>"
                + "<form action=\"/processdata\">"
                + "  Busca el dispositivo<br>"
                + "  <input type=\"text\" name=\"value\">"
                + "  <br>"
                + "  <input type=\"submit\" value=\"Enviar\">"
                + "</form>"
                + "<p></p>"

                + "<div class=\"table-responsive\">"
                + "<table class=\"table\" border =\"1\">\n"
                + "<thead>\n"
                + "<tr>\n"
                + "<th>id</th>\n"
                + "<th>address</th>\n"
                + "<th>Fecha Inicial</th>\n"
                + "<th>Fecha Final</th>\n"
                + "<th>dt</th>\n"
                + "</tr>\n"
                + "</thead>\n"
                + "<tbody>\n"+
                table
                + "</tbody>\n"
                +"</table>\n"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        return pageContent;
    }

    private static String infoDataPage(Request req) {
        String table = fillTableReport();
        Device dev = infoDevice();

        String pageContent
                = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>GIGAIOT</title>"
                + "</head>"
                + "<body>"
+ "        <table id=\"datos\" border=\"1\">"
+ "        <tr><th>Peticion</th><th>dt</th></tr>"+
table
+ "        </table>"
+ "<h1>GIGAIOT</h1>"
+ "<p>"+"Id dispositivo"+"</p>"
+ "<p>"+dev.getId()+"</p>"
+ "<p>"+"Direccion dispositivo"+"</p>"
+ "<p>"+dev.address+"</p>"
+ "        <canvas id=\"lienzo\" width=\"500\" height=\"250\">"
+ "        Su navegador no permite utilizar canvas."
+ "        </canvas>"
+ "        <script type=\"text/javascript\">"
+ "            function retornarLienzo(x) {"
+ "            var canvas = document.getElementById(x);"
+ "            if (canvas.getContext) {"
+ "                var lienzo = canvas.getContext(\"2d\");   "
+ "                return lienzo;"
+ "            } else"
+ "                return false;"
+ "            }"
+ "            function dibujar() {"
+ "            var lienzo=retornarLienzo(\"lienzo\");"
+ "            if (lienzo) {"
+ "                var minx = 50;"
+ "                var maxx = 450;"
+ "                var miny = 30;"
+ "                var maxy = 170;"
+ "                var ancho = maxx - minx;"
+ "                var alto = maxy - miny;"
+ "            var minv = \"nulo\";"
+ "            var maxv = 0;"
+ "            var valor = 0;"
+ "            var tabla = document.getElementById(\"datos\");"
+ "             for (var i=1, fila; fila=tabla.rows[i]; i++) {"
+ "                valor = parseFloat(fila.cells[1].innerHTML);"
+ "                if (minv == \"nulo\" || minv > valor) {"
+ "                    minv = valor;"
+ "                }"
+ "                if (maxv < valor) {"
+ "                    maxv = valor;"
+ "                }"
+ "            }"
+ "                var escala = maxv - minv;"
+ "                var cant = i;"
+ "                lienzo.beginPath();"
+ "                lienzo.font = '10px Calibri';"
+ "                lienzo.lineWidth = 1;"
+ "                lienzo.fillStyle = 'blue';"
+ "                x = minx;"
+ "                for (var i=1, row; row=tabla.rows[i]; i++) {    "
+ "                    lienzo.fillText(row.cells[0].innerHTML, x, maxy+40);"
+ "                    x += ancho/(cant-2);"
+ "                }"
+ "                lienzo.stroke();"
+ "                lienzo.beginPath();"
+ "                lienzo.font = '10px Calibri';"
+ "                lienzo.lineWidth = 1;"
+ "                lienzo.fillStyle = 'blue';"
+ "                for (var i=1, row; row=tabla.rows[i]; i++) { "
    + "                    valor = parseFloat(row.cells[1].innerHTML);"
    + "                    y = maxy - (valor-minv) * (alto/escala);"
    + "                    lienzo.fillText(row.cells[1].innerHTML, minx-20, y);"
    + "                }"
    + "                lienzo.stroke();"
    + "                    lienzo.beginPath();"
    + "                    lienzo.lineWidth = 1;"
    + "                    lienzo.setLineDash([5,2]);"
    + "                    lienzo.strokeStyle=\"rgb(230,230,180)\";"
    + "                    for (var i=1, row; row=tabla.rows[i]; i++) { "
        + "                        valor = parseFloat(row.cells[1].innerHTML);"
        + "                        y = maxy - (valor-minv) * (alto/escala);"
        + "                        lienzo.moveTo(minx,y);"
        + "                        lienzo.lineTo(maxx,y);"
        + "                    }"
        + "                    lienzo.stroke();"
        + "                    lienzo.beginPath();"
        + "                    lienzo.strokeStyle=\"rgb(180,180,180)\";"
        + "                    lienzo.setLineDash([0,0]);"
+ "                    lienzo.lineWidth=1;"
+ "                    lienzo.strokeRect(minx,miny-20,maxx-minx,maxy-miny+40);"
+ "                    lienzo.strokeStyle=\"rgb(100,100,100)\";"
+ "                    valor = parseFloat(tabla.rows[1].cells[1].innerHTML);"
+ "                    var x = minx;"
+ "                    var y = maxy - (valor-minv) * (alto/escala);"
+ "                    lienzo.fillRect(x-2,y-2,4,4);"
+ "                    lienzo.moveTo(x,y);"
+ "                    for (var i=2, row; row=tabla.rows[i]; i++) {"
+ "                        x += ancho/(cant-2);"
+ "                        valor = parseFloat(row.cells[1].innerHTML);"
+ "                        y = maxy - (valor-minv) * (alto/escala);"
+ "                        lienzo.lineTo(x,y);"
+ "                        lienzo.fillRect(x-2,y-2,4,4);"
+ "                    }"
+ "                    lienzo.stroke();"
+ "                }"
+ "                }"
+ "                dibujar();"
+ "            </script>"
                + "</body>"
                + "</html>";
                
        return pageContent;
    }

    private static String fillTable() {
        DBConnection con = new DBConnection();
        ArrayList<JSONObject> list = con.queryTail();
        String table = "";

        for(JSONObject data: list){
            table+="<tr>\n"
            + "<td>"+data.getString("id")+"</td>\n"
            + "<td>"+data.getString("address")+"</td>\n"
            + "<td>"+data.getString("date1f")+"</td>\n"
            + "<td>"+data.getString("date2f")+"</td>\n"
            + "<td>"+data.getNumber("recoil").toString()+"</td>\n"
            + "</tr>\n";
        }
        return table;
    }

    private static Device infoDevice() {
        DBConnection con = new DBConnection();
        ArrayList<JSONObject> list = con.queryTail();
        Device dev = new Device();

        dev.setId(list.get(0).getString("id"));
        dev.setAddress(list.get(0).getString("address"));
        return dev;
    }

    private static String fillTableReport() {
        DBConnection con = new DBConnection();
        ArrayList<JSONObject> list = con.queryTail();
        String table = "";
        int i=1;

        for(JSONObject data: list){
            table+="<tr>\n"
            + "<td>"+"P"+i+"</td>\n"
            + "<td>"+data.getNumber("recoil").toString()+"</td>\n"
            + "</tr>\n";
            i++;
        }
        return table;
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
            return 4567;
    }
}
