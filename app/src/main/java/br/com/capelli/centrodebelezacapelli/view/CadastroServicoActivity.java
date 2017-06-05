package br.com.capelli.centrodebelezacapelli.view;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
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

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Categoria;
import br.com.capelli.centrodebelezacapelli.model.Servico;

public class CadastroServicoActivity extends BaseActivity implements View.OnClickListener{

    private EditText nomeServico;
    private Spinner categoriaSpinner;
    private EditText valorServico;
    private Button cadastrar;
    private ArrayList<String> categorias;
    private String categoriaSelecionada;
    private DatabaseReference refCategorias;
    private DatabaseReference refServicos;
    private NumberPicker picker;
    private String tempoEstimado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_servico);

        categoriaSelecionada = getResources().getString(R.string.selecione_uma_categoria);
        final String[] listaTempo = getResources().getStringArray(R.array.listaTempoEstimado);
        tempoEstimado = listaTempo[0];

        nomeServico = (EditText) findViewById(R.id.nomeEditText);
        cadastrar = (Button) findViewById(R.id.cadastrarButton);
        categoriaSpinner = (Spinner) findViewById(R.id.categoriaSpinner);
        valorServico = (EditText) findViewById(R.id.valorEditText);

        picker = (NumberPicker) findViewById(R.id.tempoNumberPicker);
        picker.setDisplayedValues(listaTempo);
        picker.setMinValue(0);
        picker.setMaxValue(listaTempo.length - 1);
        picker.setValue(1);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tempoEstimado = listaTempo[newVal];
            }
        });

        categorias = new ArrayList<>();
        categorias.add(getResources().getString(R.string.selecione_uma_categoria));

        refCategorias = FirebaseDatabase.getInstance().getReference().child("categorias");
        refCategorias.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Categoria cat = snapshot.getValue(Categoria.class);
                    cat.setUid(snapshot.getKey());
                    categorias.add(cat.getNome());
                }
                ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<String>(CadastroServicoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, categorias);
                categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoriaSpinner.setAdapter(categoriasAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        cadastrar.setOnClickListener(this);

    }

    public boolean validarFormulario(){

        if(nomeServico.getText().toString().isEmpty()){
            nomeServico.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(categoriaSelecionada.equalsIgnoreCase(getResources().getString(R.string.selecione_uma_categoria))){
            categoriaSpinner.setBackgroundColor(Color.RED);
            return false;
        }else if(valorServico.getText().toString().isEmpty()){
            valorServico.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.cadastrarButton){

            if(validarFormulario()){


                final String nome = nomeServico.getText().toString().trim();
                refServicos = FirebaseDatabase.getInstance().getReference().child("servicos");
                Query consulta = refServicos.orderByChild("nome").equalTo(nome);
                consulta.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() == 0){
                            Categoria cat = new Categoria();
                            cat.setNome(categoriaSelecionada);
                            Servico servico = new Servico();
                            servico.setNome(nome);
                            servico.setValorServico(Double.parseDouble(valorServico.getText().toString().trim()));
                            servico.setTempoEstimado(tempoEstimado);
                            servico.setCategoria(cat);
                            Log.d("SERVICO", servico.toString());

                            refServicos.push().setValue(servico.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        nomeServico.setText(null);
                                        valorServico.setText(null);
                                        categoriaSpinner.setSelection(0);
                                        Toast.makeText(CadastroServicoActivity.this, getResources().getString(R.string.categoria_cadastrada), Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(CadastroServicoActivity.this, getResources().getString(R.string.erro_ao_cadastrar), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.servico_ja_cadastrado), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        categoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoriaSelecionada = categorias.get(position);
                categoriaSpinner.setBackgroundColor(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
