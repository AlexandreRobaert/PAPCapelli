package br.com.capelli.centrodebelezacapelli.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Categoria;
import br.com.capelli.centrodebelezacapelli.model.Servico;
import br.com.capelli.centrodebelezacapelli.util.AdapterServico;
import br.com.capelli.centrodebelezacapelli.util.RecyclerViewOnClickListener;

public class AgendaServicoActivity extends AppCompatActivity implements RecyclerViewOnClickListener{

    private ArrayList<Servico> servicos;
    private RecyclerView listaRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterServico adapterServico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_servico);

        String categoria = getIntent().getExtras().getString("categoria");
        servicos = new ArrayList<>();
        buscarServicos(categoria);
    }

    private void buscarServicos(String categoria){
        DatabaseReference refServicos = FirebaseDatabase.getInstance().getReference().child("servicos");
        Query consulta = refServicos.orderByChild("categoria").equalTo(categoria);
        consulta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapServico : dataSnapshot.getChildren()) {
                        Servico servico = new Servico();
                        servico.setId(snapServico.getKey());
                        servico.setNome(snapServico.child("nome").getValue(String.class));
                        servico.setValorServico(snapServico.child("valorServico").getValue(Double.class));
                        servico.setTempoEstimado(snapServico.child("tempoEstimado").getValue(String.class));
                        Categoria cat = new Categoria();
                        cat.setNome(snapServico.child("categoria").getValue(String.class));
                        servico.setCategoria(cat);
                        servicos.add(servico);
                    }
                    setRecyclerView();
                } else {
                    Toast.makeText(getApplicationContext(), "Sem serviços cadastrados", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setRecyclerView(){
        listaRecyclerView = (RecyclerView) findViewById(R.id.servicosRecyclerview);
        layoutManager = new LinearLayoutManager(this);
        listaRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaRecyclerView.setLayoutManager(layoutManager);

        adapterServico = new AdapterServico(servicos, AgendaServicoActivity.this, AgendaServicoActivity.this);
        listaRecyclerView.setAdapter(adapterServico);
    }


    @Override
    public void onClickListner(Object object) {

        Servico servico = (Servico) object;
        Intent proximo = new Intent(AgendaServicoActivity.this, AgendaHorarioActivity.class);
        proximo.putExtra("servico", servico.getId());
        startActivity(proximo);
    }
}
