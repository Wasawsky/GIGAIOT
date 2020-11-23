package edu.eci.isib.sparkDocker;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
Programa que usa la fecha de la maquina y la 
convierte en la fecha de Bogota/America
**/
public class prueba2 {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static void main(String[] args) {	
		DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
        ZoneId bogotaZoneId = ZoneId.of("America/Bogota");
		LocalDateTime now = LocalDateTime.now();
		ZonedDateTime americaZonedDateTime = now.atZone(bogotaZoneId);		
        String fecha = format.format(americaZonedDateTime);
        System.out.println(fecha);
    }
}