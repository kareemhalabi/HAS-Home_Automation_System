package ca.mcgill.ecse321.android_has_v3;

import android.app.Application;
import android.os.Environment;
import android.view.MenuItem;

import ca.mcgill.ecse321.HAS.persistence.PersistenceHAS;

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
