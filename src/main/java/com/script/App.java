package com.script;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    static public String Inserisci(Scanner input){
        System.out.println("Inserimento nome utente: ");
        String testo = input.nextLine();
        return testo;
    }

    static public String Comunica(Scanner input){
        System.out.println("\nOpzioni per comunicare: @everyone - Nome");
        System.out.println("\nInserisci l'username per comunicare: ");
        String nome = input.nextLine();
        return nome;
    }


    public static void main( String[] args ){

        

        try{
            Socket socket = new Socket("localhost", 3000);

                MioThread thread = new MioThread(socket);
                thread.start();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                System.out.println("Connessione effettuato a un canale Server.");
                Scanner input = new Scanner(System.in);
                String testo = "";
                String messaggio = "";
                do{
                    System.out.println("Inserimento nome utente: ");
                    testo = input.nextLine();
                    out.writeBytes(testo + "\n");
                    messaggio = in.readLine();
                    if(messaggio.equals("!")){
                        
                        System.out.println("ATTENZIONE: Sei stupido, l'username inserito è già usato.\n");
                    }
                }while(messaggio.equals("!"));
                do{

                    do{
                        System.out.println("\nOpzioni per comunicare: @everyone / Username\n");
                        System.out.println("Scrivi il nome dell'utente con cui vuoi comunicare: ");
                        testo = input.nextLine();
                        out.writeBytes(testo + "\n");
                        messaggio = in.readLine();
                        if(messaggio.equals("!")){
                            System.out.println("\nATTENZIONE: Coglione, l'username inserito per comunicare non è valido");
                        }
                    }while(messaggio.equals("#"));
                    if(messaggio.equals("-")){
                        System.out.println("\nIl client" + testo + "chiude la connessione\n");
                    }else{
                        // INVIO MESSAGGIO
                    }
                }while(messaggio.equals("-"));
                socket.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
