package Engine;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class TGViewer extends JFrame implements MouseListener {
    private TGLocalController controller;
    private ControlPanel controlPanel;
    private VisualPanel visualPanel;
    private int xSize = 920;
    private int ySize = 720;
    
    private VectorDTO ballVelocity;
    private CoordinatesDTO ballInitPosition;

    public TGViewer(TGLocalController controller) {
        this.controlPanel = new ControlPanel();
        this.controller = controller;
        
        this.visualPanel = new VisualPanel(this);
        Thread threadVisual = new Thread(this.visualPanel);
        threadVisual.start();

        configureFrame();
    }
    
    private void configureFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.setTitle("Balls Game");
        this.addMouseListener(this);
        this.setSize(xSize, ySize);
        this.setVisible(true);
        this.addUIComponents();
    }

    private void addUIComponents() {
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        this.add(visualPanel, c);
    }
    
    public List<VisualObject> getVisualElements() {
        return this.controller.getVisualElements();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.ballInitPosition = new CoordinatesDTO();
        this.ballInitPosition.setX(e.getX());
        this.ballInitPosition.setY(e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ballVelocity = new VectorDTO();
        ballVelocity.setX(ballInitPosition.getX() - e.getX());
        ballVelocity.setY(ballInitPosition.getY() - e.getY());
        
        if(!((ballVelocity.getX() == 0) && (ballVelocity.getY() == 0))){
        
            this.controller.addBall(ballVelocity, ballInitPosition);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public TGLocalController getController() {
        return controller;
    }

    public void setController(TGLocalController controller) {
        this.controller = controller;
    }

    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    public VisualPanel getVisualPanel() {
        return visualPanel;
    }

    public void setVisualPanel(VisualPanel visualPanel) {
        this.visualPanel = visualPanel;
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public VectorDTO getBallVelocity() {
        return ballVelocity;
    }

    public void setBallVelocity(VectorDTO ballVelocity) {
        this.ballVelocity = ballVelocity;
    }

    public CoordinatesDTO getBallInitPosition() {
        return ballInitPosition;
    }

    public void setBallInitPosition(CoordinatesDTO ballInitPosition) {
        this.ballInitPosition = ballInitPosition;
    }
}
