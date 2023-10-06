package com.example.tp1sharedprefmvvm_lab3.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tp1sharedprefmvvm_lab3.Model.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ApiClient {

    private static SharedPreferences sp;
    private static final String FILE_NAME ="user_data.txt";


    public static void guardar(Context context, Usuario usuario){
        File carpeta=context.getFilesDir();
        File archivo=new File(carpeta,FILE_NAME);
        try{
            FileOutputStream fos = new FileOutputStream(archivo);
            BufferedOutputStream bos=new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(usuario);
            bos.flush();
            oos.close();
            fos.close();
            Log.d("guardar",usuario.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Usuario leer(Context context){
        Usuario usuario = null;
        try{
            FileInputStream fis = context.openFileInput(FILE_NAME);
            BufferedInputStream bis=new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario)ois.readObject();
            ois.close();
            fis.close();
            if(usuario!=null){
                Log.d("leer",usuario.toString());
            }else{
                Log.d("leer","usuario nulllll");
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public static Boolean login(Context context, String email, String pass){
       Usuario usuario = leer(context);
       if(usuario!=null && usuario.getMail().equals(email) && usuario.getPassword().equals(pass)){
           return true;
       }else {
           return false;
       }

    }

}
