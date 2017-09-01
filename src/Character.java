import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

/**
 * Created by 164543 on 8/30/2017.
 */
abstract class Character {
    private float xPos;
    private float yPos;
    private int xSize;
    private int ySize;
    private BoundingBox boundingBox;

    public Character(int xPos, int yPos, int xSize, int ySize, BoundingBox boundingBox) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;
        this.boundingBox = boundingBox;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public int getXSize() {
        return xSize;
    }

    public void setXSize(int xSize) {
        this.xSize = xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public void setYSize(int ySize){
        this.ySize = ySize;
    }
}



class ControllableCharacter extends Character{
    private float velocityX = 0.0f;
    private float velocityY = 0.0f;
    private float gravity = 0.5f;

    public ControllableCharacter() {
        super(0,0,150, 100, new BoundingBox(0,0,150,100));
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void render(Graphics g, ArrayList<BoundingBox> arr){
        g.setColor(Color.cyan);
        step(arr);
        System.out.println("x: " + getXPos() + "; y: " + getYPos() + "; w: " + getXSize() + "; h: " + getYSize() + "; vx: " + velocityX + "; vy: " + velocityY);
        g.fillRect(getXPos(), getYPos(), getXSize(), getYSize());
        getBoundingBox().render(g);
    }

    public void step(ArrayList<BoundingBox> arr){
        velocityY += gravity;
        setXPos(getXPos() + velocityX);
        setYPos(getYPos() + velocityY);

        for(BoundingBox b : arr) {
            System.out.println(b.doesItCollide(getBoundingBox()));
            if (b.doesItCollide(getBoundingBox()) == 2 || b.doesItCollide(getBoundingBox()) == 3) {
                setYPos(b.getYPos() - getYSize());
                velocityY = 0.0f;
            }
            if (b.doesItCollide(getBoundingBox()) == 1 || b.doesItCollide(getBoundingBox()) == 3)
                velocityX *= -1;
        }

        setBoundingBox(new BoundingBox((int) getXPos(),(int) getYPos(),getXSize(),getYSize()));
    }
}