package br.com.capelli.centrodebelezacapelli.view;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Agendamento;
import br.com.capelli.centrodebelezacapelli.model.Categoria;
import br.com.capelli.centrodebelezacapelli.model.Cliente;
import br.com.capelli.centrodebelezacapelli.model.Funcionario;
import br.com.capelli.centrodebelezacapelli.model.Servico;

public class AgendaHorarioActivity extends BaseActivity {

    private DatePicker calendario;
    private Button proximo;
    private Button voltar;
    private Calendar data;
    private ArrayList<String> nomesFuncionarios;
    private Spinner funcionarioSpinner;
    private Cliente cliente;
    private Funcionario funcionario;
    private ArrayList<Funcionario> funcionarios;
    private Calendar horario;
    private Servico servico;
    private TimePicker relogio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_horario);
        nomesFuncionarios = new ArrayList<>();
        funcionarios = new ArrayList<>();
        nomesFuncionarios.add("SELECIONE UM FUNCIONARIO");
        horario = Calendar.getInstance();

        Bundle bundle = getIntent().getExtras();
        String servicoUid = bundle.getString("servico");

        DatabaseReference refServicos = FirebaseDatabase.getInstance().getReference().child("servicos");
        Query consulta = refServicos.orderByKey().equalTo(servicoUid);
        consulta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapServico : dataSnapshot.getChildren()) {
                    servico = new Servico();
                    servico.setId(snapServico.getKey());
                    servico.setNome(snapServico.child("nome").getValue(String.class));
                    servico.setValorServico(snapServico.child("valorServico").getValue(Double.class));
                    servico.setTempoEstimado(snapServico.child("tempoEstimado").getValue(String.class));
                    Categoria cat = new Categoria();
                    cat.setNome(snapServico.child("categoria").getValue(String.class));
                    servico.setCategoria(cat);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference refFuncionarios = FirebaseDatabase.getInstance().getReference().child("funcionarios");
        refFuncionarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Funcionario funcionario = snap.getValue(Funcionario.class);
                    funcionario.setUid(snap.getKey());
                    nomesFuncionarios.add(funcionario.getNome());
                    funcionarios.add(funcionario);
                }
                configuraSpinner();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        calendario = (DatePicker) findViewById(R.id.calendarioDatePicker);
        long agora = System.currentTimeMillis() - 1000;
        calendario.setMinDate(agora);
        calendario.setMaxDate(agora + 5_000_000_000L);
        funcionarioSpinner = (Spinner) findViewById(R.id.funcionariosSpinner);
        funcionarioSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    funcionarioSpinner.setBackgroundColor(Color.RED);
                    Log.d("SPINNER", "CHAMOU");
                }else{
                    calendario.setVisibility(View.VISIBLE);
                    proximo.setVisibility(View.VISIBLE);
                    funcionarioSpinner.setBackgroundColor(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        relogio = (TimePicker) findViewById(R.id.relogioTimePicker);
        relogio.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            private static final int TIME_PICKER_INTERVAL=30;
            private boolean mIgnoreEvent=false;

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                horario.set(Calendar.HOUR_OF_DAY, hourOfDay);
                horario.set(Calendar.MINUTE, minute);
                Log.d("RELOGIO", "minuto");
                if (mIgnoreEvent)
                    return;
                if (minute % TIME_PICKER_INTERVAL != 0){
                    int minuteFloor = minute - (minute % TIME_PICKER_INTERVAL);
                    minute = minuteFloor + (minute == minuteFloor + 1 ? TIME_PICKER_INTERVAL : 0);
                    if (minute == 60)
                        minute = 0;
                    mIgnoreEvent = true;
                    relogio.setCurrentMinute(minute);
                    mIgnoreEvent=false;
                }
            }
        });
        proximo = (Button) findViewById(R.id.proximoButton);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                if(proximo.getText().toString().equalsIgnoreCase("Agendar")){
                    Query queryCliente = FirebaseDatabase.getInstance().getReference().child("clientes").orderByKey().equalTo(getUid());
                    queryCliente.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snap: dataSnapshot.getChildren()){
                                cliente = snap.getValue(Cliente.class);
                                cliente.setUid(snap.getKey());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    final Funcionario func = funcionarios.get(funcionarioSpinner.getSelectedItemPosition() - 1);
                    Query queryFuncionario = FirebaseDatabase.getInstance().getReference().child("funcionarios").orderByChild("nome").equalTo(func.getNome());
                    queryFuncionario.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snap: dataSnapshot.getChildren()){
                                funcionario = snap.getValue(Funcionario.class);
                                funcionario.setUid(dataSnapshot.getKey());
                            }
                            horario.set(calendario.getYear(), calendario.getMonth(), calendario.getDayOfMonth());

                            Agendamento agendamento = new Agendamento();
                            agendamento.setCliente(cliente);
                            agendamento.setFuncionario(funcionario);
                            agendamento.setFinalizado(false);
                            agendamento.setBloquearHorario(true);
                            agendamento.setServico(servico);
                            agendamento.setConfirmado(false);
                            agendamento.setHorario(horario);
                            agendamento.setValorTotal(servico.getValorServico());

                            DatabaseReference refAgendamentos = FirebaseDatabase.getInstance().getReference().child("agendamentos");
                            refAgendamentos.push().setValue(agendamento.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AgendaHorarioActivity.this,
                                                getResources().getString(R.string.agendamento_realizado), Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else{
                                        Toast.makeText(AgendaHorarioActivity.this,"Falha ao agendar", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{
                    calendario.setVisibility(View.INVISIBLE);
                   relogio.setVisibility(View.VISIBLE);
                    voltar.setVisibility(View.VISIBLE);
                    proximo.setText("Agendar");
                }

                hideProgressDialog();
            }
        });

        voltar = (Button) findViewById(R.id.voltarButton);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(proximo.getText().toString().equalsIgnoreCase("Proximo")){
                    calendario.setVisibility(View.INVISIBLE);
                    relogio.setVisibility(View.VISIBLE);
                    voltar.setVisibility(View.INVISIBLE);
                }else{
                    calendario.setVisibility(View.VISIBLE);
                    relogio.setVisibility(View.INVISIBLE);
                    proximo.setText("Proximo");
                }
            }
        });
    }

    private void configuraSpinner(){
        ArrayAdapter<String> funcionarioAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_item_spinner, nomesFuncionarios);
        funcionarioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        funcionarioSpinner.setAdapter(funcionarioAdapter);
    }
}
