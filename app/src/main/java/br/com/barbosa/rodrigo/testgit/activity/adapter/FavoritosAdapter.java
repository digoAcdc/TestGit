package br.com.barbosa.rodrigo.testgit.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.barbosa.rodrigo.testgit.R;
import br.com.barbosa.rodrigo.testgit.activity.data.DBHelper;
import br.com.barbosa.rodrigo.testgit.activity.model.Favorito;


public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.CustomViewHolder> {


    private Context context;
    private List<Favorito> mLstFavoritos;
    private final FavoritosAdapter.FavoritosOnClickListener onClickListener;
    private final DBHelper db;
    private final List<Favorito> favoritos;


    public FavoritosAdapter(Context context, FavoritosAdapter.FavoritosOnClickListener onClickListener) {
        this.context = context;
        this.mLstFavoritos = new ArrayList<>();
        this.onClickListener = onClickListener;
        this.db = new DBHelper(context);
        favoritos = db.getAllFavoritos();
    }


    public interface FavoritosOnClickListener {
        public void OnClick(View view, int index);

        public void OnClickFavorito(View view, int index);
    }

    public void addAll(List<Favorito> lstFavoritos) {
        mLstFavoritos.addAll(lstFavoritos);
        notifyDataSetChanged();
    }

    public void clean(){
        mLstFavoritos.clear();
        notifyDataSetChanged();
    }

    public Favorito getWithIndex(int index) {
        return mLstFavoritos.get(index);
    }


    @Override
    public FavoritosAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_gits, parent, false);
        FavoritosAdapter.CustomViewHolder holder = new FavoritosAdapter.CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final FavoritosAdapter.CustomViewHolder holder, final int position) {

        Favorito favorito = mLstFavoritos.get(position);


        holder.tvNome.setText(context.getString(R.string.nome, favorito.getNome()));

        holder.tvTitulo.setText(favorito.getTitulo() == null ? "Titulo" : favorito.getTitulo());
        holder.tvIdioma.setText(context.getString(R.string.idioma, favorito.getIdioma()));

        holder.view.setTag(position);
        holder.imvFavorito.setTag(true);


        holder.imvFavorito.setImageResource(R.drawable.ic_favorito);

        if (favorito.getImagem() != null)
            Picasso.with(context).load(favorito.getImagem()).into(holder.imvAvatar);


        if (onClickListener != null) {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClick(holder.view, position);
                }
            });
            holder.imvFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickFavorito(holder.view, position);
                }
            });
        }
    }

    public boolean verifyIsFavorite(String id) {
        if (favoritos == null)
            return false;
        else {
            for (Favorito f : favoritos) {
                if (f.getId().equals(id))
                    return true;
            }

        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mLstFavoritos.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo;
        TextView tvNome;
        TextView tvIdioma;
        ImageView imvAvatar;
        ImageView imvFavorito;


        private View view;

        public CustomViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);
            this.tvNome = (TextView) view.findViewById(R.id.tvNome);
            this.tvIdioma = (TextView) view.findViewById(R.id.tvIdioma);
            this.imvAvatar = (ImageView) view.findViewById(R.id.imvAvatar);
            this.imvFavorito = (ImageView) view.findViewById(R.id.imvFavorito);


        }

    }
}
