package de.beaverstudios.cc;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import de.beaverstudios.cc.Box2D.Asteroid;
import de.beaverstudios.cc.Box2D.Player;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static de.beaverstudios.cc.GameScreen.universe;

public class Universe {

    private static Universe instance;

    public static Player player;
    public World world;

    public float tLastAst;
    public float dtAst;
    public float v0;
    public float vCam;
    public Asteroid asteroid;

    public static Array<Body> asteroids;


    public Universe(World world) {
        this.world  = world;
        tLastAst    = 0.0f;
        dtAst       = 3.0f;
        asteroid    = Asteroid.getInstance(world);
        v0          = 2.f;
        vCam        = 0.f;
        asteroids   = new Array<Body>();
    }

    public static Universe getInstance(World world){
        if(Universe.instance == null){
            Universe.instance = new Universe(world);
            player      = Player.getInstance(world);
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
            asteroids.add(asteroid.makeAsteroid(15, rndY, rndRad,3, DynamicBody,false));
            advanceDtAst();
        }
    }

    public void updateVCam() {

        Vector2 vel;
        //System.out.println("vel1 " + player.b2dPlayer.getLinearVelocity());
        for(int i=0; i<asteroids.size; i++){
            vel = asteroids.get(i).getLinearVelocity();
            vel.x += vCam;
            asteroids.get(i).setLinearVelocity(vel);
        }

        vel = player.b2dPlayer.getLinearVelocity();
        vel.x += vCam;
        player.b2dPlayer.setLinearVelocity(vel);

        vCam = (float) (v0 * 1f/32f * (player.b2dPlayer.getPosition().x + 16f));

        for(int i=0; i<asteroids.size; i++){
            vel = asteroids.get(i).getLinearVelocity();
            vel.x -= vCam;
            asteroids.get(i).setLinearVelocity(vel);
        }

        vel = player.b2dPlayer.getLinearVelocity();
        vel.x -= vCam;
        player.b2dPlayer.setLinearVelocity(vel);
        //System.out.println("vel2 " + player.b2dPlayer.getLinearVelocity());

    return;
    }

    public void deleteDeadAsteroids(){
        for(int i=0; i<asteroids.size; i++){

            if (asteroids.get(i).getPosition().x < -20f){
                world.destroyBody(asteroids.get(i));
                asteroids.removeIndex(i);
            }
        }
        return;
    }

    public void advanceDtAst(){
        dtAst *= 0.99;
        return;
    }
}
