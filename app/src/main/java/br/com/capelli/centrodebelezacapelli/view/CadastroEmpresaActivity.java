package br.com.capelli.centrodebelezacapelli.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.capelli.centrodebelezacapelli.R;

public class CadastroEmpresaActivity extends BaseActivity {

    private EditText nomeEditText;
    private EditText razaoSocialEditText;
    private EditText cnpjEditText;
    private EditText inscricaoEstadualEditText;
    private EditText senhaEditText;
    private Button proximoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_empresa);

        nomeEditText = (EditText) findViewById(R.id.nomeEmpresaEditText);
        razaoSocialEditText = (EditText) findViewById(R.id.razaoSocialEditText);
        cnpjEditText = (EditText) findViewById(R.id.cnpjEditText);
        inscricaoEstadualEditText = (EditText) findViewById(R.id.inscricaoEstadualEditText);
        senhaEditText = (EditText) findViewById(R.id.senhaWeServiceEditText);
        proximoButton = (Button) findViewById(R.id.proximoButton);
        proximoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarFormulario()){
                    Intent proximaTela = new Intent(CadastroEmpresaActivity.this, CadastroFuncionarioActivity.class);
                    proximaTela.putExtra("nomeFantasia", nomeEditText.getText().toString());
                    proximaTela.putExtra("razaoSocial", razaoSocialEditText.getText().toString());
                    proximaTela.putExtra("cnpj", cnpjEditText.getText().toString());
                    proximaTela.putExtra("inscricaoEstadual", inscricaoEstadualEditText.getText().toString());
                    if(!senhaEditText.getText().toString().isEmpty()){
                        proximaTela.putExtra("senhaWebService", senhaEditText.getText().toString());
                    }
                    startActivity(proximaTela);
                    finish();
                }
            }
        });
    }

    private boolean validarFormulario(){

        if(nomeEditText.getText().toString().isEmpty()){
            nomeEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(razaoSocialEditText.getText().toString().isEmpty()){
            razaoSocialEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(cnpjEditText.getText().toString().isEmpty()){
            cnpjEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(inscricaoEstadualEditText.getText().toString().isEmpty()){
            inscricaoEstadualEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }

        return true;
    }
}
