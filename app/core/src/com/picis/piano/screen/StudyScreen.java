package com.picis.piano.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.picis.piano.AudioLibrary;
import com.picis.piano.GameContext;
import com.picis.piano.ui.Staff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class StudyScreen extends ScreenAdapter {
	private final Game game;
	private final GameContext gameContext;
	private Staff staff = Staff.generate(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	private final AudioLibrary audioLibrary = new AudioLibrary();

	@Override
	public void show() {
		if (gameContext.getGameState() == GameContext.GameState.PLAY) {
			gameContext.getBlockingPool().submit(() -> {
					while (true) {
						short[] samples = gameContext.getRecorderManger().record(100);
						String res = audioLibrary.analyzePitch(samples);
					}
				}
			);
		}
	}

	@Override
	public void render(float delta) {
//		ScreenUtils.clear(1, 0, 0, 1);

		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		drawStaff();
	}

	private void drawStaff() {
		ShapeRenderer sr = new ShapeRenderer();
		sr.setColor(Color.BLACK);
		sr.setProjectionMatrix(gameContext.getCamera().combined);
//		sr.setTransformMatrix(gameContext.getCamera().projection);

		sr.begin(ShapeRenderer.ShapeType.Filled);
		sr.rectLine(staff.getLine1().getX1(), staff.getLine1().getY1(), staff.getLine1().getX2(), staff.getLine1().getY2(), 3);
		sr.rectLine(staff.getLine2().getX1(), staff.getLine2().getY1(), staff.getLine2().getX2(), staff.getLine2().getY2(), 3);
		sr.rectLine(staff.getLine3().getX1(), staff.getLine3().getY1(), staff.getLine3().getX2(), staff.getLine3().getY2(), 3);
		sr.rectLine(staff.getLine4().getX1(), staff.getLine4().getY1(), staff.getLine4().getX2(), staff.getLine4().getY2(), 3);
		sr.rectLine(staff.getLine5().getX1(), staff.getLine5().getY1(), staff.getLine5().getX2(), staff.getLine5().getY2(), 3);
		sr.end();
	}

	@Override
	public void resize(int width, int height) {
		staff = Staff.generate(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void dispose () {
	}
}
