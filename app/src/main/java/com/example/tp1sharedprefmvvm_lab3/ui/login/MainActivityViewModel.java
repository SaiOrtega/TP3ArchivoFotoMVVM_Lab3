package com.example.tp1sharedprefmvvm_lab3.ui.login;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tp1sharedprefmvvm_lab3.Model.Usuario;
import com.example.tp1sharedprefmvvm_lab3.request.ApiClient;
import com.example.tp1sharedprefmvvm_lab3.ui.registro.RegistroActivity;

public class MainActivityViewModel extends AndroidViewModel {
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void userLogin(String email, String password){

        if(email.equals("")|| password.equals("")){
            Toast.makeText(this.getApplication(), "No pueden haber campos vacios", Toast.LENGTH_SHORT).show();
        }else{
            //si extiste los datos de usuario obtengo un booleano
            boolean userlog = ApiClient.login(getApplication().getApplicationContext(),email,password);

            //genero un Intent que envia a la otra Activity,(true o false)
            if(userlog){
                Intent intent = new Intent(getApplication().getApplicationContext(), RegistroActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("uslog", userlog);

                //no necesito el context por que uso el getApp...Context
                getApplication().getApplicationContext().startActivity(intent);
            }else{
                Toast.makeText(this.getApplication(), "Usuario o contraseña Incorrectos", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
