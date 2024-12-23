package com.haizifang.mirror;

import static com.perfectcorp.perfectlib.developermode.WatermarkBlender.isDeveloperMode;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.Environment;
import android.util.Log;

import com.perfectcorp.perfectlib.Configuration;
import com.perfectcorp.perfectlib.DebugMode;
import com.perfectcorp.perfectlib.Functionality;
import com.perfectcorp.perfectlib.PerfectLib;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class WMSdkManager {
    final String TAG = "WMSdkManager";

   public static WMSdkManager instance = new WMSdkManager();

    private Context context;

    private InitCallback initCallback;

    private WMSdkManager(){};

    public void init(Context context,InitCallback initCallback){
        this.context = context;
        this.initCallback = initCallback;
        enableHttpCache();
        setupLog();
        initWm();
    }

    private void enableHttpCache() {
        Log.d(TAG, "[enableHttpCache]");
        File httpCacheDir = new File(context.getCacheDir(), "http");
        long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
        try {
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
            Log.d(TAG, "[enableHttpCache] succeed.");
        } catch (IOException e) {
            Log.e(TAG, "[enableHttpCache] failed.", e);
        }
    }

    private void setupLog() {
        File logFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "sdk_log");
        if (!logFolder.exists()) {
            logFolder.mkdirs();
        } else if (!logFolder.isDirectory()) {
            logFolder.delete();
            logFolder.mkdir();
        }

        PerfectLib.setDebugMode(
                DebugMode.builder()
                        .enableLogcat(Log.VERBOSE)
                        .enableFileLogger(logFolder, Log.ERROR)
                        .build()
        );
    }

    private void initWm() {
        Configuration.ImageSource imageSource = Configuration.ImageSource.values()[Configuration.ImageSource.FILE.ordinal()];
        Configuration configuration = Configuration
                .builder()
                .setModelPath(PerfectLib.ModelPath.assets("model"))
                .setImageSource(imageSource)
                .setDeveloperMode(isDeveloperMode)
                .setMappingMode(true)
                .setPreviewMode(true)
                .build();
        PerfectLib.init(context.getApplicationContext(), configuration, new PerfectLib.InitialCallback() {
            @Override
            public void onInitialized(Set<Functionality> availableFunctionalities, Map<String, Throwable> preloadErrors) {
                Log.d(TAG, "SDK initialized. preload error=" + preloadErrors);

                // Set content language here. Refer to API References.
                PerfectLib.setLocaleCode("zh");
                // Set country here. Refer to API References.
                PerfectLib.setCountryCode("CN");
                initCallback.initSuccess();
            }

            @Override
            public void onFailure(Throwable throwable, Map<String, Throwable> preloadErrors) {
                Log.e(TAG, "SDK init failed. preload error=" + preloadErrors, throwable);
                initCallback.initFailed();
            }
        });
    }

    public interface InitCallback{

        void initSuccess();

        void initFailed();
    }
}
