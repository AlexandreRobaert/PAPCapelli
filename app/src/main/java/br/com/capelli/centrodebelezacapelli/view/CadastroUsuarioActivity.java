package br.com.capelli.centrodebelezacapelli.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Cliente;
import br.com.capelli.centrodebelezacapelli.model.Perfil;

public class CadastroUsuarioActivity extends BaseActivity {

    private TextView tituloTextView;
    private EditText nomeEditText;
    private EditText sobreNomeEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;
    private EditText senhaEditText;
    private EditText repetirEditText;
    private RadioGroup sexoRadioGroup;
    private Button cadastrarButton;
    private ImageView deleteImageView;
    private static final String TAG = "USUARIO";
    private Cliente cliente;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mDatabase;
    private AlertDialog dialogEmail;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference refClientes = mDatabase.child("clientes");
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "onAuthStateChanged: LOGADO! " + user.getUid() + " - " + user.getDisplayName());
                }else{
                    Log.d(TAG, "onAuthStateChanged: NÃO LOGADO! ");
                }
            }
        };

        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        sobreNomeEditText = (EditText) findViewById(R.id.sobreNomeEditText);
        telefoneEditText = (EditText) findViewById(R.id.telefoneFuncionarioEditText);
        emailEditText = (EditText) findViewById(R.id.emailFuncionarioEditText);
        senhaEditText = (EditText) findViewById(R.id.senhaFuncionarioEditText);
        repetirEditText = (EditText) findViewById(R.id.repetirEditText);
        sexoRadioGroup = (RadioGroup) findViewById(R.id.sexoRadioGroup);
        cadastrarButton = (Button) findViewById(R.id.cadastrarButton);
        deleteImageView = (ImageView) findViewById(R.id.deletarImageView);
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CadastroUsuarioActivity.this);
                builder.setTitle("Excluir Perfil");
                builder.setMessage("Deseja realmente excluir perfil?");
                builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        final DatabaseReference refCliente = FirebaseDatabase.getInstance().getReference().child("clientes").child(getUid());
                        refCliente.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("USUARIO", "REMOVIDO");

                                final DatabaseReference refNiveis = mDatabase.child("niveis");
                                refNiveis.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                            String email = snapshot.child("email").getValue(String.class);
                                            if(email.equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                                                Log.d("CHAVE", dataSnapshot.getKey());
                                                refNiveis.child(snapshot.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                                        user.delete()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Log.d(TAG, "Usuario Deletado.");

                                                                        }
                                                                    }
                                                                });
                                                    }
                                                });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        });
                    }
                })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                CadastroUsuarioActivity.this.dialogEmail.cancel();
                            }
                        });
                dialogEmail = builder.create();
                dialogEmail.show();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            final String uid = bundle.getString("UID");
            tituloTextView = (TextView) findViewById(R.id.tituloTextView);
            tituloTextView.setText("Editar Perfil");
            cadastrarButton.setText("Salvar");

            refClientes.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cliente = dataSnapshot.getValue(Cliente.class);

                    nomeEditText.setText(cliente.getNome());
                    sobreNomeEditText.setText(cliente.getSobreNome());
                    telefoneEditText.setText(cliente.getTelefone());
                    senhaEditText.setVisibility(View.INVISIBLE);
                    repetirEditText.setVisibility(View.INVISIBLE);
                    emailEditText.setVisibility(View.INVISIBLE);

                    if(cliente.getSexo().equalsIgnoreCase("Masculino")){

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();
                boolean valido = false;

                if(bundle != null){
                    valido = validarFormularioEditado();
                    Log.d("Validador", "ValidouEditado");
                }else{
                    valido = validarFormulario();
                    Log.d("Validador", "Validou");
                }
                Log.d("Valido????", String.valueOf(valido));

                if(valido){
                    cliente = new Cliente();
                    cliente.setNome(nomeEditText.getText().toString());
                    cliente.setSobreNome(sobreNomeEditText.getText().toString());
                    cliente.setTelefone(telefoneEditText.getText().toString());
                    sexoRadioGroup = (RadioGroup) findViewById(R.id.sexoRadioGroup);
                    cliente.setSexo(((RadioButton) findViewById(sexoRadioGroup.getCheckedRadioButtonId())).getText().toString());

                    if(bundle == null) {
                        cliente.setSenha(senhaEditText.getText().toString());
                        cliente.setEmail(emailEditText.getText().toString());
                        cliente.setAtivo(true);

                        mAuth.createUserWithEmailAndPassword(cliente.getEmail(), cliente.getSenha())
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d(TAG, "createUserWithEmailAndPassword:OnComplete: " + task.isSuccessful());

                                        if (task.isSuccessful()) {

                                            showProgressDialog();
                                            FirebaseUser user = task.getResult().getUser();
                                            cliente.setSenha(null);
                                            DatabaseReference refCliente = mDatabase.child("clientes");
                                            refCliente.child(user.getUid()).setValue(cliente);
                                            DatabaseReference refNivel = mDatabase.child("niveis");
                                            Map<String, Object> nivel = new HashMap<>();
                                            nivel.put("email", cliente.getEmail());
                                            nivel.put("perfil", Perfil.CLIENTE);
                                            refNivel.push().setValue(nivel);

                                            hideProgressDialog();
                                            sendBroadcast(new Intent(LoginActivity.ALERTA_FINALIZAR));
                                            Intent telaDashboard = new Intent(CadastroUsuarioActivity.this, DashbordActivity.class);
                                            startActivity(telaDashboard);
                                            finish();

                                        } else {
                                            hideProgressDialog();
                                            Toast.makeText(getApplicationContext(),
                                                    getResources().getString(R.string.usuario_nao_cadastrado_tente_mais_tarde), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e.getClass().equals(FirebaseAuthWeakPasswordException.class)) {
                                    senhaEditText.setText(null);
                                    senhaEditText.setError(getResources().getString(R.string.senha_muito_fraca));
                                } else if (e.getClass().equals(FirebaseAuthInvalidCredentialsException.class)) {
                                    emailEditText.setError(getResources().getString(R.string.email_invalido));
                                } else if (e.getClass().equals(FirebaseAuthUserCollisionException.class)) {
                                    emailEditText.setError(getResources().getString(R.string.email_ja_cadastrado));
                                } else {
                                    Toast.makeText(CadastroUsuarioActivity.this,
                                            getResources().getString(R.string.erro_efetuar_cadastro), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }else{
                        cliente.setSenha(null);
                        DatabaseReference refCliente = mDatabase.child("clientes");
                        refCliente.child(getUid()).setValue(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "Alterado com Sucesso!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }
            }
        });
    }

    public boolean validarFormulario(){
        if(nomeEditText.getText().toString().isEmpty() || sobreNomeEditText.getText().toString().isEmpty()
                || telefoneEditText.getText().toString().isEmpty() || emailEditText.getText().toString().isEmpty()
                || senhaEditText.getText().toString().isEmpty() || repetirEditText.getText().toString().isEmpty()
                || sexoRadioGroup.getCheckedRadioButtonId() == -1 ){

            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.todos_campos_devem_ser_preenchidos), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(senhaEditText.getText().toString().length() <= 6) {
                senhaEditText.setError(getResources().getString(R.string.senha_muito_pequena));
                return false;
            }else if (!senhaEditText.getText().toString().trim().toLowerCase().equals(repetirEditText.getText().toString().trim().toLowerCase())) {
                senhaEditText.setError(getResources().getString(R.string.senhas_nao_conferem));
                return false;
            }
        }
        return true;
    }

    public boolean validarFormularioEditado(){
        if(nomeEditText.getText().toString().isEmpty() || sobreNomeEditText.getText().toString().isEmpty()
                || telefoneEditText.getText().toString().isEmpty() || sexoRadioGroup.getCheckedRadioButtonId() == -1 ) {

            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.todos_campos_devem_ser_preenchidos), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
}
