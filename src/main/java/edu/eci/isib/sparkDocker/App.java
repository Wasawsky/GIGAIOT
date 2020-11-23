package edu.eci.isib.sparkDocker;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class App {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    public static int restarFechas(Date fechaIn, Date fechaFinal ){
        GregorianCalendar fechaInicio= new GregorianCalendar();
        GregorianCalendar fechaFin= new GregorianCalendar();

        fechaInicio.setTime(fechaIn);
        fechaFin.setTime(fechaFinal);

        int dias = 0;
        if(fechaFin.get(Calendar.YEAR)==fechaInicio.get(Calendar.YEAR)){
            dias =(fechaFin.get(Calendar.DAY_OF_YEAR)- fechaInicio.get(Calendar.DAY_OF_YEAR));
        }else{
            int rangoAnyos = fechaFin.get(Calendar.YEAR) - fechaInicio.get(Calendar.YEAR);
            for(int i=0;i<=rangoAnyos;i++){
                int diasAnio = fechaInicio.isLeapYear(fechaInicio.get(Calendar.YEAR)) ? 366 : 365;
                if(i==0){
                    dias=1+dias +(diasAnio- fechaInicio.get(Calendar.DAY_OF_YEAR));
                }else if(i==rangoAnyos){
                    dias=dias +fechaFin.get(Calendar.DAY_OF_YEAR);
                }else{
                    dias=dias+diasAnio;
                }
            }
        }
        int difHoras =fechaFin.get(Calendar.HOUR_OF_DAY) - fechaInicio.get(Calendar.HOUR_OF_DAY);
        int difMinutos =fechaFin.get(Calendar.MINUTE) - fechaInicio.get(Calendar.MINUTE);
        int difSegundos =fechaFin.get(Calendar.SECOND) - fechaInicio.get(Calendar.SECOND);
        Calendar calDif =Calendar.getInstance();
        calDif.set(Calendar.HOUR_OF_DAY, difHoras);
        calDif.set(Calendar.MINUTE, difMinutos);
        calDif.set(Calendar.SECOND, difSegundos);
        int totalminutos =calDif.get(Calendar.HOUR) * 60 + calDif.get(Calendar.MINUTE);
        int totalsec = totalminutos*60+calDif.get(Calendar.SECOND);

        int total = 0;
        if (dias==0 && totalsec==0){
            total = 0;
        }else if (dias==0){
            total = totalsec;
        }else if (totalsec==0){
            total = dias * 86400;
        }else{
            total = dias * totalsec;
        }
        
        return total;
    }
    public static String getCurrentTime() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
        ZoneId bogotaZoneId = ZoneId.of("America/Bogota");
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime americaZonedDateTime = now.atZone(bogotaZoneId);		
        String fecha = format.format(americaZonedDateTime);
        return fecha;
    }

    public static Date getDate(String dateS){
        Date date = null;
        try {
            SimpleDateFormat formater=new SimpleDateFormat(DATE_FORMAT); 
            date = formater.parse(dateS);
        } catch (Exception e) {
            System.out.println("Error en parseo");
        }
        return date;
        
    }
}
