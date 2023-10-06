package com.example.tp1sharedprefmvvm_lab3.Model;

import java.io.Serializable;

public class Usuario implements Serializable {
    public long dni;
    public String Nombre;
    public String apellido;
    public String mail;
    public String password;
    public String foto;


    public Usuario(long dni, String nombre, String apellido, String mail, String password) {
        this.dni = dni;
        Nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.password = password;
        this.foto = dni+".png";
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String fotodir) {
        this.foto = fotodir;
    }

    public Usuario(){};

    public long getDni() {
        return dni;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "dni=" + dni +
                ", Nombre='" + Nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
