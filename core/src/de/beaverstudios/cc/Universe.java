package de.beaverstudios.cc;
import com.badlogic.gdx.physics.box2d.World;

import de.beaverstudios.cc.Box2D.Asteroid;
import de.beaverstudios.cc.Box2D.Player;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;

public class Universe {

    private static Universe instance;

    public Player player;
    public World world;

    public float tLastAst;
    public float dtAst;
    public Asteroid asteroid;



    public Universe(World world) {
        this.world  = world;
        player      = new Player(1 ,1 ,world);
        tLastAst    = 0.0f;
        dtAst       = 3.0f;
        asteroid    = Asteroid.getInstance(world);
    }

    public static Universe getInstance(World world){
        if(Universe.instance == null){
            Universe.instance = new Universe(world);
        }
        return Universe.instance;
    }

    public void CreateAsteroid(){
        float rndY;
        float rndRad;
        if (GameScreen.time-tLastAst > dtAst){
            System.out.println("new Asteroid");
            tLastAst = GameScreen.time;
            rndY = -10.0f + 20*(float) Math.random();
            rndRad = 1 + 3*(float) Math.random();
            asteroid.makeAsteroid(15, rndY, rndRad,3, DynamicBody,false);
        }
    }

}
