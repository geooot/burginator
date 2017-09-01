import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
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

    public BoundingBox(int xPos, int yPos, int xSize, int ySize) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xSize = xSize;
        this.ySize = ySize;

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


    public void setXSize(int xSize) {
        this.xSize = xSize;
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
}
