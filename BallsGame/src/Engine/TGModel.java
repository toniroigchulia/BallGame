package Engine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TGModel {
    private TGLocalController controller;
    private List<VisualObject> visualElements = Collections.synchronizedList(new ArrayList<>()); 


    // CONSTRUCTOR
    public TGModel(TGLocalController controller) {
        this.controller = controller;
    }


    // METODOS
    public VectorDTO simplifyVelocity(VectorDTO velocity) {
        VectorDTO normalizedVel = new VectorDTO(0, 0);
        float x = velocity.getX();
        float y = velocity.getY();

        float normalizedX = (float) (x / 20);
        float normalizedY = (float) (y / 20);

        normalizedVel.setX(normalizedX);
        normalizedVel.setY(normalizedY);

        return normalizedVel;
    }

    public void addBall(VectorDTO ballVelocity, CoordinatesDTO ballInitPosition) {
        Ball ball = new Ball(this, simplifyVelocity(ballVelocity), ballInitPosition);
        this.visualElements.add(ball);

        new Thread(ball).start();
    }

    public void addBall(Ball ball) {
        ball.setModel(this);
        ball.setAlive(true);
        this.visualElements.add(ball);
        new Thread(ball).start();
    }

    public void removeBall(Ball ball) {
        ball.setAlive(false);
        for (int i = 0; i < visualElements.size(); i++) {
            if (ball == visualElements.get(i)) {
                visualElements.remove(i);
            }
        }
    }

    public synchronized void checkBallMovement(Ball ball) {
        ArrayList<Ball> ballsColiding = new ArrayList<>();

        for (int i = 0; i < visualElements.size(); i++) {
            if(visualElements.get(i) instanceof Ball){

                if ((isColiding(ball, ((Ball) visualElements.get(i)))) && ball != ((Ball) visualElements.get(i))) {
    
                    ballsColiding.add((Ball) visualElements.get(i));
                }
            }
        }

        this.controller.checkBallColison(ball, ballsColiding);
    }

    private boolean isColiding(Ball mainBall, Ball possibleColisionBall) {
        if (possibleColisionBall != null) {
            boolean impacto;
            int distanciaCentros = calcColision(mainBall.getNextPosition(), possibleColisionBall.getNextPosition());
            int sumaRadios = mainBall.getRad() + possibleColisionBall.getRad();
            impacto = distanciaCentros <= sumaRadios;
            return impacto;
        }

        return false;
    }

    private int calcColision(CoordinatesDTO mainBallPosition, CoordinatesDTO possibleColisionBallPosition) {
        int distanciaX = mainBallPosition.getX() - possibleColisionBallPosition.getX();
        int distanciaY = mainBallPosition.getY() - possibleColisionBallPosition.getY();

        int distanciaEntreCentros = (int) (Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY) + 20);

        return distanciaEntreCentros;
    }


    // GETTERS AND SETTERS
    public List<VisualObject> getVisualElements() {
        return this.visualElements;
    }
}
