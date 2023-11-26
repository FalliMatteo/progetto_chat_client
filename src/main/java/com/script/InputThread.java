package com.script;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputThread extends Thread{
    private Socket socket;

    public InputThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        String message;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            do{
                message = in.readLine();
                if(message.equals(".") || message.equals("!") || message.equals("#") || message.equals(null)){
                    OutputThread.response = message;
                    OutputThread.latch.countDown();
                    notify();
                }else{
                    System.out.println(message);
                }
            }while(message.equals("#"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
}