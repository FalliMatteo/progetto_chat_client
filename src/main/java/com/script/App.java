package com.script;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ){
        try{
            Scanner scanner = new Scanner(System.in);
            Socket socket = null;
            boolean found = false;
            while(!found){
                found = true;
                System.out.println("\nInsert the server's IP address");
                String address = scanner.nextLine();
                try{
                    socket = new Socket(address, 3000);
                }catch(UnknownHostException e){
                    System.out.println("\nServer not found");
                    found = false;
                }
            }
            OutputThread output = new OutputThread(socket);
            InputThread input = new InputThread(socket);
            input.start();
            output.start();
            System.out.println("\nConnection established");
            scanner.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
