package com.grupoprominente.viatify.data;

import com.grupoprominente.viatify.model.SubOrganizations;

import java.util.ArrayList;
import java.util.List;

public class SubOrganizationsData {
    public static List<SubOrganizations> getAll() {
        List<SubOrganizations> subOrganizationsList = new ArrayList<>();
        subOrganizationsList.add(new SubOrganizations(1,"Solucion de sw./Producto",1));
        subOrganizationsList.add(new SubOrganizations(2,"Servicio al Cliente/Soporte de Aplicaciones",1));
        subOrganizationsList.add(new SubOrganizations(3,"Proyecto de SW./ Desarrollo de Aplicaciones",1));
        subOrganizationsList.add(new SubOrganizations(4,"Monitoreo/MDA",2));
        subOrganizationsList.add(new SubOrganizations(5,"Operaciones de Servicio - DATA CENTER",2));
        subOrganizationsList.add(new SubOrganizations(6,"Soporte Tecnico - Infraestructura",2));
        subOrganizationsList.add(new SubOrganizations(7,"Planificacion y Asesoramiento - Servicio Infraestructura",2));
        subOrganizationsList.add(new SubOrganizations(8,"Calidad/PMO",3));
        subOrganizationsList.add(new SubOrganizations(9,"OFICINA DE PROYECTOS",3));
        subOrganizationsList.add(new SubOrganizations(10,"BRA",4));
        subOrganizationsList.add(new SubOrganizations(11,"BRH",4));
        subOrganizationsList.add(new SubOrganizations(12,"MTV",4));
        subOrganizationsList.add(new SubOrganizations(13,"CORREDORES",4));
        subOrganizationsList.add(new SubOrganizations(14,"BCyL/BRD/SOFSE",4));
        subOrganizationsList.add(new SubOrganizations(15,"MONEDERO",4));
        subOrganizationsList.add(new SubOrganizations(16,"Preventa",4));
        subOrganizationsList.add(new SubOrganizations(17,"TRADITUM",4));
        subOrganizationsList.add(new SubOrganizations(18,"GESTION DE PROCESO DE NEGOCIOS",4));
        subOrganizationsList.add(new SubOrganizations(19,"INGENIERIA INFORMATICA",4));
        subOrganizationsList.add(new SubOrganizations(20,"Ventas",5));
        subOrganizationsList.add(new SubOrganizations(21,"MKT",5));
        subOrganizationsList.add(new SubOrganizations(22,"RRII",5));
        subOrganizationsList.add(new SubOrganizations(23,"PREVENTA",5));
        subOrganizationsList.add(new SubOrganizations(24,"Adm. Y Fin.",6));
        subOrganizationsList.add(new SubOrganizations(25,"Adm. De ventas y contrataciones",6));
        subOrganizationsList.add(new SubOrganizations(26,"RECURSOS HUMANOS",7));
        subOrganizationsList.add(new SubOrganizations(27,"Cordoba",8));
        subOrganizationsList.add(new SubOrganizations(28,"Buenos Aires",8));
        subOrganizationsList.add(new SubOrganizations(29,"Gerencia",9));
        subOrganizationsList.add(new SubOrganizations(30,"Presidencia",10));

        return subOrganizationsList;
    }
}
