package Engine;

public abstract class DinamicVO extends VisualObject implements Runnable{
    public VectorDTO velocity;
    public CoordinatesDTO position;
    public CoordinatesDTO nextPosition;

    public DinamicVO (VectorDTO velocity, CoordinatesDTO position, CoordinatesDTO nextPosition) {
        this.velocity = velocity;
        this.position = position;
        this.nextPosition = nextPosition;
    }
    
    public abstract void calcNewPosition();

    public VectorDTO getVelocity() {
        return velocity;
    }

    public void setVelocity(VectorDTO velocity) {
        this.velocity = velocity;
    }

    public CoordinatesDTO getPosition() {
        return position;
    }

    public void setPosition(CoordinatesDTO position) {
        this.position = position;
    }

    public CoordinatesDTO getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(CoordinatesDTO nextPosition) {
        this.nextPosition = nextPosition;
    }
}
