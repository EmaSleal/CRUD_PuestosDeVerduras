
package com.dom.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class EncriptarPassword {
    public static void main(String[] args) {
        
        var pass="123";
        System.out.println("password: "+pass);
        System.out.println("encriptado: "+encripta(pass));
    }
    
    public static String encripta(String pass){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(pass);
    }
}
