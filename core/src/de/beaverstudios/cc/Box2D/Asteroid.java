package de.beaverstudios.cc.Box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import net.dermetfan.gdx.graphics.g2d.Box2DPolygonSprite;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import de.beaverstudios.cc.Universe;

public class Asteroid {

    public static final int STEEL = 0;
    public static final int WOOD = 1;
    public static final int RUBBER = 2;
    public static final int STONE = 3;



    private static Universe universe;
    private World world;
    private static Asteroid instance;
    private Asteroid(World world){
        this.world = world;
    }

    public static Asteroid getInstance(World world){
        if(Asteroid.instance == null){
            Asteroid.instance = new Asteroid(world);
            universe = Universe.getInstance(world);
        }
        return Asteroid.instance;
    }

    public Body makeAsteroid(float posx, float posy, float radius, int material, BodyDef.BodyType bodyType, boolean fixedRotation){
        // create a definition

        float vX;
        float vY;
        float rnd;
        Fixture fixture;

        rnd = (float) Math.random();
        vX = (float) -Math.sin(rnd*Math.PI)*universe.v0*0.5f;
        vY = (float)  Math.cos(rnd*Math.PI)*universe.v0*0.5f;
        vX -= universe.vCam;

        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = posx;
        boxBodyDef.position.y = posy;
        boxBodyDef.fixedRotation = fixedRotation;

        //create the body to attach said definition
        Body boxBody = world.createBody(boxBodyDef);
        //CircleShape circleShape = new CircleShape();
        //circleShape.setRadius(radius /2);


        boxBody.createFixture(makeFixture(material,MakePoly()));
        fixture = boxBody.getFixtureList().get(0);
        boxBody.setLinearVelocity(vX,vY);
        Box2DPolygonSprite box2DSprite = new Box2DPolygonSprite(createPolygonSprite(fixture, boxBody));

        boxBody.setUserData(box2DSprite); // will draw on whole body
        boxBody.getFixtureList().get(0).setUserData(box2DSprite);

        box2DSprite.setUseOrigin(true);
        box2DSprite.
        //boxBody.setAngularVelocity((float)(1.0 + Math.random()));

        //circleShape.dispose();
        System.out.println("Vast: " + vX +  " "+ vY);
        return boxBody;
    }

    PolygonSprite createPolygonSprite(Fixture fixture, Body body){
        Texture asteroid = new Texture(Gdx.files.internal("asteroiden.png"));
        TextureRegion textureRegion = new TextureRegion(asteroid,0,0,asteroid.getWidth(),asteroid.getHeight());
        PolygonShape shape = (PolygonShape) fixture.getShape();
        int vertexCount = shape.getVertexCount();
        float[] vertices = new float[vertexCount * 2];
        for (int k = 0; k < vertexCount; k++) {
            Vector2 mTmp = new Vector2();
            shape.getVertex(k, mTmp);
            mTmp.rotate(body.getAngle()* MathUtils.radiansToDegrees);
            mTmp.add(body.getPosition());
            vertices[k * 2] = mTmp.x * 60; //PPM
            vertices[k * 2 + 1] = mTmp.y * 60; //PPM
        }
        short triangles[] = new EarClippingTriangulator()
                .computeTriangles(vertices)
                .toArray();
        PolygonRegion region = new PolygonRegion(textureRegion, vertices, triangles);

        PolygonSprite sprite = new PolygonSprite(region);
        return sprite;
    }

    static private Shape MakePoly(){

        int Npoly = 8;
        Vector2[] vertices = new Vector2[8];
        float rnd1 = (float) (5*Math.random());


        for (int i = 0; i < Npoly; i++) {
            float rnd2 = (float) Math.random()+1.0f;
            vertices[i] = new Vector2((float) (rnd1*rnd2*Math.sin(i/8.)) , (float) (rnd1*rnd2*Math.cos(i/8.)));
        }
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);

        return shape;
    }

    static public FixtureDef makeFixture(int material, Shape shape){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        switch(material){
            case 0:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.3f;
                fixtureDef.restitution = 0.1f;
                break;
            case 1:
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0.7f;
                fixtureDef.restitution = 0.3f;
                break;
            case 2:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 1f;
                break;
            case 3:
                fixtureDef.density = 1f;
                fixtureDef.friction = 0.9f;
                fixtureDef.restitution = 0.01f;
            default:
                fixtureDef.density = 7f;
                fixtureDef.friction = 0.5f;
                fixtureDef.restitution = 0.3f;
        }
        return fixtureDef;
    }
}
