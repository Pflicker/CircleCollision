package de.beaverstudios.cc.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Array;

import de.beaverstudios.cc.CC;
import de.beaverstudios.cc.ui.UiArrow;

public class ArrowTable extends WidgetGroup {

    private UiArrow third;
    private UiArrow second;
    private UiArrow first;

    private Array<Integer> array;
    private int x;

    private final static int xL = 0, xR = CC.WIDTH-100;
    private final static int y1=400,y2=500,y3=600;

    public ArrowTable(boolean clockwise, Array<Integer> array) {

        this.array = array;

        if (clockwise){
            x = xR;
        } else {
            x = xL;
        }

        third = new UiArrow(x,y3,-array.get(2), 0.8f);
        second= new UiArrow(x,y2,-array.get(1), 0.8f);
        first = new UiArrow(x,y1,-array.get(0), 0.8f);

        addActor(third);
        addActor(second);
        addActor(first);

        debug();
    }

    public void makeMove(){
        first.addAction(Actions.sequence(Actions.fadeOut(1),Actions.removeActor()));
        second.addAction(Actions.moveBy(0,-100,1));
        third.addAction(Actions.moveBy(0,-100,1));


        first = second;
        second = third;
        third = new UiArrow(x,y3,-array.get(2),0.8f);
        addActor(third);
    }
}
