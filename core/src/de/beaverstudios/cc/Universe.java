package de.beaverstudios.cc;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import de.beaverstudios.cc.Box2D.Asteroid;
import de.beaverstudios.cc.Box2D.Player;
import de.beaverstudios.cc.Screens.GameScreen;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;

public class Universe {

    private static Universe instance;

    public static Player player;
    public World world;

    public static float tLastAst;
    public static float dtAst;
    public static float dtAstInit;
    public static float v0;
    public static float v0Init;
    public static float vCam;
    public static float gameTimeNorm;
    public Asteroid asteroid;

    public static Array<Body> asteroids;


    public Universe(World world) {
        this.world  = world;
        tLastAst    = 0.0f;
        dtAst       = 3.0f;
        dtAstInit   = 3.0f;
        asteroid    = Asteroid.getInstance(world);
        v0Init      = 4.0f;
        v0          = 4.0f;
        vCam        = 0.f;
        gameTimeNorm= 100f;
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
            asteroids.add(asteroid.makeAsteroid(0.6f*GameScreen.vpW, rndY, rndRad,3, DynamicBody,false));
        }
    }

    public void updateVCam() {

        //System.out.println("Vcam0 " + vCam + " x " + player.b2dPlayer.getPosition().x + " v0 " + v0);
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

        vCam = (v0 * 1f/32f * (player.b2dPlayer.getPosition().x + 16f));

        for(int i=0; i<asteroids.size; i++){
            vel = asteroids.get(i).getLinearVelocity();
            vel.x -= vCam;
            asteroids.get(i).setLinearVelocity(vel);
        }

        vel = player.b2dPlayer.getLinearVelocity();
        vel.x -= vCam;
        player.b2dPlayer.setLinearVelocity(vel);
        //System.out.println("Vcam1 " + vCam + " x " + player.b2dPlayer.getPosition().x + " v0 " + v0);

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

    public void periodicBoundaries(){

        Vector2 pos;
        for(int i=0; i<asteroids.size; i++) {
            if (asteroids.get(i).getPosition().y > GameScreen.vpH / 2.f) {
                if (asteroids.get(i).getLinearVelocity().y > 0.0){
                    pos = asteroids.get(i).getPosition().sub( new Vector2(0.0f, GameScreen.vpH));
                    asteroids.get(i).setTransform(pos, asteroids.get(i).getAngle());
                }
            }
        }
        for(int i=0; i<asteroids.size; i++) {
            if (asteroids.get(i).getPosition().y < -GameScreen.vpH / 2.f) {
                if (asteroids.get(i).getLinearVelocity().y < 0.0){
                    pos = asteroids.get(i).getPosition().add( new Vector2(0.0f, GameScreen.vpH));
                    asteroids.get(i).setTransform(pos, asteroids.get(i).getAngle());
                }
            }
        }
        if (player.b2dPlayer.getPosition().y > GameScreen.vpH / 2.f) {
            if (player.b2dPlayer.getLinearVelocity().y > 0.0){
                pos = player.b2dPlayer.getPosition().sub( new Vector2(0.0f, GameScreen.vpH));
                player.b2dPlayer.setTransform(pos, player.b2dPlayer.getAngle());
            }
        }
        if (player.b2dPlayer.getPosition().y < -GameScreen.vpH / 2.f) {
            if (player.b2dPlayer.getLinearVelocity().y < 0.0){
                pos = player.b2dPlayer.getPosition().add( new Vector2(0.0f, GameScreen.vpH));
                player.b2dPlayer.setTransform(pos, player.b2dPlayer.getAngle());
            }
        }
        return;
    }
}
