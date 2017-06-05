package br.com.capelli.centrodebelezacapelli.view;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by alexadre on 07/05/17.
 */

public class BaseFragmentActivity extends FragmentActivity {

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
