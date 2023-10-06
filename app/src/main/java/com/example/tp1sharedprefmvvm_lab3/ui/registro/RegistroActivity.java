package com.example.tp1sharedprefmvvm_lab3.ui.registro;

import static android.Manifest.permission_group.CAMERA;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.tp1sharedprefmvvm_lab3.Model.Usuario;


import com.example.tp1sharedprefmvvm_lab3.databinding.ActivityMainViewRegistroBinding;

public class RegistroActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ActivityMainViewRegistroBinding binding;
    private RegistroActivityViewModel mv;
    private Usuario usuarioActual = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainViewRegistroBinding.inflate(getLayoutInflater());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroActivityViewModel.class);

        setContentView(binding.getRoot());
        //recibo el intent y obtengo el boolean
        Intent intent=getIntent();

        //el observer trae los datos a la vista siempre que exista el us
        mv.getUser().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                    binding.etNombre.setText(usuario.getNombre());
                    binding.etApellido.setText(usuario.getApellido());
                    binding.etDni.setText(usuario.getDni() + "");
                    binding.etMail.setText(usuario.getMail());
                    binding.etPassword.setText(usuario.getPassword());
                    binding.btGuardar.setText("Editar");
                    binding.btGuardar.setBackgroundColor(Color.parseColor("#A5D6A7"));
                    usuarioActual = usuario;
                    mv.leerFotoArchivo(usuarioActual.getFoto());

            }
        });

        binding.btTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });


        //si no hay usuario no muestro nada
        mv.datosDeUser(intent.getBooleanExtra("uslog",false));

        binding.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mv.guardar(binding.etNombre.getText().toString(),binding.etApellido.getText().toString(),binding.etDni.getText().toString(),binding.etMail.getText().toString(),binding.etPassword.getText().toString());

            }
        });

        mv.getFoto().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                binding.imgFoto.setImageBitmap(bitmap);
            }
        });

    }

 /*   public void tomarFoto(){
//startActivityForResult es otra forma de iniciar una activity, pero esperando desde donde la llamé un resultado
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mv.respuetaDeCamara(requestCode, resultCode, data, 1,usuarioActual);

    }


}