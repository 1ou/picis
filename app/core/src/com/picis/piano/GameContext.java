package com.picis.piano;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.picis.piano.audio.RecorderManger;
import com.picis.piano.sheet.GuidoExtracter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ExecutorService;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class GameContext {
    public enum GameState {
        MENU,
        PLAY,
        PAUSE;
    }

    private final GuidoExtracter guidoExtracter;
    private GameState gameState;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final RecorderManger recorderManger;
    private final ExecutorService blockingPool;
}
