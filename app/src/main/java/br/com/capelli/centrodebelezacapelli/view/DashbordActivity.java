package br.com.capelli.centrodebelezacapelli.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.ExpandableBadgeDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import br.com.capelli.centrodebelezacapelli.R;
import br.com.capelli.centrodebelezacapelli.model.Administrador;
import br.com.capelli.centrodebelezacapelli.model.Cliente;
import br.com.capelli.centrodebelezacapelli.model.Funcionario;
import br.com.capelli.centrodebelezacapelli.model.Perfil;

public class DashbordActivity extends BaseActivity {
    private Cliente cliente;
    private Funcionario funcionario;
    private Administrador administrador;
    private FirebaseDatabase mDatabase;
    private int perfilId;
    private String uid;
    private String nomeUsuario;
    private String emailLogado;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);
        showProgressDialog();

        uid = getUid();
        mDatabase = FirebaseDatabase.getInstance();
        emailLogado = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DatabaseReference refNiveis = mDatabase.getReference().child("niveis");
        Query consulta = refNiveis.orderByChild("email").equalTo(emailLogado);
        consulta.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    perfilId = snap.child("perfil").getValue(Integer.class);
                    setPerfil(savedInstanceState, emailLogado, perfilId);
                    hideProgressDialog();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent fecharLogin = new Intent(LoginActivity.ALERTA_FINALIZAR);
        sendBroadcast(fecharLogin);
    }

    private void criarNavigationDrawer(Bundle savedInstanceState, String nomeUsuario, String emailLogado){


        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.imgheader)
                .addProfiles(new ProfileDrawerItem().withName(String.valueOf(nomeUsuario)).withEmail(emailLogado)
                        .withIcon(GoogleMaterial.Icon.gmd_account_circle)).withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        return false;
                    }

                })
                .build();

        ExpandableBadgeDrawerItem expandableFuncionario = new ExpandableBadgeDrawerItem().withName("Funcionario").withIcon(R.drawable.ic_people_black_24dp).withIdentifier(2000).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)).withSubItems(
                new SecondaryDrawerItem().withName("Cadastrar").withLevel(1).withIcon(R.drawable.ic_add_black_24dp).withIdentifier(1),
                new SecondaryDrawerItem().withName("Pesquisar").withLevel(1).withIcon(GoogleMaterial.Icon.gmd_search).withIdentifier(2));

        ExpandableBadgeDrawerItem expandableCategoria = new ExpandableBadgeDrawerItem().withName("Categoria").withIcon(R.drawable.ic_featured_play_list_black_24dp).withIdentifier(2000).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)).withSubItems(
                new SecondaryDrawerItem().withName("Cadastrar").withLevel(1).withIcon(R.drawable.ic_add_black_24dp).withIdentifier(3),
                new SecondaryDrawerItem().withName("Editar").withLevel(1).withIcon(GoogleMaterial.Icon.gmd_search).withIdentifier(4));

        ExpandableBadgeDrawerItem expandableServicos = new ExpandableBadgeDrawerItem().withName("Serviço").withIcon(R.drawable.ic_content_cut_black_24dp).withIdentifier(2000).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)).withSubItems(
                new SecondaryDrawerItem().withName("Cadastrar").withLevel(1).withIcon(R.drawable.ic_add_black_24dp).withIdentifier(5),
                new SecondaryDrawerItem().withName("Agendar").withLevel(1).withIcon(R.drawable.ic_alarm_add_black_24dp).withIdentifier(6));

        ExpandableBadgeDrawerItem expandablePromocoes = new ExpandableBadgeDrawerItem().withName("Promoções").withIcon(R.drawable.ic_attach_money_black_24dp).withIdentifier(2000).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)).withSubItems(
                new SecondaryDrawerItem().withName("Cadastrar").withLevel(1).withIcon(R.drawable.ic_add_black_24dp).withIdentifier(7),
                new SecondaryDrawerItem().withName("Pesquisar").withLevel(1).withIcon(GoogleMaterial.Icon.gmd_search).withIdentifier(8));

        ExpandableBadgeDrawerItem expandableServicoPrestador = new ExpandableBadgeDrawerItem().withName("Meus Serviços").withIcon(R.drawable.ic_content_cut_black_24dp).withIdentifier(2000).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)).withSubItems(
                new SecondaryDrawerItem().withName("Minha Agenda").withLevel(1).withIcon(R.drawable.ic_event_note_black_24dp).withIdentifier(9),
                new SecondaryDrawerItem().withName("Serviços Realizados").withLevel(1).withIcon(R.drawable.ic_event_available_black_24dp).withIdentifier(10));

        ExpandableBadgeDrawerItem expandableConfiguracoes = new ExpandableBadgeDrawerItem().withName("Configurações").withIcon(R.drawable.ic_settings_black_24dp).withIdentifier(2000).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)).withSubItems(
                new SecondaryDrawerItem().withName("Perfil").withLevel(1).withIcon(R.drawable.ic_perm_identity_black_24dp).withIdentifier(11));

        Drawer navigationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    Intent proxima;
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch ((int) drawerItem.getIdentifier()){

                            case 1:
                                proxima = new Intent(DashbordActivity.this, CadastroEmpresaActivity.class);
                                startActivity(proxima);
                                break;
                            case 3:
                                proxima = new Intent(DashbordActivity.this, CadastroCategoriaActivity.class);
                                startActivity(proxima);
                                break;
                            case 4:
                                proxima = new Intent(DashbordActivity.this, CadastroCategoriaActivity.class);
                                proxima.putExtra("UIDCategoria", "-KhHbm-rwUcYT2ieQOJi");
                                proxima.putExtra("nomeCategoria", "Cabelo");
                                startActivity(proxima);
                                break;
                            case 5:
                                proxima = new Intent(DashbordActivity.this, CadastroServicoActivity.class);
                                startActivity(proxima);
                                break;
                            case 6:
                                proxima = new Intent(DashbordActivity.this, AgendaCategoriaActivity.class);
                                startActivity(proxima);
                                break;
                            case 7:
                                proxima = new Intent(DashbordActivity.this, CadastroPromocaoActivity.class);
                                startActivity(proxima);
                                break;
                            case 9:
                                proxima = new Intent(DashbordActivity.this, ServicosPretadorActivity.class);
                                startActivity(proxima);
                                break;
                            case 10:
                                proxima = new Intent(DashbordActivity.this, ServicosPrestadosActivity.class);
                                startActivity(proxima);
                                break;
                            case 11:
                                proxima = new Intent(DashbordActivity.this, CadastroUsuarioActivity.class);
                                proxima.putExtra("UID", getUid());
                                startActivity(proxima);
                                break;
                            case 12:
                                FirebaseAuth.getInstance().signOut();
                                proxima = new Intent(DashbordActivity.this, LoginActivity.class);
                                startActivity(proxima);
                                finish();
                                break;
                        }
                        return false;
                    }
                })
                .withAccountHeader(header)
                .withSavedInstance(savedInstanceState)
                .build();

        if(perfilId == 1){
            navigationDrawer.addItem(expandableFuncionario);
            navigationDrawer.addItem(expandableCategoria);
            navigationDrawer.addItem(expandableServicos);
            navigationDrawer.addItem(expandablePromocoes);
        }else if(perfilId == 2){
            navigationDrawer.addItem(expandableServicoPrestador);
        }else if(perfilId == 3){
            navigationDrawer.addItem(expandableServicos);
            navigationDrawer.addItem(expandableConfiguracoes);
        }
        navigationDrawer.addItem(new SecondaryDrawerItem().withName("Sair").withLevel(1).withIcon(R.drawable.ic_exit_to_app_black_24dp).withIdentifier(12));
    }

    private void setPerfil(final Bundle savedInstanceState, final String emailLogado, int perfilId){

        if(perfilId == Perfil.CLIENTE){
            DatabaseReference refCliente = mDatabase.getReference().child("clientes").child(uid);
            refCliente.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cliente = dataSnapshot.getValue(Cliente.class);
                    nomeUsuario = cliente.getNome() + " " + cliente.getSobreNome();
                    criarNavigationDrawer(savedInstanceState, nomeUsuario, emailLogado);
                    Intent listaServicos = new Intent(DashbordActivity.this, AgendaCategoriaActivity.class);
                    startActivity(listaServicos);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else if(perfilId == Perfil.FUNCIONARIO){
            DatabaseReference refFuncionario = mDatabase.getReference().child("funcionarios");
            Query query = refFuncionario.orderByChild("email").equalTo(emailLogado);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snap : dataSnapshot.getChildren()){
                        funcionario = snap.getValue(Funcionario.class);
                        funcionario.setUid(snap.getKey());
                        nomeUsuario = funcionario.getNome();
                        criarNavigationDrawer(savedInstanceState, nomeUsuario, emailLogado);
                        break;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else if(perfilId == Perfil.ADMINISTRADOR){
            DatabaseReference refFuncionario = mDatabase.getReference().child("administrador");
            refFuncionario.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snap: dataSnapshot.getChildren()) {
                        administrador = snap.getValue(Administrador.class);
                        if(administrador.getEmail().equalsIgnoreCase(emailLogado)){
                            nomeUsuario = administrador.getNome();
                            criarNavigationDrawer(savedInstanceState, nomeUsuario, emailLogado);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
