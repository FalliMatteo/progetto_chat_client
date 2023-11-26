package com.script;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class OutputThread extends Thread{
    private Socket socket;
    public static String response;
    public static CountDownLatch latch;

    public OutputThread(Socket socket){
        this.socket = socket;
        OutputThread.response = null;
    }

    @Override
    public void run(){
        try{
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in, "UTF-8");
            String message = "";
            String text;
            do{
                System.out.println("\nInsert your username: ");
                // This is the input that makes problems
                while(scanner.hasNextLine()){
                    message = scanner.nextLine();
                };
                if(message.isEmpty()){
                    System.out.println("\nThe string is empty");
                }else{
                    latch = new CountDownLatch(1);
                    out.writeBytes(message + "\n");
                    latch.await();
                    if(response == null){
                        System.out.println("\nServer's error");
                    }
                    if(response.equals("!")){ 
                        System.out.println("\nYour username has already been inserted or is invalid");
                    }
                }
            }while(response == null || response.equals("!"));
            do{
                System.out.println("\nInsert the username of the user that you want to chat with or write one of the following commands:\n@everyone (to chat with all users)\n@username (to change your username)\n@exit (to leave the chat)");
                message = scanner.nextLine();
                switch(message){
                    case "@everyone":
                        System.out.println("\nInsert the message to send");
                        text = scanner.nextLine();
                        message = message + "ඞ" + text + "\n";
                        latch = new CountDownLatch(1);
                        out.writeBytes(message);
                        latch.await();
                        if(response.equals(".")){
                            System.out.println("\nMessage sended");
                        }else{
                            if(response.equals("!")){
                                System.out.println("\nThere are no users online");
                            }else{
                                System.out.println("\nServer's error");
                            }
                        }
                        break;
                    case "@exit":
                        latch = new CountDownLatch(1);
                        out.writeBytes(message + "\n");
                        latch.await();
                        if(response.equals("#")){
                            System.out.println("\nYou closed the connection");
                        }else{
                            System.out.println("\nServer's error");
                        }
                        break;
                    case "@username":
                        System.out.println("\nInsert your new username: ");
                        text = scanner.nextLine();
                        message = message + "ඞ" + text + "\n";
                        latch = new CountDownLatch(1);
                        out.writeBytes(message);
                        latch.await();
                        if(response == null){
                            System.out.println("\nServer's error");
                        }else{
                            if(response.equals("!")){
                                System.out.println("\nThis username has already been inserted or is invalid");
                            }else{
                                if(response.equals(".")){
                                    System.out.println("\nUsername changed");
                                }
                            }
                        }
                        break;
                    default:
                        System.out.println("\nInsert the message to send");
                        text = scanner.nextLine();
                        message = message + "ඞ" + text + "\n";
                        latch = new CountDownLatch(1);
                        out.writeBytes(message);
                        latch.await();
                        if(response.equals("!")){
                            System.out.println("\nThis username doesn't exist");
                        }else{
                            if(response.equals(".")){
                                System.out.println("\nMessage sended");
                            }else{
                                System.out.println("\nServer's error");
                            }
                        }
                }
            }while(!response.equals("#"));
            socket.close();
            scanner.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
