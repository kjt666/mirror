package com.haizifang.mirror.common.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {
    public static String loadJSONFromAsset(Context context,String jsonFileName) {
        String json = "";
        try {
            InputStream is = context.getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}