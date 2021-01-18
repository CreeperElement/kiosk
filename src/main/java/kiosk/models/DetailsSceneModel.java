package kiosk.models;

import graphics.Color;
import kiosk.scenes.DetailsScene;
import kiosk.scenes.Scene;

public class DetailsSceneModel implements SceneModel {

    public String id;
    public String title;
    public String body;
    public ButtonModel button;

    /**
     * Stores all relevant information needed for the Details Scene.
     * Which is composed of an ID, main title, body of text, and a button
     * at the bottom.
     */
    public DetailsSceneModel() {
        this.id = IdGenerator.getInstance().getNextId();
        this.title = "";
        this.body = "";
        this.button = new ButtonModel();
    }

    @Override
    public Scene createScene() {
        return new DetailsScene(this);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public SceneModel deepCopy() {
        var copy = new DetailsSceneModel();
        copy.id = this.id;
        copy.title = title;
        copy.button = this.button.deepCopy();
        copy.body = this.body;

        return copy;
    }

    @Override
    public String[] getTargets() {
        return new String[] {button.target};
    }

    @Override
    public String toString() {
        return "Details Scene";
    }
}
