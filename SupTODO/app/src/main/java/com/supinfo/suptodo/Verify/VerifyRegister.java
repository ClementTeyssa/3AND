package com.supinfo.suptodo.Verify;


public class VerifyRegister {

    protected boolean isRegistered;
    protected String str_register;

    public VerifyRegister(String username, String firstname, String lastname, String mail, String password,
                          String confirm_password) {
        //Verification que les données ne soient pas vides
        // Que les password ne soit pas différents
        if (!verify_register_username_notEmpty(username) ||
                !verify_register_firstname_notEmpty(firstname) ||
                !verify_register_lastname_notEmpty(lastname) ||
                !verify_register_mail_notEmpty(mail) ||
                !verify_register_confirm_password_notEmpty(confirm_password) ||
                !verify_register_password_notEmpty(password)) {
            this.isRegistered = false;
            setStr_register("Empty field");
        } else if (!verify_register_password(password, confirm_password)) {
            this.isRegistered = false;
            setStr_register("Different password");
        } else {
            this.isRegistered = true;
            setStr_register("Succesful register!");
        }
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public void setRegistered(boolean registered) {
        isRegistered = registered;
    }

    public String getStr_register() {
        return str_register;
    }

    public void setStr_register(String str_register) {
        this.str_register = str_register;
    }

    public boolean verify_register_password(String password, String confirm_password){
        return password.equals(confirm_password);
    }

    public boolean verify_register_mail_notEmpty(String mail){
        return !mail.isEmpty();
    }

    public boolean verify_register_firstname_notEmpty(String firstname){
        return !firstname.isEmpty();
    }

    public boolean verify_register_lastname_notEmpty(String lastname){
        return !lastname.isEmpty();
    }

    public boolean verify_register_username_notEmpty(String username){
        return !username.isEmpty();
    }

    public boolean verify_register_password_notEmpty(String password){ return !password.isEmpty(); }

    public boolean verify_register_confirm_password_notEmpty(String confirm_password){
        return !confirm_password.isEmpty();
    }
}
