package com.example.sharingapp;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StorageHandler<T> {
    String FILENAME;

    public StorageHandler(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    public ArrayList<T> load(Context context) {
        ArrayList<T> list;
        try{
            FileInputStream stream = context.openFileInput(FILENAME);
            InputStreamReader reader = new InputStreamReader(stream);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<T>>() {}.getType();
            list = gson.fromJson(reader, listType);
            stream.close();
        }catch (FileNotFoundException e){
            list = new ArrayList<T>();
        }catch (IOException e){
            list = new ArrayList<T>();
        }
        return list;
    }

    public boolean save(Context context, ArrayList<T> list) {
        try {
            FileOutputStream stream = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            Gson gson = new Gson();
            gson.toJson(list, writer);
            writer.flush();
            stream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
