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
