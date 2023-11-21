package com.script;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ){
        try{
            Socket socket = new Socket("10.22.9.2", 3000);

                MioThread thread = new MioThread(socket);
                thread.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("Connessione effettuata con un canale server.");
                Scanner input = new Scanner(System.in);
                String testo;
                String messaggio;
                String username;
                do{
                    System.out.println("\nInserimento nome utente: ");
                    username = input.nextLine();
                    out.writeBytes(username + "\n");
                    messaggio = in.readLine();
                    if(messaggio.equals("!")){
                        
                        System.out.println("\nATTENZIONE: L'username inserito è già usato.");
                    }
                }while(messaggio.equals("!"));
                do{
                    System.out.println("\nOpzioni per comunicare: @everyone / Username (@exit per uscire)");
                    System.out.println("\nScrivi il nome dell'utente con cui vuoi comunicare: ");
                    username = input.nextLine();
                    out.writeBytes(username + "\n");
                    messaggio = in.readLine();
                    switch(messaggio){
                        case "#":
                            System.out.println("\nATTENZIONE: L'username inserito per comunicare non è valido");
                            break;
                        case "&":
                            System.out.println("\nHai chiuso la connessione");
                            break;
                        case ".":
                            System.out.println("\nInserisci il messaggio da inviare");
                            testo = input.nextLine();
                            out.writeBytes(testo);
                            break;
                        default:
                            System.out.println("Errore del server");
                    }
                }while(!messaggio.equals("&"));
                socket.close();
                input.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
