package lukin.recepti.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import lukin.recepti.R;
import lukin.recepti.adapter.JeloAdapter;
import lukin.recepti.adapter.JeloClickListener;
import lukin.recepti.model.Jelo;
import lukin.recepti.viewmodel.JeloViewModel;


public class ReadFragment extends Fragment {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private JeloViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getModel();

        definirajListu();
        definirajSwipe();
        osvjeziPodatke();


        return view;
    }

    private void osvjeziPodatke(){
        model.dohvatiJela().observe(this, new Observer<List<Jelo>>() {
            @Override
            public void onChanged(@Nullable List<Jelo> jela) {
                 swipeRefreshLayout.setRefreshing(false);
                ((JeloAdapter)listView.getAdapter()).setPodaci(jela);
                ((JeloAdapter) listView.getAdapter()).osvjeziPodatke();

            }
        });
    }
    private void definirajSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                osvjeziPodatke();
            }
        });

    }

    private void definirajListu() {

        listView.setAdapter( new JeloAdapter(getActivity(), R.layout.red_liste, new JeloClickListener() {
            @Override
            public void onItemClick(Jelo jelo) {
                model.setJelo(jelo);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void novoJelo(){
        model.setJelo(new Jelo());
        ((MainActivity)getActivity()).cud();
    }


}