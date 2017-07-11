package com.develogical.camera;

public class Camera implements WriteCompleteListener {

    private Sensor sensor;
    private boolean isOn;
    private MemoryCard memoryCard;
    private boolean writingData;

    public Camera(Sensor _sensor, MemoryCard _card) {
        this.sensor = _sensor;
        this.isOn = false;
        this.memoryCard = _card;
        this.writingData = false;
    }

    public void pressShutter() {
        if (this.isOn) {
            this.memoryCard.write(sensor.readData(), this);
            this.writingData = true;

        }
    }

    public void powerOn() {
        if (!this.isOn) {
            this.sensor.powerUp();
            this.isOn = true;
        }
    }

    public void powerOff() {
        if (this.isOn && !this.writingData) {
            this.sensor.powerDown();
            this.isOn = false;
        }
    }

    @Override
    public void writeComplete() {
        this.writingData = false;
        this.sensor.powerDown();
    }
}

