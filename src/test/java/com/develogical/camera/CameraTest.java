package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CameraTest {

    Sensor mockSensor = mock(Sensor.class);


    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {

        Camera c = new Camera(mockSensor);
         c.powerOn();

         verify(mockSensor).powerUp();

    }
}
