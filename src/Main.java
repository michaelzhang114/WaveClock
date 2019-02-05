import processing.core.PApplet;
import processing.core.PFont;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Main extends PApplet {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 750;
    public static final float MAX_OF_RAND_LIST = 9;
    public static final float[] RAND_LIST = {2,4,3,5,7,5,3,2,4,6,7,8,8,6,5,3,2,4,
                                            5,6,7,8,9,1,2,3,4,0,9,7,4,1,2,9,8,3,5,3,2};
    private List<Float> normalized = new ArrayList<>();

    Random ran = new Random();


    float angleNoise, radiusNoise;
    float xNoise, yNoise;
    float aangle = -PI/2;
    float rradius;
    float _strokeCol = 254;
    int _strokeChange = -1;


    public void setup() {
        size(WIDTH, HEIGHT);
        smooth();
        frameRate(90);
        background(255);
        noFill();
        for (float n : RAND_LIST) {
            normalized.add(n / MAX_OF_RAND_LIST);
        }
        angleNoise = getRandomFromNormalized();
        radiusNoise = getRandomFromNormalized();
        xNoise = getRandomFromNormalized();
        yNoise = getRandomFromNormalized();
        noLoop();
    }
    public void draw() {
        radiusNoise += 0.005;
        rradius = (noise(radiusNoise) * 300 );
        angleNoise += 0.005;
        aangle += (noise(angleNoise) * 6 - 3);
        if (aangle > 360) { aangle -= 360; }
        if (aangle < 0) { aangle += 360; }
        xNoise += 0.01;
        yNoise += 0.01;

        float centerX = width/3 + (noise(xNoise) * -60) ;
        float centerY = height/3 + (noise(yNoise) * -60) ;


        float rad = radians(aangle);
        float x1 = centerX + (rradius * cos(rad));
        float y1 = centerY + (rradius * sin(rad));
        float opprad = rad + PI;
        float x2 = centerX + (rradius * cos(opprad));
        float y2 = centerY + (rradius * sin(opprad));


        _strokeCol += _strokeChange;
        if (_strokeCol > 254) { _strokeChange = -1 * (int) random(3); }
        if (_strokeCol < 0) { _strokeChange = (int) random(3); }
        stroke(_strokeCol, 60);
        strokeWeight(1);

        //line(x1, y1, x2, y2);
        quad(x1, y1, x2, y2, x1 * 2, y1 * 2, x2 * 2, y2 * 2);
    }

    public void mousePressed() {
        loop();  // Holding down the mouse activates looping
    }

    public void mouseReleased() {
        noLoop();  // Releasing the mouse stops looping draw()
    }

    private float getRandomFromNormalized() {
        int x = ran.nextInt(normalized.size());
        return normalized.get(x);
    }


    public static void main (String[] args) {
        PApplet.main("Main");
    }
}
