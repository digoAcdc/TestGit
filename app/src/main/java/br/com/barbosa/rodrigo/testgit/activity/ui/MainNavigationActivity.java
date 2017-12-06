package br.com.barbosa.rodrigo.testgit.activity.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.ui.fragment.FavoritosFragment;
import br.com.barbosa.rodrigo.testgit.activity.ui.fragment.GistFragment;
import br.com.barbosa.rodrigo.testgit.activity.ui.fragment.SobreFragment;

public class MainNavigationActivity extends AppCompatActivity {


    private LinearLayout containerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);
        containerFragment = (LinearLayout)findViewById(R.id.containerFragment);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        updateViewFragment(new GistFragment(), containerFragment);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    updateViewFragment(new GistFragment(), containerFragment);
                    return true;
                case R.id.navigation_Favoritos:
                    updateViewFragment(new FavoritosFragment(), containerFragment);
                    return true;
                case R.id.navigation_sobre:
                    updateViewFragment(new SobreFragment(), containerFragment);
                    return true;
            }
            return false;
        }
    };


    private void updateViewFragment(Fragment fragment, LinearLayout container) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(container.getId(), fragment)
                    .disallowAddToBackStack()
                    .commit();
        } catch (Exception ex) {
            Log.i("ERRO FRAGMENT", ex.getMessage());
        }
    }
}
