package Comunications;

public class HealthTestChannel implements Runnable {

    private boolean working = true;
    private Channel channel;
    private long timeOut;
    private boolean killSocket = false;

    public HealthTestChannel(Channel channel, long timeOut) {
        this.channel = channel;
        this.timeOut = timeOut;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            while (working) {
                // Verificar si ha pasado más tiempo que timeOut desde que se
                // recibió el último mensaje
                long currentTime = System.currentTimeMillis();
                long timeLastMessage = channel.getRecievedTime();
                long diferencia = currentTime - timeLastMessage;
    
                if (diferencia > timeOut) {
                    try {
                        if (this.killSocket == true) {
    
                            System.out.println("Health Care detenido, matando socket");
                            channel.setDownChannel();
                            working = false;
                        }
                        
                        System.out.println("Health Care: el ultimo mensaje es de hace: " + diferencia);
                        this.killSocket = true;
                        this.channel.sendPing();
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    
            System.out.println("Thread Test Chanel terminado");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pararEjecucion() {
        System.err.println("Deteniendo Health Care Connection...");
        working = false;
    }

    // Getters And Setters
    public long getTimeOut() {
        return timeOut;
    }

    public boolean isWorking() {
        return working;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isKillSocket() {
        return killSocket;
    }

    public void setKillSocket(boolean killSocket) {
        this.killSocket = killSocket;
    }
}
