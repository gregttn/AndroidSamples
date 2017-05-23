package com.gregttn.usecameradirectlysample;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private final Camera camera;
    private final SurfaceHolder holder;

    public CameraPreview(Context context, Camera camera) {
        super(context);

        this.camera = camera;
        this.holder = getHolder();

        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            return;
        }

        stopPreview();
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void startPreview() {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Cannot set the camera", e);
        }
    }

    private void stopPreview() {
        try {
            camera.stopPreview();
        } catch(Exception e) {
            Log.e(getClass().getSimpleName(), "Cannot stop camera", e);
        }
    }

    public void restartPreview() {
        stopPreview();
        startPreview();
    }

}
