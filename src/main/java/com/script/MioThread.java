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
        try {
            while(!Thread.currentThread().isInterrupted()){
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String mess = "";
                mess = in.readLine();
                System.out.println(mess);
                if(Thread.currentThread().isInterrupted()){
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}