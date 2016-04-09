package coms309_29.tapp_5;

import java.util.ArrayList;

import ch.boye.httpclientandroidlib.NameValuePair;
import ch.boye.httpclientandroidlib.message.BasicNameValuePair;

public class DataHolder {
    private static ArrayList<NameValuePair> params = new ArrayList<>();

    public static String get(int index) {
        return params.get(index).toString();
    }

    public static void addData(String name, String data) {

        params.add(new BasicNameValuePair(name, data));

    }

    public static void updateData(int index, String name, String data) {
        params.set(index, new BasicNameValuePair(name, data));
    }

    public static ArrayList<NameValuePair> getParams() {
        return params;
    }

    public static int getSize() {return params.size();}

}