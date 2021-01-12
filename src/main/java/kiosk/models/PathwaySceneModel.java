package kiosk.models;

import kiosk.scenes.Scene;
import kiosk.scenes.PathwayScene;

public class PathwaySceneModel implements SceneModel {

    public String id;
    public float xpos;
    public float ypos;
    public float size;
    public float padding;
    public String centerText;
    public ButtonModel[] answers;

    /**
     * Creates a new spoke graph Scene Model.
     * @param id The unique id of the scene.
     * @param x The upper left-hand x position of the Scene.
     * @param y The upper left-hand y position of the Scene.
     * @param size The scene is drawn in a square, this is the size of all sides.
     * @param padding The extra spacing between circles.
     * @param centerText The text in the center circle.
     * @param answers The list of text appearing on the outer circles.
     */
    public PathwaySceneModel(String id, float x, float y, float size, float padding,
                             String centerText, ButtonModel[] answers) {
        this.id = id;
        this.xpos = x;
        this.ypos = y;
        this.size = size;
        this.padding = padding;
        this.centerText = centerText;
        this.answers = new ButtonModel[]{};
    }

    @Override
    public Scene createScene() {
        return new PathwayScene(this);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }


    @Override
    public SceneModel deepCopy() {
        return new PathwaySceneModel(this.id, this.xpos, this.ypos, this.size,
                this.padding, this.centerText, this.answers);
    }

    @Override
    public String[] getTargets() {
        return new String[0];
    }
}
