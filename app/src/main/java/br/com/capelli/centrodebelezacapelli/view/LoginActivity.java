package br.com.capelli.centrodebelezacapelli.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import br.com.capelli.centrodebelezacapelli.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText login;
    private EditText senha;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private Intent intentDashbord;
    private TextView cadastrar;
    private LoginButton buttonFacebook;
    private CallbackManager mCallbackManager;
    private TextView redefinirSenha;
    private AlertDialog dialogEmail;
    public static final String ALERTA_FINALIZAR = "finalizar_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intentDashbord = new Intent(LoginActivity.this, DashbordActivity.class);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.logado_com_sucesso), Toast.LENGTH_SHORT).show();
            startActivity(intentDashbord);
            finish();
        }

        this.registerReceiver(receiverFechar, new IntentFilter(ALERTA_FINALIZAR));
        mAuth = FirebaseAuth.getInstance();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d("Usuario", " LOGADO!");
                }else{
                    Log.d("Usuario", " NÃO LOGADO");
                }
            }
        };

        login = (EditText) findViewById(R.id.loginEditText);
        senha = (EditText) findViewById(R.id.senhaFuncionarioEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
        cadastrar = (TextView) findViewById(R.id.cadastrarTextView);
        cadastrar.setOnClickListener(this);
        redefinirSenha = (TextView) findViewById(R.id.redefinirSenhaTextView);
        redefinirSenha.setOnClickListener(this);

        buttonFacebook = (LoginButton) findViewById(R.id.buttonFacebook);
        mCallbackManager = CallbackManager.Factory.create();
        buttonFacebook.setReadPermissions("email");
        buttonFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Redefinir senha!");
        builder.setMessage("Entre como o seu email");
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirmacao_email, null);
        builder.setView(view);
        final EditText campoEmail = (EditText) view.findViewById(R.id.emailFuncionarioEditText);
        builder.setPositiveButton(R.string.enviar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        final String email = campoEmail.getText().toString().trim();

                        showProgressDialog();
                        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                LoginActivity.this.dialogEmail.cancel();
                                hideProgressDialog();
                                Toast.makeText(LoginActivity.this,
                                        getResources().getString(R.string.email_enviado), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginActivity.this.dialogEmail.cancel();
                    }
                });
        dialogEmail = builder.create();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FACEBOOK", "handleFacebookAccessToken: " + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("FACEBOOK", "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){
                            startActivity(intentDashbord);
                            finish();
                        }
                    }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListner != null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiverFechar);
    }

    public boolean validarFormulario(){
        boolean resposta = true;
        if(TextUtils.isEmpty(login.getText().toString())){
            login.setError(getResources().getString(R.string.campo_obrigatorio));
            resposta = false;
        }else{
            login.setError(null);
        }

        if(TextUtils.isEmpty(senha.getText().toString())){
            senha.setError(getResources().getString(R.string.campo_obrigatorio));
            resposta = false;
        }else{
            senha.setError(null);
        }

        return resposta;
    }

    BroadcastReceiver receiverFechar = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("LOGIN", "FECHOU");
            LoginActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cadastrarTextView:
                Intent telaCadastrarUsuario = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(telaCadastrarUsuario);
                break;

            case R.id.loginButton:
                if(validarFormulario()){
                    showProgressDialog();
                    mAuth.signInWithEmailAndPassword(login.getText().toString().trim(), senha.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    hideProgressDialog();
                                    if(task.isSuccessful()){
                                        startActivity(intentDashbord);
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            hideProgressDialog();
                            Toast.makeText(LoginActivity.this,
                                    getResources().getString(R.string.email_ou_senha_inválidos),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                    hideProgressDialog();
                }
                break;
            case R.id.redefinirSenhaTextView:
                dialogEmail.show();
                break;
        }
    }
}
