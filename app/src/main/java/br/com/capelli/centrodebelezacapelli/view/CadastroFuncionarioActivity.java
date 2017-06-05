package br.com.capelli.centrodebelezacapelli.view;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Funcionario;
import br.com.capelli.centrodebelezacapelli.model.Perfil;

public class CadastroFuncionarioActivity extends BaseActivity {

    private EditText nomeEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;
    private EditText senhaEditText;
    private EditText senhaRepetirEditText;
    private Button cadastrarButton;
    private Funcionario funcionario;
    private FirebaseAuth mAuth;
    private String TAG = "CAD_FUNCIONARIO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_funcionario);

        final Intent dados = getIntent();

        nomeEditText = (EditText) findViewById(R.id.nomeFuncionarioEditText);
        telefoneEditText = (EditText) findViewById(R.id.telefoneFuncionarioEditText);
        emailEditText = (EditText) findViewById(R.id.emailFuncionarioEditText);
        senhaEditText = (EditText) findViewById(R.id.senhaFuncionarioEditText);
        senhaRepetirEditText = (EditText) findViewById(R.id.senhaRepetirEditText);
        cadastrarButton = (Button) findViewById(R.id.cadastrarButton);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarFormulario()){
                    Bundle bEmpresa = dados.getExtras();
                    funcionario = new Funcionario();
                    funcionario.setNome(nomeEditText.getText().toString());
                    funcionario.setTelefone(telefoneEditText.getText().toString());
                    funcionario.setEmail(emailEditText.getText().toString());
                    funcionario.setAtivo(true);
                    funcionario.setCnpj(bEmpresa.getString("cnpj"));
                    funcionario.setRazaoSocial(bEmpresa.getString("razaoSocial"));
                    funcionario.setNomeFantasia(bEmpresa.getString("nomeFantasia"));

                    if(bEmpresa.getString("senhaWebService") != null){
                        funcionario.setSenhaWebService(bEmpresa.getString("senhaWebService"));
                    }

                    showProgressDialog();
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(funcionario.getEmail(), senhaEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "createUserWithEmailAndPassword:OnComplete: " + task.isSuccessful());

                                    if (task.isSuccessful()) {

                                        DatabaseReference refFuncionario = FirebaseDatabase.getInstance().getReference().child("funcionarios");
                                        final DatabaseReference refNiveis = FirebaseDatabase.getInstance().getReference().child("niveis");

                                        refFuncionario.child(task.getResult().getUser().getUid()).setValue(funcionario).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Map<String, Object> nivel = new HashMap<>();
                                                    nivel.put("email", funcionario.getEmail());
                                                    nivel.put("perfil", Perfil.FUNCIONARIO);
                                                    refNiveis.push().setValue(nivel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(CadastroFuncionarioActivity.this,
                                                                        getResources().getString(R.string.funcionario_cadastrado),
                                                                        Toast.LENGTH_LONG).show();
                                                                finish();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                        hideProgressDialog();


                                    }else{
                                        hideProgressDialog();
                                        Toast.makeText(getApplicationContext(),
                                                getResources().getString(R.string.usuario_nao_cadastrado_tente_mais_tarde), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(e.getClass().equals(FirebaseAuthWeakPasswordException.class)){
                                senhaEditText.setText(null);
                                senhaEditText.setError(getResources().getString(R.string.senha_muito_fraca));
                            }else if(e.getClass().equals(FirebaseAuthInvalidCredentialsException.class)){
                                emailEditText.setError(getResources().getString(R.string.email_invalido));
                            }else if(e.getClass().equals(FirebaseAuthUserCollisionException.class)){
                                emailEditText.setError(getResources().getString(R.string.email_ja_cadastrado));
                            }else{
                                Toast.makeText(CadastroFuncionarioActivity.this,
                                        getResources().getString(R.string.erro_efetuar_cadastro), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validarFormulario(){

        if(nomeEditText.getText().toString().isEmpty()){
            nomeEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(telefoneEditText.getText().toString().isEmpty()){
            telefoneEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(emailEditText.getText().toString().isEmpty()){
            emailEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }else if(senhaEditText.getText().toString().isEmpty()){
            senhaEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }if(senhaEditText.getText().toString().length() < 6){
            senhaEditText.setError(getResources().getString(R.string.senha_muito_pequena));
            senhaEditText.setText(null);
            return false;
        }else if(!senhaRepetirEditText.getText().toString().equals(senhaEditText.getText().toString())){
            senhaRepetirEditText.setError(getResources().getString(R.string.senhas_nao_conferem));
            senhaRepetirEditText.setText(null);
            senhaEditText.setText(null);
            return false;
        }

        return true;
    }
}
