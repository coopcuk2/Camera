package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

public class CameraTest {
    Sensor mockSensor = mock(Sensor.class);
    MemoryCard mockCard = mock(MemoryCard.class);

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        Camera c = new Camera(mockSensor, mockCard);
        c.powerOn();

        verify(mockSensor).powerUp();

    }

    @Test
    public void switchingTheCameraOnWhenOnDoesNothing() {
        Camera c = new Camera(mockSensor, mockCard);
        c.powerOn();

        verify(mockSensor).powerUp();
        c.powerOn();
        verifyNoMoreInteractions(mockSensor);

    }

    @Test
    public void switchingTheCameraOffPowersDownTheSensor() {
        Camera camera = new Camera(mockSensor, mockCard);
        camera.powerOn();
        camera.powerOff();
        verify(mockSensor).powerDown();
    }

    @Test
    public void pressingTheShutterWhenPowerIsOffDoesNothing() {
        Camera camera = new Camera(mockSensor, mockCard);
        camera.powerOff();
        camera.pressShutter();

        verifyZeroInteractions(mockSensor);
    }

    @Test
    public void pressingTheShutterWithThePowerOnCopiesToMemoryCard() {
        Camera camera = new Camera(mockSensor, mockCard);
        camera.powerOn();

        byte[] testBytes = "test".getBytes();

        when(mockSensor.readData()).thenReturn(testBytes);

        camera.pressShutter();
        verify(mockCard).write(eq(testBytes), any());
    }

    @Test
    public void switchingOffCameraDuringDataWriteDoesNothing() {
        Camera camera = new Camera(mockSensor, mockCard);
        camera.powerOn();

        byte[] testBytes = "test".getBytes();

        when(mockSensor.readData()).thenReturn(testBytes);

        camera.pressShutter();
        camera.powerOff();

        verify(mockSensor, never()).powerDown();
    }

    @Test
    public void cameraSwitchesOffSensorWhenDataFinishesWriting() {
        Camera camera = new Camera(mockSensor, mockCard);

        camera.powerOn();
        camera.pressShutter();

        ArgumentCaptor<WriteCompleteListener> argumentCaptor = ArgumentCaptor.forClass(WriteCompleteListener.class);
        verify(mockCard).write(any(), argumentCaptor.capture());
        argumentCaptor.getValue().writeComplete();

        verify(mockSensor).powerDown();
    }
}
