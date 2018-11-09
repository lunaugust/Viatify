package com.grupoprominente.viatify.data;

import com.grupoprominente.viatify.model.Organizations;

import java.util.ArrayList;
import java.util.List;

public class OrganizationsData {
    public static List<Organizations> getAll(){
        String[] arrayOrg = {"Aplicaciones", "Infraestructura", "PMO", "Tecnología", "Comercial", "Back Office", "RRHH", "Mantenimiento", "Gerencia General", "Presidencia"};
        int id = 1;
        List<Organizations> organizationsList = new ArrayList<>();
        for (String description : arrayOrg) {
            organizationsList.add(new Organizations(id, description));
            id++;
        }
        return organizationsList;
    }
}
