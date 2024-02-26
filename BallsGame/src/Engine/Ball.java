package Engine;
import static java.lang.Thread.sleep;
import java.awt.Color;
import java.awt.Graphics;

public class Ball extends DinamicVO {
    private transient TGModel model;
    private boolean alive = true;
    private float mass = 5;
    private int rad = 30;
    

    // CONSTRUCTOR
    public Ball(TGModel model, VectorDTO velocity, CoordinatesDTO position) {
        super(velocity, position, new CoordinatesDTO(0, 0));
        this.model = model;
    }
    

    // METODOS
    public void checkNextMove(){
        this.calcNewPosition();
        
        this.model.checkBallMovement(this);
    }

    @Override
    public synchronized void calcNewPosition() {
        nextPosition.setX(Math.round(position.getX() + (velocity.getX())));
        nextPosition.setY(Math.round(position.getY() + (velocity.getY())));
        
    }
    
    @Override
    public void run() {
        nextPosition.setX(Math.round(position.getX() + (velocity.getX())));
        nextPosition.setY(Math.round(position.getY() + (velocity.getY())));
        
        while (alive) {
            try {
                checkNextMove();
                sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void move() {
        position.setX(this.nextPosition.getX());
        position.setY(this.nextPosition.getY());
    }
    
    public void bounce(CoordinatesDTO bouncePosition, int bounceDirection) {
        position.setX(bouncePosition.getX());
        position.setY(bouncePosition.getY());
        
        if(bounceDirection == 0){
            
            this.velocity.setX(this.velocity.getX() * (-1));
        } else {
            
            this.velocity.setY(this.velocity.getY() * (-1));
        }
    }
    
    @Override
    public Graphics paint(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(this.position.getX() - rad, this.position.getY() - rad, rad, rad);
        return g;
    }


    //GETTERS AND SETTERS
    public TGModel getModel() {
        return model;
    }


    public void setModel(TGModel model) {
        this.model = model;
    }


    public boolean isAlive() {
        return alive;
    }


    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    public float getMass() {
        return mass;
    }


    public void setMass(float mass) {
        this.mass = mass;
    }


    public int getRad() {
        return rad;
    }


    public void setRad(int rad) {
        this.rad = rad;
    }
}
