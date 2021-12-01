import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.plaf.PopupMenuUI;

public class Server{
    ServerSocket server =null;
    Socket client =null;
    String stringaricevuta= null;
    String n= null;
    String stringaModificata=null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    int numero;
    int conta;



    public Socket attendi(){
        try{
            System.out.println("Server in attesa");
            server =new ServerSocket(6789);
            client = server.accept();
            server.close();
            System.out.println("Connessione effettuata");
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient= new DataOutputStream(client.getOutputStream());
            outVersoClient.writeBytes(" Connessione effettuata"+'\n'); 

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server!");
        }
        return client;
    }

    public void comunica(){
        try{
            for(;;){
            if(conta>0){
            int nClient=Integer.parseInt(n);
            if(nClient>numero){
                outVersoClient.writeBytes(" Troppo grande"+'\n');
                n= inDalClient.readLine();
            }
            if(nClient<numero){
                outVersoClient.writeBytes(" Troppo piccolo"+'\n');
                n= inDalClient.readLine();
                
            }
            if(nClient==numero){
                nClient=0;
                outVersoClient.writeBytes("Hai indovinato in "+ conta +" tentativi. Vuoi rigiocare(Y/N)?"+'\n');
                conta=0;
                stringaricevuta=inDalClient.readLine();
                if(stringaricevuta.equals("N")){
                    outVersoClient.writeBytes("socket in chiusura"+'\n');
                    client.close();
                    
                    break;
            }
            
            }
        }
        else{
        outVersoClient.writeBytes("Indovina il numero:"+'\n');
            n= inDalClient.readLine();
            numero = new java.util.Random().nextInt(100) + 1;
        }
            conta++;
    }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col client!");
            System.exit(1);
        }
    }
    public static void main( String[] args )
    {
        Server servente = new Server();
        servente.attendi();
        servente.comunica();
    }

}





