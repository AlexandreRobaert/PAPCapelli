package br.com.capelli.centrodebelezacapelli.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Agendamento;
import br.com.capelli.centrodebelezacapelli.model.Cliente;
import br.com.capelli.centrodebelezacapelli.model.Funcionario;
import br.com.capelli.centrodebelezacapelli.model.Servico;
import br.com.capelli.centrodebelezacapelli.util.AdapterServicoPrestador;
import br.com.capelli.centrodebelezacapelli.util.RecyclerViewOnClickListener;

public class ServicosPretadorActivity extends BaseActivity implements RecyclerViewOnClickListener {

    private RecyclerView listaRecyclerView;
    private CardView cardview;
    private ArrayList<Agendamento> agendamentos;
    private DatabaseReference refAgendamentos;
    private ValueEventListener valueEventListener;
    private DatabaseReference mDatabase;
    private RecyclerView.LayoutManager layoutManager;
    private AdapterServicoPrestador adapter;
    private Funcionario funcionario;
    private AlertDialog dialog;
    private Agendamento agendamento;
    private Query retornoAgendamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos_prestador);

        showProgressDialog();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                agendamentos = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Agendamento agendamento = new Agendamento();
                    boolean finalizado = snapshot.child("finalizado").getValue(boolean.class);
                    String servicoNome = snapshot.child("servico").getValue(String.class);
                    String horario = snapshot.child("horario").getValue(String.class);
                    String nomeCliente = snapshot.child("cliente").getValue(String.class);
                    double valor = snapshot.child("valor").getValue(double.class);

                    if(!finalizado){
                        agendamento.setUid(snapshot.getKey());

                        Servico servico = new Servico();
                        servico.setNome(servicoNome);
                        agendamento.setServico(servico);

                        SimpleDateFormat format = new SimpleDateFormat();
                        Calendar date = Calendar.getInstance();
                        Log.d("DATAString", horario);
                        try {
                            Date data = format.parse(horario);
                            date.setTime(data);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        agendamento.setHorario(date);

                        Cliente cliente = new Cliente();
                        cliente.setNome(nomeCliente);
                        agendamento.setCliente(cliente);
                        agendamento.setValorTotal(valor);
                        agendamentos.add(agendamento);
                    }
                }
                setRecyclerView();
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Query retornoFuncionario = mDatabase.child("funcionarios").orderByKey().equalTo(getUid());
        retornoFuncionario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    funcionario = new Funcionario();
                    funcionario.setUid(snap.getKey());
                    funcionario.setNome(snap.child("nome").getValue(String.class));
                    break;
                }

                refAgendamentos = mDatabase.child("agendamentos");
                Query retornoAgendamentos = refAgendamentos.orderByChild("funcionario").equalTo(funcionario.getNome());
                retornoAgendamentos.addValueEventListener(valueEventListener);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Finalizar Serviço!");
        builder.setMessage("Deseja Finalizar o Serviço?");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_finalizar_servico, null);
        builder.setView(view);
        final EditText valorServico = (EditText) view.findViewById(R.id.valorServicoEditText);
        Locale local = new Locale("pt", "BR");
        NumberFormat format = NumberFormat.getCurrencyInstance(local);
        builder
                .setPositiveButton(R.string.finalizar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        agendamento.setValorTotal(Integer.parseInt(valorServico.getText().toString()));
                        refAgendamentos.child(agendamento.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Map<String, Object> dados = new HashMap<>();
                                dados.put("finalizado", true);
                                dados.put("valor", agendamento.getValorTotal());
                                refAgendamentos.child(agendamento.getUid()).updateChildren(dados);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        dialog = builder.create();
    }

    private void setRecyclerView(){
        listaRecyclerView = (RecyclerView) findViewById(R.id.listaServicosRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        listaRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listaRecyclerView.setLayoutManager(layoutManager);

        adapter = new AdapterServicoPrestador(agendamentos, ServicosPretadorActivity.this.getApplicationContext(), ServicosPretadorActivity.this);
        listaRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickListner(Object object) {
        agendamento = (Agendamento) object;
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(retornoAgendamentos != null){
            retornoAgendamentos.removeEventListener(valueEventListener);
        }
    }
}
