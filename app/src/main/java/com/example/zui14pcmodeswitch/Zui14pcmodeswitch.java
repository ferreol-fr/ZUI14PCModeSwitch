package com.example.zui14pcmodeswitch;

import static android.Manifest.permission.WRITE_SETTINGS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

public class Zui14pcmodeswitch extends Activity {

    @Override
    @SuppressLint("Recycle")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        boolean isGranted;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Context context = this.getApplicationContext();
            ContentResolver contentResolver = context.getContentResolver();
            contentResolver.query(Uri.parse("content://settings/system"), null, null, null);
            isGranted = checkPermission(context);
            if (isGranted) {
                ContentValues contentValues = new ContentValues(2);
                contentValues.put("name", "zui_pc_mode");
                String value = Settings.System.getString(contentResolver, "zui_pc_mode");
                if (value.equals("0")) {
                    contentValues.put("value", "1");
                } else {
                    contentValues.put("value", "0");
                }
                contentResolver.insert(Uri.parse("content://settings/system"), contentValues);
            }
        }
    }


    /**
     * Check whether the permission has been granted
     *
     * @return {@code true} if granted, and {@code false} otherwise
     **/
    public boolean checkPermission(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(context)) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                        .setData(Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                startActivity(intent);
                onActivityResult(WRITE_SETTINGS.hashCode(), WRITE_SETTINGS.hashCode(), intent);
                return false;
            } catch (Exception ignore) {

            }
            return false;
        }
        return true;
    }
}