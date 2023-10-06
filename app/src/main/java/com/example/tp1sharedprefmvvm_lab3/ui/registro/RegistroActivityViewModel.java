package com.example.tp1sharedprefmvvm_lab3.ui.registro;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tp1sharedprefmvvm_lab3.Model.Usuario;
import com.example.tp1sharedprefmvvm_lab3.request.ApiClient;
import com.example.tp1sharedprefmvvm_lab3.ui.login.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegistroActivityViewModel extends AndroidViewModel {

    public MutableLiveData<Usuario> user;
    private MutableLiveData<Bitmap> foto;

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<Usuario> getUser() {
        if(user == null){
            user = new MutableLiveData<>();
        }
        return user;
    }
    public LiveData<Bitmap> getFoto(){
        if(foto==null){
            foto=new MutableLiveData<>();
        }
        return foto;

    }

    //si exiten un usuario leo y muestro sus datos
    public void datosDeUser(boolean data){
        if (data){
            user.setValue(ApiClient.leer(getApplication().getApplicationContext()));
        }
    }


    public void guardar(String nombre, String apellido, String dni, String mail,String pass){

        Usuario usuario = new Usuario(Long.parseLong(dni),nombre,apellido,mail,pass);
        ApiClient.guardar(getApplication().getApplicationContext(),usuario);

        Intent intent = new Intent(getApplication().getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().getApplicationContext().startActivity(intent);

    }

    public void respuetaDeCamara(int requestCode, int resultCode, Intent data, int REQUEST_IMAGE_CAPTURE, Usuario usuarioActual){
        // Log.d("salida",requestCode+"");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Recupero los datos provenientes de la camara.
            Bundle extras = data.getExtras();
            //Casteo a bitmap lo obtenido de la camara.
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Rutina para optimizar la foto,
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            foto.setValue(imageBitmap);


            //Rutina para convertir a un arreglo de byte los datos de la imagen
            byte [] b=baos.toByteArray();


            //Aquí podría ir la rutina para llamar al servicio que recibe los bytes.
            File archivo =new File(getApplication().getApplicationContext().getFilesDir(),usuarioActual.getDni() +".png");
            usuarioActual.setFoto(usuarioActual.getDni()+".png");
            if(archivo.exists()){
                archivo.delete();
            }
            try {
                FileOutputStream fo=new FileOutputStream(archivo);
                BufferedOutputStream bo=new BufferedOutputStream(fo);
                // ObjectOutputStream OOS = new ObjectOutputStream(bo);
                bo.write(b);
                bo.flush();
                bo.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void leerFotoArchivo(String archivo) {
        File archivoF = new File(getApplication().getApplicationContext().getFilesDir(), archivo);

        try {
            FileInputStream FIS = new FileInputStream(archivoF);
            BufferedInputStream BIS = new BufferedInputStream(FIS);
            //   ObjectInputStream OIS = new ObjectInputStream(BIS);

            byte b[] ;
            b = new byte[BIS.available()];
            BIS.read(b);

            Bitmap BM =  BitmapFactory.decodeByteArray(b, 0, b.length);
            this.foto.setValue(BM);


            BIS.close();
            FIS.close();

        } catch (FileNotFoundException e) {
            Log.d("salida",e.toString());
        } catch (IOException e) {
            Log.d("salida",e.toString());
        }

    }
}
