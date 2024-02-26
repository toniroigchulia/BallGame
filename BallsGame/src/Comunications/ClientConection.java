package Comunications;
import java.net.Socket;

public class ClientConection implements Runnable {
    private TGComunications tgComunications;
    private Socket SOCKET;

    public ClientConection(TGComunications tgComunications) {
        this.tgComunications = tgComunications;
    }

    @Override
    public void run() {
        while (true) {
            this.SOCKET = new Socket();
            createConnection();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
    
                throw new RuntimeException(ex);
            }
        }
    }

    public void createConnection() {
        if (this.tgComunications.getDownChannels() != null) {
            for (int i = 0; i < this.tgComunications.getDownChannels().size(); i++) {
                try {

                    System.out.println("Conectando como cliente al canal: " + i);
                    this.SOCKET = new Socket(this.tgComunications.getDownChannels().get(i).getInterlocutor().getIP(), 8000);
                    this.tgComunications.addChannel(SOCKET, i);
                    System.out.println("Conexion como cliente establecida");
                } catch (Exception e) {
                
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {

                        throw new RuntimeException(ex);
                    }
                }
            }
        }
    }

    // Getters And Setters
    public TGComunications getTgComunications() {
        return tgComunications;
    }

    public void setTgComunications(TGComunications tgComunications) {
        this.tgComunications = tgComunications;
    }

    public Socket getSOCKET() {
        return SOCKET;
    }

    public void setSOCKET(Socket sOCKET) {
        SOCKET = sOCKET;
    }
}
