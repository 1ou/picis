package com.picis.piano.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.picis.piano.Piano;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplication config = new LwjglApplication(new Piano());
		new LwjglApplication(new Piano());
	}
}
