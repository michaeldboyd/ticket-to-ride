package com.example.sharedcode.model;

import java.io.Serializable;

/**
 * Created by hunte on 2/10/2018.
 */

public class TrainType implements Serializable {
    public static final int BOX = 0;
    public static final int PASSENGER = 1;
    public static final int TANKER = 2;
    public static final int REEFER = 3;
    public static final int FREIGHT = 4;
    public static final int HOPPER = 5;
    public static final int COAL = 6;
    public static final int CABOOSE = 7;
    public static final int LOCOMOTIVE = 8;

    public static String typeToName(int type){
        switch (type){
            case BOX : return "box";
            case PASSENGER : return "passenger";
            case TANKER : return "tanker";
            case REEFER : return "reefer";
            case FREIGHT : return "freight";
            case HOPPER : return "hopper";
            case COAL : return "coal";
            case CABOOSE : return "caboose";
            case LOCOMOTIVE : return "locomotive";
            default: return null;
        }
    }

    public static String typeToColor(int type){
        switch (type){
            case BOX : return "red";
            case PASSENGER : return "white";
            case TANKER : return "brown";
            case REEFER : return "green";
            case FREIGHT : return "blue";
            case HOPPER : return "yellow";
            case COAL : return "black";
            case CABOOSE : return "purple";
            case LOCOMOTIVE : return "wildcard";
            default: return null;
        }
    }
}