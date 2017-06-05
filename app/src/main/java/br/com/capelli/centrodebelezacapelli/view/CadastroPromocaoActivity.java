package br.com.capelli.centrodebelezacapelli.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Promocao;

public class CadastroPromocaoActivity extends BaseActivity implements View.OnClickListener{

    private EditText descricaoEditText;
    private EditText dataInicioEditText;
    private EditText dataFimEditText;
    private EditText porcentagemEditText;
    private Switch ativoSwitch;
    private Button cadastrobutton;
    private DatabaseReference refPromocao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_promocao);

        descricaoEditText = (EditText) findViewById(R.id.descricaoEditText);
        dataInicioEditText = (EditText) findViewById(R.id.dataInicioEditText);
        dataFimEditText = (EditText) findViewById(R.id.dataFimEditText);
        porcentagemEditText = (EditText) findViewById(R.id.porcentagemEditText);
        ativoSwitch = (Switch) findViewById(R.id.ativoSwitch);
        cadastrobutton = (Button) findViewById(R.id.cadastrarButton);
        cadastrobutton.setOnClickListener(this);

        refPromocao = FirebaseDatabase.getInstance().getReference().child("promocoes");

    }

    private boolean validarFormulario(){

        if(descricaoEditText.getText().toString().isEmpty()){
            descricaoEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(dataInicioEditText.getText().toString().isEmpty()){
            dataInicioEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(dataFimEditText.getText().toString().isEmpty()){
            dataFimEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        if(validarFormulario()){
            showProgressDialog();
            Promocao promocao = new Promocao();
            promocao.setDescricao(descricaoEditText.getText().toString());
            promocao.setAtivo(ativoSwitch.isChecked());
            promocao.setPorcentagemDesconto(Integer.parseInt(porcentagemEditText.getText().toString()));
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            Calendar data = Calendar.getInstance();

            try {
                data.setTime(format.parse(dataInicioEditText.getText().toString()));
                promocao.setDataInicio(data);
            } catch (ParseException e) {
                e.printStackTrace();
                dataInicioEditText.setError(getResources().getString(R.string.formato_data_invalido));
                dataInicioEditText.setText(null);
                hideProgressDialog();
                return;
            }

            try{
                data.setTime(format.parse(dataFimEditText.getText().toString()));
                promocao.setDataFim(data);
            } catch (ParseException e){
                dataFimEditText.setError(getResources().getString(R.string.formato_data_invalido));
                dataFimEditText.setText(null);
                hideProgressDialog();
                return;
            }

            refPromocao.push().setValue(promocao.toMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        descricaoEditText.setText(null);
                        dataInicioEditText.setText(null);
                        dataFimEditText.setText(null);
                        ativoSwitch.setChecked(false);
                        porcentagemEditText.setText(null);
                        Toast.makeText(CadastroPromocaoActivity.this, getResources().getString(R.string.promocao_cadastrada_com_sucesso),
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(CadastroPromocaoActivity.this, getResources().getString(R.string.erro_ao_cadastrar),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        hideProgressDialog();
    }
}