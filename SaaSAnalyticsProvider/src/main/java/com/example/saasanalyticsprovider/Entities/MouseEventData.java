package com.example.saasanalyticsprovider.Entities;

import java.time.Duration;
import java.util.Date;

public class MouseEventData {
    Date date;
    Duration hoverDuration;
    float x;
    float y;
    String button;     // e.g., "left", "right", "middle"
    String eventType;  // e.g., "click", "move", "doubleClick"
    String targetElement; // e.g., "div", "button", "input"
    String targetElementText;

    public String getTargetElementText() {
        return targetElementText;
    }

    public void setTargetElementText(String targetElementText) {
        this.targetElementText = targetElementText;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Duration getHoverDuration() {
        return hoverDuration;
    }

    public void setHoverDuration(Duration hoverDuration) {
        this.hoverDuration = hoverDuration;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTargetElement() {
        return targetElement;
    }

    public void setTargetElement(String targetElement) {
        this.targetElement = targetElement;
    }

    @Override
    public String toString() {
        return "MouseEventData{" +
                "date=" + date +
                ", hoverDuration=" + hoverDuration +
                ", x=" + x +
                ", y=" + y +
                ", button='" + button + '\'' +
                ", eventType='" + eventType + '\'' +
                ", targetElement='" + targetElement + '\'' +
                ", targetElementText='" + targetElementText + '\'' +
                '}';
    }
}
