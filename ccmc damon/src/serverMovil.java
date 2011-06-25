
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author daniel
 */
public class serverMovil extends Thread {
    private HashMap alertas =null;
    private HashMap camaras =null;    
    private int puerto =2003;
    private boolean matar=true;
    
    public serverMovil (int puerto, HashMap Alertas,HashMap Camaras){
        this.puerto = puerto;
        this.alertas = Alertas;
        this.camaras = Camaras;
    }

   
    @Override
    public void run(){

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(puerto);
            
        } catch (IOException e) {
            System.err.println("No se puede escuchar al puerto "+puerto);
            System.exit(-1);
        }
        try {
            while (matar)
                new conexionMovil(serverSocket.accept(),this.alertas,this.camaras).start();
            serverSocket.close();
        } catch (IOException ex) {
            
            System.out.println("Fallo de conexion");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
}

class conexionMovil extends Thread{

    private HashMap camaras =null;  
    private Socket soc;
    private String archivo=null;
    private Fotograma fo = null;
    private HashMap alertas= null;
    ObjectInputStream in;
    private ObjectInputStream entrada = null;
    private ObjectOutputStream salida = null;
    
    public conexionMovil(Socket accept, HashMap alertas, HashMap camaras) throws IOException {
                this.soc = accept;
        this.alertas = alertas;
        this.camaras = camaras;
             // entrada = new ObjectInputStream(soc.getInputStream());
              salida = new ObjectOutputStream(soc.getOutputStream());
    }
    
    @Override
    public void run(){
        try {
            System.out.println("Peticion de "+ soc.getInetAddress().getHostAddress());
            salida.writeObject(camaras);
            salida.flush();
            salida.writeObject(alertas);
            salida.flush();

            salida.close();
            soc.close();
        } catch (IOException ex) {
            Logger.getLogger(conexionMovil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
}
