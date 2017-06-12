package br.com.capelli.centrodebelezacapelli.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by alexadre on 09/04/17.
 */

public class BaseActivity extends AppCompatActivity{

    private ProgressDialog mProgressDialog;

    public void showProgressDialog(){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Carregando...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog(){
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    public String getUid(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
