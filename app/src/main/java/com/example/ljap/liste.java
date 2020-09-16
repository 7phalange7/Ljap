package com.example.ljap;

//list element

public class liste {

    private String meng;
    private String mjap;
    private int mimgres = -1;
    private int maudres=-1;

    public liste(String eng, String jap, int imres, int audres) {
        meng = eng;
        mjap = jap;
        mimgres = imres;
        maudres = audres;
    }

    public liste(String eng, String jap, int audres) {
        meng = eng;
        mjap = jap;
        maudres = audres;
    }

    public liste(String eng, String jap) {
        meng = eng;
        mjap = jap;

    }

    public String getMeng() {
        return meng;
    }

    public String getMjap() {
        return mjap;
    }

    public int getMimgres() {
        return mimgres;
    }

    public int getMaudres(){
        return maudres;
    }

    public boolean hasimg(){
        return mimgres != -1;
    }

    public boolean hasaud(){
        return maudres != -1;
    }



}
