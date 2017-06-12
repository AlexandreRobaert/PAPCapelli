package br.com.capelli.centrodebelezacapelli.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Categoria;
import br.com.capelli.centrodebelezacapelli.util.AdapterCategoria;
import br.com.capelli.centrodebelezacapelli.util.RecyclerViewOnClickListener;

public class AgendaCategoriaActivity extends BaseFragmentActivity implements RecyclerViewOnClickListener {

    private RecyclerView recyclerViewCategorias;
    private DatabaseReference refCategorias;
    private ValueEventListener valueEventCategoriaListener;
    private ArrayList<Categoria> categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_categoria);

        recyclerViewCategorias = (RecyclerView) findViewById(R.id.lstCategoriasRecyclerView);
        refCategorias = FirebaseDatabase.getInstance().getReference().child("categorias");
        categorias = new ArrayList<>();

        showProgressDialog();
        valueEventCategoriaListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snap: dataSnapshot.getChildren()){
                    Categoria categoria = snap.getValue(Categoria.class);
                    categoria.setUid(snap.getKey());
                    categorias.add(categoria);
                }
                AdapterCategoria adapterCategoria = new AdapterCategoria(categorias, AgendaCategoriaActivity.this, AgendaCategoriaActivity.this);
                recyclerViewCategorias.setAdapter(adapterCategoria);
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewCategorias.setLayoutManager(layoutManager);
        recyclerViewCategorias.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onStart() {
        super.onStart();
        refCategorias.addListenerForSingleValueEvent(valueEventCategoriaListener);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(valueEventCategoriaListener != null){
            refCategorias.removeEventListener(valueEventCategoriaListener);
        }
    }

    @Override
    public void onClickListner(Object object) {

        Categoria categoria = (Categoria) object;
        Intent proximo = new Intent(AgendaCategoriaActivity.this, AgendaServicoActivity.class);
        proximo.putExtra("categoria", categoria.getNome());
        startActivity(proximo);

    }
}
