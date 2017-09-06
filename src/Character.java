import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Stack;

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



class CharacterWithPhysics extends Character{
    private float velocityX = 0.0f;
    private float velocityY = 0.0f;
    private float gravity = 0.5f;
    boolean isOnGround = false;

    public CharacterWithPhysics() {
        super(0,0,150, 100, new BoundingBox(0,0,150,100));
    }

    public CharacterWithPhysics(int xPos, int yPos, int xSize, int ySize, BoundingBox boundingBox, float velocityX, float velocityY) {
        super(xPos, yPos, xSize, ySize, boundingBox);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public void render(Graphics g, ArrayList<BoundingBox> arr){
        step(arr);
        getBoundingBox().render(g);
    }

    public void step(ArrayList<BoundingBox> arr){
        velocityY += gravity;

        float oldYPos =  getYPos();
        float oldXPos =  getXPos();

        setXPos(getXPos() + velocityX);
        setYPos(getYPos() + velocityY);

        isOnGround = false;

        for(BoundingBox b : arr) {
            if (b.doesItTouchTop(getBoundingBox())) {
                setYPos(b.getYPos() - getYSize() - 0.5f);
                velocityY = 0.0f;
                isOnGround = true;
            }else if(b.doesItTouchLeft(getBoundingBox())){
                setXPos(b.getXPos() - getXSize());
            }else if(b.doesItTouchRight(getBoundingBox())){
                setXPos(b.getXPos() + b.getXSize());
            }


        }

        if (getXPos() < 0)
            setXPos(0);

        setBoundingBox(new BoundingBox((int) getXPos(),(int) getYPos(),getXSize(),getYSize()));
    }


}

class ControllableCharacter extends CharacterWithPhysics {
    public void goLeft(){
        setXPos(getXPos() - 8f);
    }

    public void goRight(){
        setXPos(getXPos() + 8f);
    }

    public void jump(){
        if(isOnGround) {
            setYPos(getYPos() - 10);
            setVelocityY(getVelocityY() - 16.0f);
        }
    }
}


class BurgerCharacter extends  ControllableCharacter{
    public Stack<Image> items = new Stack<>();
    private Image burgerTopImage;
    private Image burgerBottomImage;
    private Image patty;


    public void init() throws SlickException {
        burgerTopImage = new Image("images/BurgerBunTop.png");
        burgerBottomImage = new Image("images/BurgerBunBottom.png");
        patty = new Image("images/Patty.png");
    }

    @Override
    public void render(Graphics g, ArrayList<BoundingBox> arr) {
        super.render(g, arr);

        g.drawImage(burgerBottomImage, getXPos(),getYPos() + getYSize() - 50, 0, 0, 150, 50);
        g.drawImage(patty, getXPos(), getYPos() + getYSize() - 35, 0, 0, 150, 25);
        g.drawImage(burgerTopImage, getXPos(),getYPos() + getYSize() - 80, 0, 0, 150, 50);

    }

}