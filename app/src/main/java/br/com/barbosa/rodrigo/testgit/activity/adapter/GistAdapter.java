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
import br.com.barbosa.rodrigo.testgit.activity.model.File;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;


public class GistAdapter extends RecyclerView.Adapter<GistAdapter.CustomViewHolder> {


    private Context context;
    private List<Gist> mLstGist;
    private final GistOnClickListener onClickListener;
    private final DBHelper db;
    private final List<Favorito> favoritos;


    public GistAdapter(Context context, GistOnClickListener onClickListener) {
        this.context = context;
        this.mLstGist = new ArrayList<>();
        this.onClickListener = onClickListener;
        this.db = new DBHelper(context);
        favoritos = db.getAllFavoritos();
    }


    public interface GistOnClickListener {
        public void OnClick(View view, int index);

        public void OnClickFavorito(View view, int index);
    }

    public void addAll(List<Gist> lstGist) {
        mLstGist.addAll(lstGist);
        notifyDataSetChanged();
    }

    public Gist getWithIndex(int index) {
        return mLstGist.get(index);
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_gits, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        Gist e = mLstGist.get(position);
        File f = (File) e.getFiles().values().toArray()[0];


        holder.tvNome.setText(context.getString(R.string.nome, f.getFilename()));
        if (e.getOwner() != null)
            holder.tvTitulo.setText(e.getOwner().getLogin() == null ? "" : e.getOwner().getLogin());
        holder.tvIdioma.setText(context.getString(R.string.idioma, f.getLanguage()));

        holder.view.setTag(position);
        holder.imvFavorito.setTag(false);

        if(verifyIsFavorite(e.getId()))
            holder.imvFavorito.setImageResource(R.drawable.ic_favorito);
        else
            holder.imvFavorito.setImageResource(R.drawable.ic_nao_favoritado);

        if (e.getOwner() != null)
            if (e.getOwner().getAvatarUrl() != null) {
                Picasso.with(context).load(e.getOwner().getAvatarUrl()).into(holder.imvAvatar);
            }


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
        return mLstGist.size();
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
