package com.grupoprominente.viatify.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import com.grupoprominente.viatify.BuildConfig;

public class PermissionUtil
{
    public static boolean hasPermissions(Context context, String[] permissions)
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        boolean granted = true;

        if(permissions != null)
        {
            for(int i=0; i<permissions.length; i++)
            {
                granted = granted && ActivityCompat.checkSelfPermission(context, permissions[i]) == PackageManager.PERMISSION_GRANTED;
            }
        }

        return granted;
    }

    public static void openSettings(Context context)
    {
        // Build intent that displays the App settings screen.
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}