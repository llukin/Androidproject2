package lukin.recepti.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import lukin.recepti.R;
import lukin.recepti.viewmodel.JeloViewModel;

public class MainActivity extends AppCompatActivity {

    private JeloViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this).get(JeloViewModel.class);
        read();
    }

    public void read(){
        setFragment( new ReadFragment());
    }

    public void cud(){
        setFragment(new CUDFragment());
    }

    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }

    public JeloViewModel getModel() {
        return model;
    }
}
