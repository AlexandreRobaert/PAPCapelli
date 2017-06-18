package br.com.capelli.centrodebelezacapelli.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Agendamento;
import br.com.capelli.centrodebelezacapelli.model.Servico;

/**
 * Created by alexadre on 05/04/17.
 */

public class AdapterServicoPrestador extends RecyclerView.Adapter<AdapterServicoPrestador.ViewHolderServico> {

    private ArrayList<Agendamento> agendamentos;
    private Context contexto;
    private RecyclerViewOnClickListener listenerOnClick;

    public AdapterServicoPrestador(ArrayList<Agendamento> agendamentos, Context context, RecyclerViewOnClickListener listner){
        this.agendamentos = agendamentos;
        this.contexto = context;
        this.listenerOnClick = listner;
    }

    @Override
    public ViewHolderServico onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview_servicos_prestador, viewGroup, false);
        ViewHolderServico viewHolderServico = new ViewHolderServico(view);

        return viewHolderServico;
    }

    @Override
    public void onBindViewHolder(ViewHolderServico viewHolderServico, int position) {

        Agendamento agendamento = agendamentos.get(position);
        TextView horaDia = (TextView) viewHolderServico.servicoPrestadorCardView.findViewById(R.id.horarioDiaTextView);
        SimpleDateFormat formatData = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
        Date data = agendamento.getHorario().getTime();
        horaDia.setText(formatData.format(data) + " ás " + formatHora.format(data));

        NumberFormat formatador = NumberFormat.getCurrencyInstance();
        TextView nomeServico = (TextView) viewHolderServico.servicoPrestadorCardView.findViewById(R.id.nomeServicoTextView);
        nomeServico.setText(agendamento.getServico().getNome() + " " + formatador.format(agendamento.getValorTotal()));

        TextView nomeCliente = (TextView) viewHolderServico.servicoPrestadorCardView.findViewById(R.id.nomeClienteTextView);
        nomeCliente.setText("Cliente " + agendamento.getCliente().getNome());
    }

    @Override
    public int getItemCount() {
        return agendamentos != null ? agendamentos.size() : 0 ;
    }

    public class ViewHolderServico extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView servicoPrestadorCardView;

        public ViewHolderServico(View itemView){
            super(itemView);
            servicoPrestadorCardView = (CardView) itemView.findViewById(R.id.servicoPrestadorCardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listenerOnClick != null){
                listenerOnClick.onClickListner(agendamentos.get(getLayoutPosition()));
            }
        }
    }
}
