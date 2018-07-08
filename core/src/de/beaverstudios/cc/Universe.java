package de.beaverstudios.cc;
import com.badlogic.gdx.physics.box2d.World;

import de.beaverstudios.cc.Box2D.Player;

public class Universe {

    private static Universe instance;

    public Player player;
    public World world;


    public Universe(World world) {
        this.world = world;
        player = new Player(1 ,1 ,world);
    }

    public static Universe getInstance(World world){
        if(Universe.instance == null){
            Universe.instance = new Universe(world);
        }
        return Universe.instance;
    }
}
