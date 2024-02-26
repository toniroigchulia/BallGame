package Comunications;

import java.net.Socket;

public class PeerIDIdentificator implements Runnable{
    private Socket CLSOCK;
    private ServerConection serverConection;
    
    public PeerIDIdentificator(ServerConection serverConection, Socket CLSOCK) {
        this.serverConection = serverConection;
        this.CLSOCK = CLSOCK;
    }

    @Override
    public void run() {
        if(CLSOCK != null){
        
            System.out.println("Direccion de la Conexion: " + CLSOCK.getInetAddress().getHostAddress());
            try {
                for (int i = 0; i < this.serverConection.getTgComunications().getDownChannels().size(); i++) {
                    // Si el programa se ejecuta en varios ordenadores descomentar el IF para que el ServerConector compruebe si la conexion es valida o no.
                    //if(this.serverConection.getTgComunications().getDownChannels().get(i).getInterlocutor().getIP() == CLSOCK.getInetAddress().getHostAddress()){

                        this.serverConection.getTgComunications().addChannel(CLSOCK, i);
                    //}
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}