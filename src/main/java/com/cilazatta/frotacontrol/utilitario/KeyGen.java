package com.cilazatta.frotacontrol.utilitario;


import java.util.Base64;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class KeyGen {
    public static void main(String[] args) {

    	   SecretKey key = Jwts.SIG.HS256.key().build();

           String base64 = Base64.getEncoder().encodeToString(key.getEncoded());

           System.out.println("SECRET BASE64:");
           System.out.println(base64);
    }
}