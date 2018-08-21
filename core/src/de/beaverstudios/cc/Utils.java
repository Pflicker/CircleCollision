package de.beaverstudios.cc;

import com.badlogic.gdx.math.Vector2;

public class Utils {

    public static float sigmoid(double x, double norm, double x0){
        return (float) (0.5*(1.0+Math.tanh(0.5*(x+x0)/norm)));
    }

    public static Vector2 worldToScreen(float x, float y){
        Vector2 screenCoord = new Vector2();
        //        1280 * 720
        int meterToPixel = 50; //50 pixels to a meter
        int offsetX = 640; //x offset in Pixels centerX
        int offsetY = 360; //y offset in Pixels centerY
        int screenX = (int)(x*meterToPixel + offsetX);
        int screenY = (int)(y*meterToPixel + offsetY) ;

        screenCoord.x = screenX;
        screenCoord.y = screenY;
        return screenCoord;
    }

}
