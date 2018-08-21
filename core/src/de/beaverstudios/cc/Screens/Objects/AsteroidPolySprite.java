package de.beaverstudios.cc.Screens.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static de.beaverstudios.cc.Utils.worldToScreen;

public class AsteroidPolySprite{

    public PolygonSprite sprite;
    private Fixture fixture;
    private Body body;

    public AsteroidPolySprite(Fixture fixture, Body body) {
        this.fixture = fixture;
        this.body=body;

        createPolygonSprite();
    }

    void createPolygonSprite(){
        Texture asteroid = new Texture(Gdx.files.internal("asteroiden.png"));
        TextureRegion textureRegion = new TextureRegion(asteroid,0,0,asteroid.getWidth(),asteroid.getHeight());
        /*PolygonRegion region = new PolygonRegion(new TextureRegion(asteroid),
                new float[]{
                        0, 10,
                        85, 10,
                        85, 80,
                        0, 120
                }, new short[]{
                0, 1, 2,
                0, 2, 3
        });*/

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

        sprite = new PolygonSprite(region);
    }

    public void update(){
        Vector2 pos = new Vector2(worldToScreen(body.getPosition().x,body.getPosition().y));
        sprite.setPosition(pos.x,pos.y);
        sprite.setRotation(body.getAngle());
    }
}
