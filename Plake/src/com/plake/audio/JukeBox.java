package com.plake.audio;

import java.util.*;
import javax.sound.sampled.*;

public class JukeBox
{
    private static HashMap<String, Clip> clips;
    private static int gap;
    private static boolean mute;
    
    static {
        JukeBox.mute = false;
    }
    
    public static void init() {
        JukeBox.clips = new HashMap<String, Clip>();
        JukeBox.gap = 0;
    }
    
    public static void load(final String s, final String n) {
        try {
        	if (JukeBox.clips.get(n) != null) {
                return;
            }
            final AudioInputStream ais = AudioSystem.getAudioInputStream(JukeBox.class.getResourceAsStream(s));
            final AudioFormat baseFormat = ais.getFormat();
            final AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            final AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            final Clip clip = AudioSystem.getClip();
            clip.open(dais);
            JukeBox.clips.put(n, clip);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void play(final String s) {
        play(s, JukeBox.gap);
    }
    
    public static void play(final String s, final int i) {
        if (JukeBox.mute) {
            return;
        }
        final Clip c = JukeBox.clips.get(s);
        if (c == null) {
            return;
        }
        if (c.isRunning()) {
            c.stop();
        }
        c.setFramePosition(i);
        while (!c.isRunning()) {
            c.start();
        }
    }
    
    public static void stop(final String s) {
        if (JukeBox.clips.get(s) == null) {
            return;
        }
        if (JukeBox.clips.get(s).isRunning()) {
            JukeBox.clips.get(s).stop();
        }
    }
    
    public static void resume(final String s) {
        if (JukeBox.mute) {
            return;
        }
        if (JukeBox.clips.get(s).isRunning()) {
            return;
        }
        JukeBox.clips.get(s).start();
    }
    
    public static void loop(final String s) {
        loop(s, JukeBox.gap, JukeBox.gap, JukeBox.clips.get(s).getFrameLength() - 1);
    }
    
    public static void loop(final String s, final int frame) {
        loop(s, frame, JukeBox.gap, JukeBox.clips.get(s).getFrameLength() - 1);
    }
    
    public static void loop(final String s, final int start, final int end) {
        loop(s, JukeBox.gap, start, end);
    }
    
    public static void loop(final String s, final int frame, final int start, final int end) {
        stop(s);
        if (JukeBox.mute) {
            return;
        }
        JukeBox.clips.get(s).setLoopPoints(start, end);
        JukeBox.clips.get(s).setFramePosition(frame);
        JukeBox.clips.get(s).loop(-1);
    }
    
    public static void setPosition(final String s, final int frame) {
        JukeBox.clips.get(s).setFramePosition(frame);
    }
    
    public static int getFrames(final String s) {
        return JukeBox.clips.get(s).getFrameLength();
    }
    
    public static int getPosition(final String s) {
        return JukeBox.clips.get(s).getFramePosition();
    }
    
    public static void close(final String s) {
        stop(s);
        JukeBox.clips.get(s).close();
    }
}
