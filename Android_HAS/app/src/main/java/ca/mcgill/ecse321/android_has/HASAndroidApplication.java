package ca.mcgill.ecse321.android_has;

import android.app.Application;
import android.os.Environment;
import android.view.MenuItem;

import ca.mcgill.ecse321.HAS.persistence.PersistenceHAS;
/**
 * ECSE 321 Group 5:
 * Kareem Halabi
 * Alexander Orzechowski
 * Kristina Pearkes
 * Aidan Piwowar
 * Aurélie Pluche
 *
 * This application has been tested on a physical Samsung Galaxy S6 running Android 5.1.1 
 * It has also been tested on a Genymotion emulator running Android 5.1.1
 *
 * This application was compiled on Android studio 2.0, if you are using a previous version
 * change the version in this Project's build.gradle
 */

public class HASAndroidApplication extends Application {

    private static MenuItem currentMenu;

    @Override
    public void onCreate() {
        PersistenceHAS.setFileName(
                Environment.getExternalStorageDirectory().getPath() + "/HAS.xml");
        PersistenceHAS.loadHASModel();

        super.onCreate();
    }

    public static MenuItem getCurrentMenu() {
        return currentMenu;
    }

    public static void setCurrentMenu(MenuItem theCurrentMenu) {
        currentMenu = theCurrentMenu;
    }
}
