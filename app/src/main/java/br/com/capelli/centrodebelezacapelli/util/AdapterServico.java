package br.com.capelli.centrodebelezacapelli.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Servico;

/**
 * Created by alexadre on 05/04/17.
 */

public class AdapterServico extends RecyclerView.Adapter<AdapterServico.ViewHolderServico> {

    private ArrayList<Servico> servicos;
    private Context contexto;
    private RecyclerViewOnClickListener listenerOnClick;

    public AdapterServico(ArrayList<Servico> servicos, Context context, RecyclerViewOnClickListener listner){
        this.servicos = servicos;
        this.contexto = context;
        this.listenerOnClick = listner;
    }

    @Override
    public ViewHolderServico onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview_servico, viewGroup, false);
        ViewHolderServico viewHolderServico = new ViewHolderServico(view);

        return viewHolderServico;
    }

    @Override
    public void onBindViewHolder(ViewHolderServico viewHolderServico, int position) {

        Servico servico = servicos.get(position);
        TextView nome = (TextView) viewHolderServico.servicoCardView.findViewById(R.id.itemNome);
        nome.setText(servico.getNome());
        TextView tempoEstimado = (TextView) viewHolderServico.servicoCardView.findViewById(R.id.itemTempoEstimado);
        tempoEstimado.setText(servico.getTempoEstimado());
        TextView valorServico = (TextView) viewHolderServico.servicoCardView.findViewById(R.id.itemValor);
        valorServico.setText(String.valueOf(servico.getValorServico()));
    }

    @Override
    public int getItemCount() {
        return servicos != null ? servicos.size() : 0 ;
    }

    public class ViewHolderServico extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView servicoCardView;

        public ViewHolderServico(View itemView){
            super(itemView);
            servicoCardView = (CardView) itemView.findViewById(R.id.servicoCardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listenerOnClick != null){
                listenerOnClick.onClickListner(servicos.get(getLayoutPosition()));
            }
        }
    }
}
