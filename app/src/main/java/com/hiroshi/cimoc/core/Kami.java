package com.hiroshi.cimoc.core;

import com.hiroshi.cimoc.core.base.Manga;

/**
 * Created by Hiroshi on 2016/7/3.
 */
public class Kami {

    public static final int SOURCE_IKANMAN = 1;

    public static String getSourceById(int id) {
        switch (id) {
            case SOURCE_IKANMAN:
                return "看漫画";
            default:
                return "";
        }
    }

    public static String getHostById(int id) {
        switch (id) {
            case SOURCE_IKANMAN:
                return "http://m.ikanman.com";
            default:
                return "";
        }
    }

    private static IKanman mIKanman;

    public static Manga getMangaById(int id) {
        switch (id) {
            case SOURCE_IKANMAN:
                if (mIKanman == null) {
                    mIKanman = new IKanman();
                }
                return mIKanman;
            default:
                return null;
        }
    }

}
