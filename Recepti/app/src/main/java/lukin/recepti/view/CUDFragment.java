package lukin.recepti.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lukin.recepti.R;
import lukin.recepti.viewmodel.JeloViewModel;


public class CUDFragment extends Fragment {

    static final int SLIKANJE =1;

    private String putanjaSlike;

    @BindView(R.id.naziv)
    EditText naziv;
    @BindView(R.id.vrsta_jela)
    Spinner vrstaJela;
    @BindView(R.id.priprema)
    EditText priprema;
    @BindView(R.id.slika)
    ImageView slika;
    @BindView(R.id.novoJelo)
    Button novoJelo;
    @BindView(R.id.uslikaj)
    Button uslikaj;
    @BindView(R.id.promjenaJela)
    Button promjenaJela;
    @BindView(R.id.obrisiJelo)
    Button obrisiJelo;

    JeloViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,
                container, false);
        ButterKnife.bind(this, view);

        model = ((MainActivity) getActivity()).getModel();

        if (model.getJelo().getId() == 0) {
            definirajNovoJelo();
            return view;
        }
        definirajPromjenaBrisanjeJela();

        return view;
    }

    private void definirajPromjenaBrisanjeJela() {
        novoJelo.setVisibility(View.GONE);
        vrstaJela.setSelection(model.getJelo().getVrsta());
        naziv.setText(model.getJelo().getNaziv());
        priprema.setText(model.getJelo().getPriprema());

        Picasso.get().load(model.getJelo().getSlika()).error(R.drawable.no_img).into(slika);

        uslikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uslikaj();
            }
        });

        promjenaJela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promjenaJela();
            }
        });

        obrisiJelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obrisiJelo();
            }
        });


    }

    private void definirajNovoJelo() {
        promjenaJela.setVisibility(View.GONE);
        obrisiJelo.setVisibility(View.GONE);
        uslikaj.setVisibility(View.GONE);
        novoJelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novoJelo();
            }
        });
    }

    private void novoJelo() {
        model.getJelo().setNaziv(naziv.getText().toString());
        model.getJelo().setVrsta(vrstaJela.getSelectedItemPosition());
        model.getJelo().setPriprema(priprema.getText().toString());
        model.dodajNovoJelo();
        nazad();
    }
    private void promjenaJela() {
        model.getJelo().setNaziv(naziv.getText().toString());
        model.getJelo().setVrsta(vrstaJela.getSelectedItemPosition());
        model.getJelo().setPriprema(priprema.getText().toString());
        model.promjeniJelo();
        nazad();
    }

    private void obrisiJelo() {
        model.getJelo().setNaziv(naziv.getText().toString());
        model.getJelo().setVrsta(vrstaJela.getSelectedItemPosition());
        model.getJelo().setPriprema(priprema.getText().toString());
        model.obrisiJelo();
        nazad();
    }

    @OnClick(R.id.nazad)
    public void nazad() {
        ((MainActivity) getActivity()).read();
    }

    private void uslikaj() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) == null) {
            Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;

        }

            File slika = null;
            try {
                slika = kreirajDatotekuSlike();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
            return;
            }

            if (slika == null) {
                Toast.makeText(getActivity(), "Problem kod kreiranja slike", Toast.LENGTH_LONG).show();
                return;
            }

            Uri slikaURI = FileProvider.getUriForFile(getActivity(),"lukin.recepti.provider", slika);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, slikaURI);
            startActivityForResult(takePictureIntent, SLIKANJE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SLIKANJE && resultCode == Activity.RESULT_OK) {

            model.getJelo().setSlika("file://" + putanjaSlike);
            model.promjeniJelo();
            Picasso.get().load(model.getJelo().getSlika()).into(slika);

        }
    }

    private File kreirajDatotekuSlike() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imeSlike = "jelo_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imeSlike,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        putanjaSlike = image.getAbsolutePath();
        return image;
    }

}