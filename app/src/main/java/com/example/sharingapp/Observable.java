package com.example.sharingapp;

import java.util.ArrayList;

public class Observable {

    private final ArrayList<Observer> observers;

    public Observable(){
        observers = new ArrayList<>();
    }

    public void notifyObservers(){
        for (Observer observer: observers) {
            observer.update();
        }
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        observers.remove(observer);
    }
}
