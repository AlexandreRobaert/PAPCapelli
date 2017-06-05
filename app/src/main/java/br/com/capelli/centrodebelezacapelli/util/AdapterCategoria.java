package br.com.capelli.centrodebelezacapelli.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Categoria;

/**
 * Created by alexadre on 05/04/17.
 */

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.ViewHolderCategoria> {

    private ArrayList<Categoria> categorias;
    private Context context;
    private RecyclerViewOnClickListener listenerOnClick;

    public AdapterCategoria(ArrayList<Categoria> categorias, Context context, RecyclerViewOnClickListener listner){
        this.categorias = categorias;
        this.context = context;
        listenerOnClick = listner;
    }

    @Override
    public ViewHolderCategoria onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview_categoria, viewGroup, false);
        ViewHolderCategoria viewHolderCategoria = new ViewHolderCategoria(view);

        return viewHolderCategoria;
    }

    @Override
    public void onBindViewHolder(ViewHolderCategoria viewHolder, int position) {

        Categoria categoria = categorias.get(position);
        TextView nomeServico = (TextView) viewHolder.categoriaCardView.findViewById(R.id.nomeServicoTextView);
        nomeServico.setText(categoria.getNome());
        ImageView imagem = (ImageView) viewHolder.categoriaCardView.findViewById(R.id.imagenImageView);
        imagem.setBackground(context.getResources().getDrawable(R.drawable.cabelo_corte));
    }

    @Override
    public int getItemCount() {
        return categorias != null ? categorias.size() : 0 ;
    }

    public class ViewHolderCategoria extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView categoriaCardView;

        public ViewHolderCategoria(View itemView){
            super(itemView);
            categoriaCardView = (CardView) itemView.findViewById(R.id.categoriaCardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listenerOnClick != null){
                listenerOnClick.onClickListner(categorias.get(getLayoutPosition()));
            }
        }
    }
}
