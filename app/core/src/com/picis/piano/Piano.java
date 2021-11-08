package com.picis.piano;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.picis.piano.audio.RecorderManger;
import com.picis.piano.screen.StudyScreen;
import com.picis.piano.sheet.GuidoExtracter;

import java.util.concurrent.Executors;

public class Piano extends Game {
    private Viewport viewport;
    private OrthographicCamera camera;

    @Override
    public void create () {
        GuidoExtracter guidoExtracter = new GuidoExtracter();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 640, 480);
        viewport = new FitViewport(640, 480, camera);
        GameContext gameContext = GameContext.builder()
            .gameState(GameContext.GameState.PLAY)
            .recorderManger(new RecorderManger(44100, true))
            .viewport(viewport)
            .camera(camera)
            .guidoExtracter(guidoExtracter)
            .blockingPool(Executors.newFixedThreadPool(4, Executors.defaultThreadFactory()))
            .build();

        setScreen(new StudyScreen(this, gameContext));
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    @Override
    public void render() {
        super.render();
    }
}
