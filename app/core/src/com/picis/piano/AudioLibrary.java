package com.picis.piano;

import jnr.ffi.LibraryLoader;

import java.io.File;

import static java.lang.System.mapLibraryName;

public class AudioLibrary {
    public static interface RustLib {
        int double_input(int i);
    }

    public static String getLibraryPath(String dylib) {
        File f = new File("/home/anton/prog/project/aud/target/debug/libpitchdetector.so");
        return f.getParent();
    }

    public static void load() {
        String dylib = "pitchdetector";
        System.setProperty("jnr.ffi.library.path", getLibraryPath(dylib));

        RustLib rlib = LibraryLoader.create(RustLib.class).load(dylib);
        int r = rlib.double_input(20);

        System.out.println("Result from rust double_input:  " + r);
    }
}