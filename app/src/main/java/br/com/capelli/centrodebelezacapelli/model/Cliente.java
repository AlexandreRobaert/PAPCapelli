package br.com.capelli.centrodebelezacapelli.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.capelli.centrodebelezacapelli.model.Usuario;

/**
 * Created by alexadre on 19/03/17.
 * @author Alexandre Robaert
 * Classe POJO de Cliente.
 */


public class Cliente extends Usuario {

    private String sobreNome;
    private String sexo;

    public Cliente(){}

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return getNome() + " " + getSobreNome() + "/n" + getTelefone() + "Sexo: " + getSexo();
    }
}
