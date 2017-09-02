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
    private int xPos;
    private int yPos;
    private int xSize;
    private int ySize;
    private Rectangle r;
    public boolean useLastYPositionWhenColliding = false;

    public BoundingBox(int xPos, int yPos, int xSize, int ySize) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;

        r = new Rectangle(xPos, yPos, xSize, ySize);
    }

    public BoundingBox(int xPos, int yPos, int xSize, int ySize, boolean useLastYPositionWhenColliding) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;
        this.useLastYPositionWhenColliding = useLastYPositionWhenColliding;

        r = new Rectangle(xPos, yPos, xSize, ySize);
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
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

    public int doesItCollide(int x, int y, int width, int height){
        boolean xCollision = r.intersects(new Rectangle(x,y,width,1));
        boolean yCollision = r.intersects(new Rectangle(x,y,1,height));
        if(!xCollision && !yCollision){
            return 0;
        }else if(xCollision && !yCollision){
            return 1;
        }else if(!xCollision){
            return 2;
        }else{
            return 3;
        }
    }



    public int doesItCollide(BoundingBox other){
        boolean xCollision = r.intersects(new Rectangle(other.xPos,other.yPos,other.xSize,1));
        boolean yCollision = r.intersects(new Rectangle(other.xPos,other.yPos,1, other.ySize));
        if(!xCollision && !yCollision){
            return 0;
        }else if(xCollision && !yCollision){
            return 1;
        }else if(!xCollision){
            return 2;
        }else{
            return 3;
        }
    }


    public boolean doesItTouchLeft(BoundingBox other){
        Rectangle other_rect = new Rectangle(other.xPos,other.yPos,other.xSize,other.ySize);
        float other_x1 = other.getXPos() + other.getXSize();
        float x1 = getXPos();

        if((other_x1 >= x1-10 && other_x1 <= x1+50) && r.intersects(other_rect)){
            return true;
        }else{
            return false;
        }
    }

    public boolean doesItTouchRight(BoundingBox other){
        Rectangle other_rect = new Rectangle(other.xPos,other.yPos,other.xSize,other.ySize);
        float other_x1 = other.getXPos();
        float x1 = getXPos() + getXSize();

        if((other_x1 >= x1-50 && other_x1 <= x1+10) && r.intersects(other_rect)){
            return true;
        }else{
            return false;
        }
    }

    public boolean doesItTouchTop(BoundingBox other){
        Rectangle other_rect = new Rectangle(other.xPos,other.yPos,other.xSize,other.ySize);
        float other_y1 = other.getYPos() + other.getYSize();
        float y1 = getYPos();


        if((other_y1 >= y1-10 && other_y1 <= y1+20) && r.intersects(other_rect)){
            return true;
        }else{
            return false;
        }
    }




}
