package de.beaverstudios.cc.Box2D;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class ListenerClass implements ContactListener {

    World b2dWorld;

    public ListenerClass(World b2dWorld) {
        this.b2dWorld = b2dWorld;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void beginContact(Contact contact) {

        Body ObjA;
        Body ObjB;
        Player player;

        ObjA = contact.getFixtureA().getBody();
        ObjB = contact.getFixtureB().getBody();
        player = Player.getInstance(b2dWorld);

        if (ObjA == player.b2dPlayer || ObjB == player.b2dPlayer){
            System.out.println("GAME OVER");
        }
    }
};
