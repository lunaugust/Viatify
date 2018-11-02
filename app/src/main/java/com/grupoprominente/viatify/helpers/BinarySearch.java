package com.grupoprominente.viatify.helpers;

import com.grupoprominente.viatify.model.Organizations;
import com.grupoprominente.viatify.model.ServiceLine;
import com.grupoprominente.viatify.model.SubOrganization;
import com.grupoprominente.viatify.model.SubOrganizations;

import java.util.List;

public class BinarySearch {
    // Returns index of x if it is present in arr[],
    // else return -1
    public static int serviceLinePosition(List<ServiceLine> serviceLineList, int x)
    {
        int l = 0, r = serviceLineList.size() - 1;
        while (l <= r)
        {
            int m = l + (r-l)/2;

            // Check if x is present at mid
            if (serviceLineList.get(m).getId() == x)
                return m;

            // If x greater, ignore left half
            if (serviceLineList.get(m).getId() < x)
                l = m + 1;

                // If x is smaller, ignore right half
            else
                r = m - 1;
        }

        // if we reach here, then element was
        // not present
        return -1;
    }
    // Returns index of x if it is present in arr[],
    // else return -1
    public static int SubOrgPosition(List<SubOrganizations> subOrganizationsList, int x)
    {
        int l = 0, r = subOrganizationsList.size() - 1;
        while (l <= r)
        {
            int m = l + (r-l)/2;

            // Check if x is present at mid
            if (subOrganizationsList.get(m).getId() == x)
                return m;

            // If x greater, ignore left half
            if (subOrganizationsList.get(m).getId() < x)
                l = m + 1;

                // If x is smaller, ignore right half
            else
                r = m - 1;
        }

        // if we reach here, then element was
        // not present
        return -1;
    }
    // Returns index of x if it is present in arr[],
    // else return -1
    public static int OrganizationPosition(List<Organizations> organizationsList, int x)
    {
        int l = 0, r = organizationsList.size() - 1;
        while (l <= r)
        {
            int m = l + (r-l)/2;

            // Check if x is present at mid
            if (organizationsList.get(m).getId() == x)
                return m;

            // If x greater, ignore left half
            if (organizationsList.get(m).getId() < x)
                l = m + 1;

                // If x is smaller, ignore right half
            else
                r = m - 1;
        }

        // if we reach here, then element was
        // not present
        return -1;
    }

}
