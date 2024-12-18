package com.auty.modules.applets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;


//applet that opens up music apps on your phone
//two functions launchMusic and containsPackage
public class MusicApplet extends Applet {

    private Context context;

    public MusicApplet(Context context) {
        super("MusicApp", "config");
        this.context = context;
    }

    public void launchMusic() {

        //intent for apps that identify as music apps
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_MUSIC);



        //intent looks for apps that handles audio files
        Intent alternateIntent = new Intent(Intent.ACTION_VIEW);
        alternateIntent.setType("audio/*");


        //gathers list of music apps
        List<ResolveInfo> musicApps = new ArrayList<>();


        //queries list for apps that can handle intent
        List<ResolveInfo> mainMusicApps = context.getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        musicApps.addAll(mainMusicApps);


        List<ResolveInfo> audioApps = context.getPackageManager()
                .queryIntentActivities(alternateIntent, PackageManager.MATCH_DEFAULT_ONLY);


        for (ResolveInfo app : audioApps) {
            if (!containsPackage(musicApps, app.activityInfo.packageName)) {
                musicApps.add(app);
            }
        }

        if (!musicApps.isEmpty()) {

            Intent chooserIntent = Intent.createChooser(intent, "Which app would you like to open?");


            chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(chooserIntent);
        }
    }

    private boolean containsPackage(List<ResolveInfo> list, String packageName) {
        for (ResolveInfo info : list) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

}