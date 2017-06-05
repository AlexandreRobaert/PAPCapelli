package br.com.capelli.centrodebelezacapelli.view;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Categoria;

public class CadastroCategoriaActivity extends BaseActivity {

    private EditText categoriaEditText;
    private Button cadastrarButton;
    private DatabaseReference refCategorias;
    private String uidCategoria = null;
    private String nomeCategoria = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_categoria);

        categoriaEditText = (EditText) findViewById(R.id.nomeEditText);
        cadastrarButton = (Button) findViewById(R.id.cadastrarButton);
        refCategorias = FirebaseDatabase.getInstance().getReference().child("categorias");
        final Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            uidCategoria = bundle.getString("UIDCategoria");
            nomeCategoria = bundle.getString("nomeCategoria");
            categoriaEditText.setText(nomeCategoria);
            cadastrarButton.setText("Salvar");
        }
        cadastrarButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(validarFormulario()){
                    showProgressDialog();
                    final String nomeCategoria = categoriaEditText.getText().toString().trim();
                    Query consulta = refCategorias.orderByChild("nome").equalTo(nomeCategoria);
                    consulta.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getChildrenCount() == 0){
                                Categoria categoria = new Categoria();
                                categoria.setNome(nomeCategoria);

                                if(uidCategoria != null){
                                    refCategorias.child(uidCategoria).setValue(categoria);
                                }else{
                                    refCategorias.push().setValue(categoria);
                                }
                                categoriaEditText.setText(null);
                                hideProgressDialog();
                            }else{
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.categoria_ja_cadastrada),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                    hideProgressDialog();
                }
            }
        });
    }

    public boolean validarFormulario(){
        if(categoriaEditText.getText().toString().isEmpty()){
            categoriaEditText.setError(getResources().getString(R.string.campo_obrigatorio));
            return false;
        }
        return true;
    }
}
