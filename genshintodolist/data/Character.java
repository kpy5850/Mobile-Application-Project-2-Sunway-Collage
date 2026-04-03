package com.example.genshintodolist.data;

public class Character {
    int iconResId;
    int wallpaperRawResId;
    String name;

    public Character(int iconResId, int wallpaperRawResId, String name){
        this.iconResId = iconResId;
        this.wallpaperRawResId = wallpaperRawResId;
        this.name = name;
    }

    public int getIconResId(){
        return iconResId;
    }

    public int getWallpaperRawResId(){
        return wallpaperRawResId;
    }

    public String getName(){ return name; }
}
