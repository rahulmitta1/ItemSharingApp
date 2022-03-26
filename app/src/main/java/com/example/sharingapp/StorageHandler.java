package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StorageHandler<T> {
    String FILENAME;
    private final Type listType;

    public StorageHandler(String FILENAME, Type listType) {
        this.FILENAME = FILENAME;
        this.listType = listType;
    }

    public ArrayList<T> load(Context context) {
        ArrayList<T> list;
        try{
            FileInputStream stream = context.openFileInput(FILENAME);
            InputStreamReader reader = new InputStreamReader(stream);
            Gson gson = new Gson();

            list = gson.fromJson(reader, listType);
            stream.close();
        } catch (IOException | JsonIOException e){
            list = new ArrayList<>();
        }

        return list;
    }

    public boolean save(Context context, ArrayList<T> list) {
        try {
            FileOutputStream stream = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            Gson gson = new Gson();
            gson.toJson(list, listType, writer);
            writer.flush();
            stream.close();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
