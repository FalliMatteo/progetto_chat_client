package com.script;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


public class MioThread extends Thread{
    protected Socket socket;

    public MioThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        String messaggio;
        try {
            do{
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                messaggio = in.readLine();
                String username;
                String testo;
                switch(messaggio){
                    case "+":
                        username = in.readLine();
                        System.out.println(username + " si è unito alla chat");
                        break;
                    case "-":
                        username = in.readLine();
                        System.out.println(username + " ha abbandonato la chat");
                        break;
                    case "@":
                        username = in.readLine();
                        testo = in.readLine();
                        System.out.println(username + " ha scritto: " + testo);
                        break;
                    case "*":
                        username = in.readLine();
                        testo = in.readLine();
                        System.out.println(username + " ti ha scritto in privato: " + testo);
                }
            }while(messaggio.equals("x"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}