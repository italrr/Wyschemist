package com.nite.wyschemist;

public class Vec2 {
    public float x, y;

    public Vec2(){
        x = 0;
        y = 0;
    }

    public Vec2(float c){
        x = c;
        y = c;
    }

    public Vec2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Vec2(Vec2 v){
        x = v.x;
        y = v.y;
    }

    public void set(float c){
        x = c;
        y = c;
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void set(Vec2 v){
        x = v.x;
        y = v.y;
    }

    public Vec2 Sum(Vec2 v){
        return new Vec2(this.x + v.x, this.y + v.y);
    }

    public Vec2 Rest(Vec2 v){
        return new Vec2(this.x - v.x, this.y - v.y);
    }

    public Vec2 Multiply(Vec2 v){
        return new Vec2(this.x * v.x, this.y * v.y);
    }

    public Vec2 Divide(Vec2 v){
        return new Vec2(this.x / v.x, this.y / v.y);
    }

    public void lerp(Vec2 v, float m){
        x = Tools.lerp(x, v.x, m);
        y = Tools.lerp(y, v.y, m);
    }

    public boolean equals(Vec2 v){
        return x == v.x && y == v.y;
    }

    @Override
    public String toString(){
        return "{"+String.valueOf(x)+", "+String.valueOf(y)+"}";
    }

}

