package it.web.routex.utility.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private final List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(EventType eventType) {
        for (Observer obs : observers) {
            obs.update(eventType);
        }
    }
}