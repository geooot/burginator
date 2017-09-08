import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by 164543 on 8/30/2017.
 */
public class BoundingBox {
    private float xPos;
    private float yPos;
    private int xSize;
    private int ySize;
    private Rectangle r;
    public boolean useLastYPositionWhenColliding = false;

    public BoundingBox(float xPos, float yPos, int xSize, int ySize) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;

        r = new Rectangle((int) xPos, (int) yPos, xSize, ySize);
    }

    public BoundingBox(int xPos, int yPos, int xSize, int ySize, boolean useLastYPositionWhenColliding) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;
        this.useLastYPositionWhenColliding = useLastYPositionWhenColliding;

        r = new Rectangle(xPos, yPos, xSize, ySize);
    }

    public float getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
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

    public void setYSize(int ySize) {
        this.ySize = ySize;
    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.drawRect(xPos, yPos, xSize, ySize);
    }


    public boolean doesItIntesect(BoundingBox other){
        Rectangle other_rect = new Rectangle((int) other.xPos,(int) other.yPos,other.xSize,other.ySize);
        return r.intersects(other_rect);
    }

    public boolean doesItTouchLeft(BoundingBox other){
        Rectangle other_rect = new Rectangle((int) other.xPos,(int) other.yPos,other.xSize,other.ySize);
        float other_x1 = other.getXPos() + other.getXSize();
        float x1 = getXPos();

        if((other_x1 >= x1 && other_x1 <= x1+10) && r.intersects(other_rect)){
            return true;
        }else{
            return false;
        }
    }

    public boolean doesItTouchRight(BoundingBox other){
        Rectangle other_rect = new Rectangle((int) other.xPos,(int) other.yPos,other.xSize,other.ySize);
        float other_x1 = other.getXPos();
        float x1 = getXPos() + getXSize();

        if((other_x1 >= x1-10 && other_x1 <= x1+5) && r.intersects(other_rect)){
            return true;
        }else{
            return false;
        }
    }

    public boolean doesItTouchTop(BoundingBox other){
        Rectangle other_rect = new Rectangle((int) other.xPos,(int) other.yPos,other.xSize,other.ySize);
        float other_y1 = other.getYPos() + other.getYSize();
        float y1 = getYPos();


        if((other_y1 >= y1-10 && other_y1 <= y1+20) && r.intersects(other_rect)){
            return true;
        }else{
            return false;
        }
    }




}
