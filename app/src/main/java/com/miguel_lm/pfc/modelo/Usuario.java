package com.miguel_lm.pfc.modelo;

import androidx.annotation.NonNull;

public class Usuario {

    private String token;
    private String uid;
    private String numSocio;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String fechaNaci;
    private String telefono;
    private String email;
    private String password;
    private String rol;
    private boolean isAdmin;

    public Usuario(){
    }

    public Usuario(String uid, String numSocio, String nombre, String apellido1, String apellido2, String fechaNaci, String telefono, String email, String password, String rol, boolean isAdmin) {
        this.uid = uid;
        this.numSocio = numSocio;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNaci = fechaNaci;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.isAdmin = isAdmin;
    }

    public Usuario(String uid, String numSocio, String nombre, String apellido1, String apellido2, String fechaNaci, String telefono, String email, String password) {
        this.uid = uid;
        this.numSocio = numSocio;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNaci = fechaNaci;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.rol = "user";
        this.isAdmin = false;
    }

    public Usuario(String token,String uid, String numSocio, String nombre, String apellido1, String apellido2, String fechaNaci, String telefono, String email, String password) {
        this.token = token;
        this.uid = uid;
        this.numSocio = numSocio;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.fechaNaci = fechaNaci;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.rol = "user";
        this.isAdmin = false;
    }

    /*public Usuario(String token,String uid,String nombre){
        this.token = token;
        this.uid = uid;
        this.nombre = nombre;
    }*/

    public Usuario(String nombre,String apellido1, String apellido2){
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public String  setUid(String uid) {
        this.uid = uid;

        return uid;
    }

    public String getNumSocio() {
        return numSocio;
    }

    public String setNumSocio(String numSocio) {
        this.numSocio = numSocio;

        return numSocio;
    }

    public String getNombre() {
        return nombre;
    }

    public String setNombre(String nombre) {
        this.nombre = nombre;

        return nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public String setApellido1(String apellido1) {
        this.apellido1 = apellido1;

        return apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public String setApellido2(String apellido2) {
        this.apellido2 = apellido2;

        return apellido2;
    }

    public String getFechaNaci() {
        return fechaNaci;
    }

    public String setFechaNaci(String fechaNaci) {
        this.fechaNaci = fechaNaci;

        return fechaNaci;
    }

    public String getTelefono() {
        return telefono;
    }

    public String setTelefono(String telefono) {
        this.telefono = telefono;

        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String setEmail(String email) {
        this.email = email;

        return email;
    }

    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        this.password = password;

        return password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @NonNull
    @Override
    public String toString() {

        return nombre + " " + apellido1 + " " + apellido2;
    }
}
