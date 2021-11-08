package com.picis.piano;

import jnr.ffi.LibraryLoader;

import java.io.File;

import static java.lang.System.mapLibraryName;

public class AudioLibrary {
    private final RustLib rustLib;

    public AudioLibrary() {
        String dylib = "pitchdetectorrr";
        String path = "/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib:/home/anton/prog/project/aud/target/debug:/lib/x86_64-linux-gnu:/usr/lib/x86_64-linux-gnu:/lib/x86_64-linux-gnu";
        System.setProperty("java.library.path", path);
        System.setProperty("LD_LIBRARY_PATH", path);
        System.setProperty("jnr.ffi.library.path", path);
        System.out.println(System.getProperty("LD_LIBRARY_PATH"));
        System.out.println(System.getProperty("java.library.path"));
        System.out.println(System.getProperty("jnr.ffi.library.path"));
        rustLib = LibraryLoader.create(RustLib.class).load(dylib);
    }

    public interface RustLib {
        int double_input(int i);
        String detect_pitch(short[] samples);
    }

    public String getLibraryPath() {
        File f = new File("/home/anton/prog/project/aud/target/debug/libpitchdetector.so");
        return f.getParent();
    }

    public String analyzePitch(short[] samples) {
        int r = rustLib.double_input(20);
        System.out.println("Result from rust double_input:  " + r);

        String res = rustLib.detect_pitch(samples);
        System.out.println("Result from rust pitch:  " + res);
        return res;
    }
}