package MainController;

import Engine.*;
import java.util.ArrayList;
import Comunications.*;

public class TGController {
    private TGLocalController tgLocalController;
    private TGComunications tgComunications;
    private ArrayList<Interlocutor> peerInterlocutors = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        TGController peerController = new TGController();
        peerController.init();
    }

    public void init() {
        this.peerInterlocutors.add(new Interlocutor("localhost", PeerLocation.EAST));
        this.peerInterlocutors.add(new Interlocutor("localhost", PeerLocation.WEST));
        this.tgLocalController = new TGLocalController(this);
        this.tgComunications = new TGComunications(this);
    }

    public void addObject(AppFrame appFrame) {
        try {
            switch (appFrame.getAppFrameType()) {
                case BALL:
                    System.out.println("Añadimos bola");
                    this.tgLocalController.addBall((Ball) appFrame.getObject());
                    break;
                case ASTEROID:

                    break;
                case BULLET:

                    break;
                case CONTROL_ACTION:

                    break;
                case MISIL:

                    break;
                case PLAYER_SHIP:

                    break;
                case PLAYER_SHIP_ACTION:

                    break;
                default:
                    break;
            }
        } catch (Exception e) {
        
            System.out.println(e);
        }
    }

    public void sendObject(Ball ball, Enum<PeerLocation> direc) {
        this.tgComunications.sendObject(ball, direc);
    }

    public ArrayList<Interlocutor> getPeerInterlocutors() {
        return peerInterlocutors;
    }

    public void setPeerInterlocutors(ArrayList<Interlocutor> peerInterlocutors) {
        this.peerInterlocutors = peerInterlocutors;
    }

    public TGLocalController getTgLocalController() {
        return tgLocalController;
    }

    public TGComunications getTgComunications() {
        return tgComunications;
    }
}