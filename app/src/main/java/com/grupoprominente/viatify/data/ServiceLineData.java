package com.grupoprominente.viatify.data;

import com.grupoprominente.viatify.model.ServiceLines;

import java.util.ArrayList;
import java.util.List;

public class ServiceLineData {
    public static List<ServiceLines> getAll() {
        List<ServiceLines> serviceLinesList = new ArrayList<>();
        serviceLinesList.add(new ServiceLines(1,"Petra BPM",1));
        serviceLinesList.add(new ServiceLines(2,"Hub",1));
        serviceLinesList.add(new ServiceLines(3,"Mr Bubo",1));
        serviceLinesList.add(new ServiceLines(4,"PREM",1));
        serviceLinesList.add(new ServiceLines(5,"Consultoria BI",2));
        serviceLinesList.add(new ServiceLines(6,"Consultoria de Pectra BPM",2));
        serviceLinesList.add(new ServiceLines(7,"Consultoria de PREM",2));
        serviceLinesList.add(new ServiceLines(8,"Consultoria de Mr. Bubo",2));
        serviceLinesList.add(new ServiceLines(9,"Consultoria de O365",2));
        serviceLinesList.add(new ServiceLines(10,"Analista Funcional",2));
        serviceLinesList.add(new ServiceLines(11,"Mant SW.",2));
        serviceLinesList.add(new ServiceLines(12,"Proyecto de SW.",3));
        serviceLinesList.add(new ServiceLines(13,"Monitoreo de Servicios",4));
        serviceLinesList.add(new ServiceLines(14,"Mesa de Ayuda",4));
        serviceLinesList.add(new ServiceLines(15,"Servicio de Data Center",5));
        serviceLinesList.add(new ServiceLines(16,"Consultoria de Infraestructura",5));
        serviceLinesList.add(new ServiceLines(17,"Soporte Tecnico",6));
        serviceLinesList.add(new ServiceLines(18,"Servicios Profesionales On Site",6));
        serviceLinesList.add(new ServiceLines(19,"Gestion de Proyectos y Servicios",8));
        serviceLinesList.add(new ServiceLines(20,"Gestion de Outsorcing",10));
        serviceLinesList.add(new ServiceLines(21,"Gestion de Outsorcing",11));
        serviceLinesList.add(new ServiceLines(22,"Gestion de Outsorcing",12));
        serviceLinesList.add(new ServiceLines(23,"Gestion de Outsorcing",13));
        serviceLinesList.add(new ServiceLines(24,"Gestion de Outsorcing",14));
        serviceLinesList.add(new ServiceLines(25,"Gestion de Outsorcing",15));
        serviceLinesList.add(new ServiceLines(26,"Gestion de Outsorcing",16));
        serviceLinesList.add(new ServiceLines(27,"Gestion de Outsorcing",17));
        serviceLinesList.add(new ServiceLines(28,"Gestion de Outsorcing",18));
        serviceLinesList.add(new ServiceLines(29,"Gestion de Outsorcing",19));
        serviceLinesList.add(new ServiceLines(30,"Ventas",20));
        serviceLinesList.add(new ServiceLines(31,"MKT",21));
        serviceLinesList.add(new ServiceLines(32,"RRII",22));
        serviceLinesList.add(new ServiceLines(33,"Admin. y Fin.",24));
        serviceLinesList.add(new ServiceLines(34,"Adm. De ventas y contrataciones",25));
        serviceLinesList.add(new ServiceLines(35,"RECURSOS HUMANOS",26));
        serviceLinesList.add(new ServiceLines(36,"Cordoba",27));
        serviceLinesList.add(new ServiceLines(37,"Buenos Aires",28));
        serviceLinesList.add(new ServiceLines(38,"Gerencia General",29));
        serviceLinesList.add(new ServiceLines(39,"Presidencia",30));

        return serviceLinesList;
    }
}
