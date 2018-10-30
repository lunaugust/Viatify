package com.grupoprominente.viatify.data;

import android.content.Context;

import com.grupoprominente.viatify.model.ServiceLine;

import java.util.List;

public class ServiceLineSerializer extends ObjectSerializer
{
    //User file name
    private final static String FILE_NAME = "ServiceLines.dat";

    //Singleton instance
    private static ServiceLineSerializer serviceLineSerializer;

    private ServiceLineSerializer()
    {
        //Prevent initialization from the reflection api.
        if(serviceLineSerializer != null)
            throw new RuntimeException("Use getInstance() method to get the single instance of this class");
    }

    public static synchronized ServiceLineSerializer getInstance()
    {
        if(serviceLineSerializer == null)
            serviceLineSerializer = new ServiceLineSerializer();

        return serviceLineSerializer;
    }

    public void save(Context context, List<ServiceLine> lstServiceLine)
    {
        save(context, FILE_NAME, lstServiceLine);
    }

    public  List<ServiceLine> load(Context context)
    {
        return (List<ServiceLine>) load(context, FILE_NAME);
    }

    public void clear(Context context)
    {
        save(context, null);
    }
}
