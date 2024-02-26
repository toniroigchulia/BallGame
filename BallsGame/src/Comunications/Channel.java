package Comunications;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import Engine.Ball;

public class Channel implements Runnable {

    private TGComunications tgComunications;
    private HealthTestChannel testChanel;

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket SOCKET;

    private int sendTime;
    private long recievedTime;

    private Interlocutor interlocutor;
    private boolean isClient;

    public Channel(TGComunications tgComunications, Interlocutor interlocutor) {

        this.tgComunications = tgComunications;
        this.interlocutor = interlocutor;
    }

    @Override
    public void run() {
        while (SOCKET != null) {

            // Leer mensajes entrantes
            System.out.println("\n" + "Esperando un mensaje...");
            this.dataIn();
        }

        System.out.println("Thread Channel terminado");
    }

    public synchronized void setSocket(Socket SOCKET) {
        try {
            this.SOCKET = SOCKET;
            // Inicializar los objetos BufferedReader y PrintWriter
            OutputStream os = this.SOCKET.getOutputStream();
            this.out = new ObjectOutputStream(os);
            InputStream is = this.SOCKET.getInputStream();
            this.in = new ObjectInputStream(is);

            // Una vez se ha creado el socket creamos el test channel para asegurar que siga
            // funcionando
            this.testChanel = new HealthTestChannel(this, 10000);
            new Thread(this.testChanel).start();
        } catch (IOException e) {

            System.out.println(e);
        }
    }

    public synchronized void setDownChannel() {
        // Si detectamos que la conexion no funciona queremos eliminamos el socket
        try {

            stopTestChannel();
            in.close();
            out.close();
            SOCKET.close();
            SOCKET = null;
            System.err.println("Matando el socket...");
        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            this.tgComunications.moveToDownChannel(this);
        }
    }

    // Metodo para mandar informacion
    public void sendData(Object object) {
        if (object instanceof Ball) {
            Ball b = (Ball) object;

            AppFrame appFrame = new AppFrame(AppFrameType.BALL, b);
            DataFrame data = new DataFrame(DataFrameType.APLICATION_FRAME, appFrame);

            try {
                out.writeObject(data);
                out.flush();
                System.out.println("Objeto mandado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Metodo para recibir informacion
    public void dataIn() {
        try {

            DataFrame data = (DataFrame) in.readObject();
            this.recievedTime = System.currentTimeMillis();
            if (data != null) {
                switch (data.getDataFramType()) {
                    case APLICATION_FRAME:

                        this.tgComunications.addObject((AppFrame) data.getSendObject());
                        System.out.println("Objecto Recibido");
                        break;
                    case INTERNAL_INFO:

                        break;
                    case FRAME_REFUSED:

                        break;
                    case KEEP_ALIVE:

                        System.out.println("Ping recibido");
                        this.sendPingBack();
                        break;
                    case KEEP_ALIVE_BACK:

                        System.out.println("Ping recibido de vuelta");
                        this.testChanel.setKillSocket(false);
                        break;
                    default:

                        System.out.println("Mensaje no tratado");
                        break;
                }
            }
        } catch (Exception e) {

            System.out.println(e);
            setDownChannel();
        }
    }

    public void sendPing() {
        try {
            DataFrame data = new DataFrame(DataFrameType.KEEP_ALIVE, "Ping");
            out.writeObject(data);
            out.flush();
            System.out.println("Ping mandado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPingBack() {
        try {
            DataFrame data = new DataFrame(DataFrameType.KEEP_ALIVE_BACK, "PingBack");
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Parar el hilo del test channel
    public void stopTestChannel() {
        if (testChanel != null) {
            testChanel.pararEjecucion();
            testChanel = null;
        }
    }

    // Getters And Setters
    public TGComunications getTgComunications() {
        return tgComunications;
    }

    public void setTgComunications(TGComunications tgComunications) {
        this.tgComunications = tgComunications;
    }

    public HealthTestChannel getTestChanel() {
        return testChanel;
    }

    public void setTestChanel(HealthTestChannel testChanel) {
        this.testChanel = testChanel;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public Socket getSOCKET() {
        return SOCKET;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public long getRecievedTime() {
        return recievedTime;
    }

    public void setRecievedTime(long recievedTime) {
        this.recievedTime = recievedTime;
    }

    public Interlocutor getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(Interlocutor interlocutor) {
        this.interlocutor = interlocutor;
    }

    public boolean isClient() {
        return isClient;
    }

    public void setIsClient(boolean wasClient) {
        this.isClient = wasClient;
    }
}
