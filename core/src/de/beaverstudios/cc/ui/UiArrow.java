package de.beaverstudios.cc.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class UiArrow extends Image {

    public UiArrow(int x,int y, int rotation, float scale) {
        super(UI.arrow);
        setScale(0.5f);
        setPosition(x,y);
        setOrigin(UI.arrow.getWidth()/2,UI.arrow.getHeight()/2);
        addAction(Actions.parallel(Actions.rotateBy(rotation,1)));

    }
}
