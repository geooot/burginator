import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.newdawn.slick.*;

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

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public void render(Graphics g, ArrayList<BoundingBox> arr){
        step(arr);
        //getBoundingBox().render(g);
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
                velocityY = 0;
                velocityX = 0.0f;
                isOnGround = true;
            }else if(b.doesItTouchLeft(getBoundingBox())){
                setXPos(b.getXPos() - getXSize());
            }else if(b.doesItTouchRight(getBoundingBox())){
                setXPos(b.getXPos() + b.getXSize());
            }
        }


        if (getXPos() < 0)
            setXPos(0);

        if(getXPos() >= 1850)
            setXPos(1850);

        setBoundingBox(new BoundingBox((int) getXPos(),(int) getYPos(),getXSize(),getYSize()));
    }


}

class ControllableCharacter extends CharacterWithPhysics {
    public int lastMovement = 1;
    public void goLeft(){
        lastMovement = -1;
        setXPos(getXPos() - 8f);
    }

    public void goRight(){
        lastMovement = 1;
        setXPos(getXPos() + 8f);
    }

    public void jump(){
        if(isOnGround) {
            setYPos(getYPos() - 10);
            setVelocityY(getVelocityY() - 20.0f);
        }
    }
}


class BurgerCharacter extends  ControllableCharacter{
    public Stack<Image> items = new Stack<>();
    private Image burgerTopImage;
    private Image burgerBottomImage;
    public Image patty;
    public Image lettuce;
    public Image cheese;
    public boolean gotHit = false;
    private int totalHeight = 70;



    public void init() throws SlickException {
        burgerTopImage = new Image("images/BurgerBunTop.png");
        burgerBottomImage = new Image("images/BurgerBunBottom.png");
        patty = new Image("images/Patty.png");
        lettuce = new Image("images/lettuce.png");
        cheese = new Image("images/cheese.png");
    }

    @Override
    public void render(Graphics g, ArrayList<BoundingBox> arr) {
        super.render(g, arr);
        gotHit = true;
        renderStack(g);

    }

    private void renderStack(Graphics g){
        totalHeight = 70;
        int oldHeight = getYSize();
        int yPos = 20;
        g.drawImage(burgerBottomImage, getXPos(),getYPos() + getYSize() - 20, 0, 0, 150, 20);
        for(Image item : items){
            yPos += 20;
            g.drawImage(item, getXPos(), getYPos() + getYSize() - yPos, 0, 0, 150, 20);
            totalHeight += 20;
        }
        g.drawImage(burgerTopImage, getXPos(),getYPos() + getYSize() - (yPos + 50), 0, 0, 150, 50);
        setYSize(totalHeight);
        setYPos(getYPos() - (totalHeight - oldHeight));
    }

    public void addToStack(Image item){
        items.push(item);
    }

    public void gotHit(){
        if(items.size() > 0)
            items.pop();
        gotHit = true;
    }

    public Projectile shootItem(){
        Image itemPopped = items.pop();
        return new Projectile(itemPopped, getXPos(),getYPos() + 70, lastMovement);
    }

    public int getTotalHeight() {
        return totalHeight;
    }
}

class EnemyCharacter extends ControllableCharacter {
    private Animation enemyWalkingAnimationRight;
    private Animation enemyWalkingAnimationLeft;
    private Animation dyingAnimation;
    private Animation currentAnimation;
    private int health;
    private int startingHealth;
    private int speedReducer;
    private boolean gotHit = false;

    public EnemyCharacter(int x, int y) throws SlickException {
        setXSize(100);
        setYSize(200);
        setXPos(x);
        setYPos(y);
        health = (int)(Math.random() * 3) + 1;
        startingHealth = health;
        speedReducer = (int)(Math.random() * 6) + 2;
    }

    public void init() throws SlickException {
        SpriteSheet enemyWalkingSpritesLeft = new SpriteSheet(new Image("images/enemyAnimation_left.png"), 100, 200);
        enemyWalkingAnimationLeft = new Animation(enemyWalkingSpritesLeft, 100);
        SpriteSheet enemyWalkingSpritesRight = new SpriteSheet(new Image("images/enemyAnimation_right.png"), 100, 200);
        enemyWalkingAnimationRight = new Animation(enemyWalkingSpritesRight, 100);
        SpriteSheet enemyDyingSprites = new SpriteSheet(new Image("images/enemyAnimationDying.png"), 100, 200);
        dyingAnimation = new Animation(enemyDyingSprites, 30);

    }

    public void render(Graphics g, ArrayList<BoundingBox> arr, BurgerCharacter mainChar) {
        if(mainChar.getXPos() > getXPos()){
            goRight();
        }else if(mainChar.getXPos() < getXPos()){
            goLeft();
        }
        super.render(g, arr);
        if(getBoundingBox().doesItIntesect(mainChar.getBoundingBox())){
            mainChar.gotHit();
            mainChar.setYPos(mainChar.getYPos() - 10);
            mainChar.setXPos(mainChar.getXPos() + (lastMovement)*10);
            mainChar.setVelocityX((lastMovement)*10);
            mainChar.setVelocityY(-15);
        }

        if(!gotHit) {
            if (lastMovement > 0)
                currentAnimation = enemyWalkingAnimationRight;
            else
                currentAnimation = enemyWalkingAnimationLeft;
        }else{
            currentAnimation = dyingAnimation;
        }

        //currentAnimation = dyingAnimation;

        currentAnimation.draw(getXPos(), getYPos());

        if(isOnGround){
            gotHit = false;
        }
    }

    @Override
    public void goLeft() {
        super.goLeft();
        setXPos(getXPos() + speedReducer);
    }

    @Override
    public void goRight() {
        super.goRight();
        setXPos(getXPos() - speedReducer);
    }

    public int getHealth() {
        return health;
    }

    public void gotHit(){
        health--;
        currentAnimation = dyingAnimation;
        gotHit = true;
    }

    public int getStartingHealth() {
        return startingHealth;
    }
}