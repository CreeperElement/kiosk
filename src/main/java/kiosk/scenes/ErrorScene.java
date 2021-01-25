package kiosk.scenes;

import kiosk.Graphics;
import kiosk.Kiosk;
import kiosk.SceneGraph;
import kiosk.models.ErrorSceneModel;
import processing.core.PConstants;

public class ErrorScene implements Scene {

    private final ErrorSceneModel model;

    public ErrorScene(ErrorSceneModel model) {
        this.model = model;
    }

    @Override
    public void init(Kiosk sketch) {

    }

    @Override
    public void update(float dt, SceneGraph sceneGraph) {

    }

    @Override
    public void draw(Kiosk sketch) {
        Graphics.useGothic(sketch, 24);
        sketch.background(255, 0, 0);
        sketch.fill(255);

        sketch.textAlign(PConstants.CENTER, PConstants.CENTER);
        sketch.rectMode(PConstants.CENTER);
        sketch.text(this.model.errorMsg, sketch.width / 2f, sketch.height / 2f, sketch.width, sketch.height);
    }
}
