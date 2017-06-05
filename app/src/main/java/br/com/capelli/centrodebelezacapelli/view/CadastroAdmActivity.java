package br.com.capelli.centrodebelezacapelli.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import br.com.capelli.centrodebelezacapelli.model.Administrador;

public class CadastroAdmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Administrador adm = new Administrador();
        adm.setNome("Administrador Capelli");
        adm.setCnpj("74.854.887/0001-23");
        adm.setSenhaWebService("senhaweb");
        adm.setInscricaoEstadual("8766263");
        adm.setTelefone("(41) 9 9654-7933");
        adm.setRazaoSocial("Centro de beleza Capelli LTDA");
        adm.setAtivo(true);
        adm.setEmail("capelli@capelli.com.br");
        adm.setSenha("capelli");

        DatabaseReference refAdm = FirebaseDatabase.getInstance().getReference().child("administrador");
        refAdm.child("KqD6bbDbUJh23buMZev7g0v5y0x2").setValue(adm);

        DatabaseReference refNiveis = FirebaseDatabase.getInstance().getReference().child("niveis");
        Map<String, Object> niv = new HashMap<String, Object>();
        niv.put("email", "capelli@capelli.com.br");
        niv.put("perfil", 1.0);
        refNiveis.push().setValue(niv);
    }

}
