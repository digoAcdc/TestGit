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
import br.com.barbosa.rodrigo.testgit.activity.model.File;
import br.com.barbosa.rodrigo.testgit.activity.model.Gist;



public class GistAdapter extends RecyclerView.Adapter<GistAdapter.CustomViewHolder> {


    private Context context;
    private List<Gist> mLstGist;
    private final GistOnClickListener onClickListener;

    public GistAdapter(Context context, GistOnClickListener onClickListener) {
        this.context = context;
        this.mLstGist = new ArrayList<>();
        this.onClickListener = onClickListener;
    }


    public interface GistOnClickListener {
        public void OnClick(View view, int index);
    }

    public void addAll( List<Gist> lstGist){
        mLstGist.addAll(lstGist);
        notifyDataSetChanged();
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


        holder.tvNome.setText(f.getFilename());
        holder.tvTitulo.setText(e.getOwner().getLogin());
        holder.tvIdioma.setText(f.getLanguage());

        holder.view.setTag(e.getId());

        Picasso.with(context).load(e.getOwner().getAvatarUrl()).into(holder.imvAvatar);
        if (onClickListener != null) {

            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClick(holder.view, position);
                }
            });

        }

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


        private View view;

        public CustomViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvTitulo = (TextView) view.findViewById(R.id.tvTitulo);
            this.tvNome = (TextView) view.findViewById(R.id.tvNome);
            this.tvIdioma = (TextView) view.findViewById(R.id.tvIdioma);
            this.imvAvatar = (ImageView) view.findViewById(R.id.imvAvatar);


        }

    }
}
