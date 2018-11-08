package com.grupoprominente.viatify.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.grupoprominente.viatify.data.LoginSerializer;
import com.grupoprominente.viatify.data.OrganizationsData;
import com.grupoprominente.viatify.data.ServiceLineData;
import com.grupoprominente.viatify.data.ServiceLineSerializer;
import com.grupoprominente.viatify.data.SubOrganizationsData;
import com.grupoprominente.viatify.helpers.BinarySearch;
import com.grupoprominente.viatify.model.LoginResponse;
import com.grupoprominente.viatify.model.Organization;
import com.grupoprominente.viatify.model.Organizations;
import com.grupoprominente.viatify.model.ServiceLine;
import com.grupoprominente.viatify.model.ServiceLines;
import com.grupoprominente.viatify.model.SubOrganization;
import com.grupoprominente.viatify.model.SubOrganizations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginResponse lResponse = LoginSerializer.getInstance().load(SplashActivity.this);
        List<ServiceLine> lstServiceLine = ServiceLineSerializer.getInstance().load(SplashActivity.this);
        if(lstServiceLine != null){
            lstServiceLine.clear();
        }
        else{
            saveServiceLines();
        }
        if (lResponse != null) {
            Date date = lResponse.getDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 29);
            long diff  = cal.getTime().getTime() - new Date().getTime();
            long days = TimeUnit.DAYS.convert(diff , TimeUnit.MILLISECONDS);
            if(days > 1){

                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }
            else{
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }
        else{
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }


    }
    private void saveServiceLines(){
        List<ServiceLines> lstServiceLines;
        List<SubOrganizations> lstSubOrganizations;
        List<Organizations> lstOrganizations;
        List<ServiceLine> lstServiceLine = new ArrayList<>();
        lstServiceLines = ServiceLineData.getAll();
        lstSubOrganizations = SubOrganizationsData.getAll();
        lstOrganizations = OrganizationsData.getAll();

        for (ServiceLines service_lines: lstServiceLines) {
            int position = BinarySearch.SubOrgPosition(lstSubOrganizations, service_lines.getSub_org_id());
            SubOrganizations subOrgs = lstSubOrganizations.get(position);
            position = BinarySearch.OrganizationPosition(lstOrganizations, subOrgs.getOrg_id());
            Organizations orgs = lstOrganizations.get(position);
            Organization org = new Organization(orgs.getId(), orgs.getTitle());
            SubOrganization subOrg = new SubOrganization(subOrgs.getId(),subOrgs.getTitle(), org);
            ServiceLine serviceLine = new ServiceLine(service_lines.getId(),service_lines.getTitle(), subOrg);
            lstServiceLine.add(serviceLine);
        }
        if (lstServiceLine.size() > 0){
            ServiceLineSerializer.getInstance().save(SplashActivity.this, lstServiceLine);
        }
    }
}
