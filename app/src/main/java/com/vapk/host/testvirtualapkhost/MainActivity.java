package com.vapk.host.testvirtualapkhost;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.didi.virtualapk.PluginManager;
import com.didi.virtualapk.internal.LoadedPlugin;

import java.io.File;
import java.io.FileDescriptor;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String pluginName = "com.vapk.plugin.testvirtualapkplugin_20170904192920.apk";
        final String myPath = this.getFilesDir().getPath() + "/" + pluginName;
        File plugin = new File(myPath);
        if (plugin.exists()) {
            plugin.delete();
        }

        try {
            UtilTools.CopyAssets(MainActivity.this,pluginName,myPath);
            PluginManager.getInstance(this).loadPlugin(plugin);
        } catch (Exception e) {
            e.printStackTrace();
        }



        Button btn = (Button)this.findViewById(R.id.load_apk_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    List<LoadedPlugin> plugins = PluginManager.getInstance(MainActivity.this).getAllLoadedPlugins();
                    LoadedPlugin loadedPlugin = plugins.get(0);


//                    Intent intent = new Intent();
//                    intent.setClassName("com.vapk.plugin.testvirtualapkplugin", "com.vapk.plugin.testvirtualapkplugin.MainActivity");
                    startActivity(loadedPlugin.getLaunchIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
