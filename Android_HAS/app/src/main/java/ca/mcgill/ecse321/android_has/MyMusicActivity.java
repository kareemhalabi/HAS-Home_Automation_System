package ca.mcgill.ecse321.android_has;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ca.mcgill.ecse321.android_has.albums.AlbumNavFragment;
import ca.mcgill.ecse321.android_has.artists.ArtistNavFragment;
import ca.mcgill.ecse321.android_has.playlists.PlaylistNavFragment;
import ca.mcgill.ecse321.android_has.roomgroups.RoomGroupNavFragment;
import ca.mcgill.ecse321.android_has.rooms.RoomNavFragment;
import ca.mcgill.ecse321.android_has.songs.SongNavFragment;

public class MyMusicActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //auto generated code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Sets the artist view as the default on startup
        if(HASAndroidApplication.getCurrentMenu() == null)
            HASAndroidApplication.setCurrentMenu(navigationView.getMenu().getItem(0));
        HASAndroidApplication.getCurrentMenu().setChecked(true);
        onNavigationItemSelected(HASAndroidApplication.getCurrentMenu());
    }

    @Override
    public void onResume() {
        super.onResume();
        HASAndroidApplication.getCurrentMenu().setChecked(true);
        onNavigationItemSelected(HASAndroidApplication.getCurrentMenu());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        HASAndroidApplication.setCurrentMenu(item);

        int id = item.getItemId();
        Fragment currentFragment = null;

        switch (id){
            case R.id.nav_artists:
                currentFragment = new ArtistNavFragment();
                setTitle("Artists");
                break;

            case R.id.nav_albums:
                currentFragment = new AlbumNavFragment();
                setTitle("Albums");
                break;

            case R.id.nav_songs:
                currentFragment = new SongNavFragment();
                setTitle("Songs");
                break;

            case R.id.nav_playlists:
                currentFragment = new PlaylistNavFragment();
                setTitle("Playlists");
                break;

            case R.id.nav_rooms:
                currentFragment = new RoomNavFragment();
                setTitle("Rooms");
                break;

            case R.id.nav_room_groups:
                currentFragment = new RoomGroupNavFragment();
                setTitle("Room Groups");
                break;

            case R.id.nav_about:
                currentFragment = new AboutNavFragment();
                setTitle("About");
                break;
        }

        //sets the new currentFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_container, currentFragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}