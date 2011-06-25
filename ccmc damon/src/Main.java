import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
   
    
    public static void main(String[] args) throws Exception {

      int puerto = 2002;
      int puerto2 = 2003;
      if (args.length > 0) {
          if (args[0].equals("-p")){
                puerto = Integer.parseInt(args[1]);
          }
      }
      File file = new File("index.html");
      if(file.exists()){
          file.delete();
       }
        HashMap Alertas = new HashMap();
         HashMap Camaras = new HashMap();
      
        file.createNewFile();
        SimpleWebServer webserver = new SimpleWebServer(2004);
       // webserver.start();
        Server g = new Server(puerto,"index.html",Alertas, Camaras);
        serverMovil m = new serverMovil(puerto2, Alertas, Camaras);
        m.start();
        g.start();

      
    }

}

