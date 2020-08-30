package lukin.recepti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import lukin.recepti.R;
import lukin.recepti.model.Jelo;


public class JeloAdapter extends ArrayAdapter<Jelo> {

    private List<Jelo> podaci;
    private JeloClickListener jeloClickListener;
    private int resource;
    private Context context;

    public JeloAdapter(@NonNull Context context, int resource, JeloClickListener jeloClickListener) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.jeloClickListener = jeloClickListener;
    }


    private static class ViewHolder {

        private TextView naziv;
        private ImageView slika;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        Jelo jelo;
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);

                viewHolder.naziv = view.findViewById(R.id.naziv_tip);
                viewHolder.slika = view.findViewById(R.id.slika);
            } else {
                viewHolder = (ViewHolder) view.getTag();

            }

            jelo = getItem(position);

            if (jelo != null) {

                viewHolder.naziv.setText(jelo.getNaziv() + " - " + context.getResources().getStringArray(R.array.vrsta_jela)[jelo.getVrsta()]);

                if (jelo.getSlika() == null) {
                    Picasso.get().load(R.drawable.no_img).fit().centerCrop().into(viewHolder.slika);
                } else {
                    Picasso.get().load(jelo.getSlika()).fit().centerCrop().into(viewHolder.slika);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jeloClickListener.onItemClick(jelo);
                }
            });
        }
        return view;
    }

    @Override
    public int getCount() {
        return podaci == null ? 0 : podaci.size();
    }

    @Nullable
    @Override
    public Jelo getItem(int position) {
        return podaci.get(position);
    }

    public void setPodaci(List<Jelo> jela) {
        this.podaci = jela;
    }

    public void osvjeziPodatke() {
        notifyDataSetChanged();
    }

}
