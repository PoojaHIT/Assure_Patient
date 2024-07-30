package com.hit.assure.ImageCrop;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.assure.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class ZoomCroppingActivityold extends Activity {

    TouchImageView cropView;
    LinearLayout btnDoneCropping;
    LinearLayout btnRotate;

    int windowwidth;

    float dpi = 0.0f;


    int imageWidth, imageHeight;

    float[] screenDimen;
    float deviceWidth, deviceHeight;

    private Bitmap bitmap;

    TextView textViewCropInstructions;

    Uri uri = null;

    CameraImageSave cameraImageSave;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_cropping);

        initViews();

        windowwidth = getWindowManager().getDefaultDisplay().getWidth();

        /** getExtras() */
        final String comingfrom = getIntent().getExtras().getString("comingfrom");

        if (comingfrom.equals("gallery")) {
            String imagePath = getIntent().getExtras().getString("imagepath");
            /** Convert the image URI to bitmap */
            if (imagePath != null) {
                try {
                    Uri uri = Uri.fromFile(new File(imagePath));
                    bitmap = getThumbnail(uri, 720);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //bitmap = BitmapFactory.decodeFile(imagePath);
            textViewCropInstructions.setText("Pinch to zoom. Resize. Crop.");
        } else if (comingfrom.equals("camera")) {
            int orientation = getIntent().getIntExtra("orientation", 0);
            try {
                String filePath = cameraImageSave.getImagePath();
//                AddPrescriptionActivity.ImageUri1 = cameraImageSave.getImagePath();
                FileInputStream in = new FileInputStream(filePath);
                BufferedInputStream buf = new BufferedInputStream(in);
                bitmap = BitmapFactory.decodeStream(buf);

                if (filePath != null) {
                    Uri uri = Uri.fromFile(new File(filePath));

                    if (bitmap.getWidth() >= 2000) {
                        bitmap = getThumbnail(uri, (windowwidth * 2));
                    }
                    bitmap = changeImageOrientation(orientation, bitmap);
                }
                //getRotatedImage(filePath, bitmap);
                textViewCropInstructions.setText("Pinch to zoom. Resize. Crop.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Utils u = new Utils(ZoomCroppingActivityold.this);
        dpi = u.getScreenDPI();

        screenDimen = u.calculateScreenDimen();
        deviceWidth = screenDimen[1];
        deviceHeight = screenDimen[0];
        float zoom = 0.0f;
        if (bitmap != null) {
            /** Set the bitmap for cropping view */
            cropView.setImageBitmap(bitmap);
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();

            if (height > width) {
                zoom = (float) height / width;
            } else
                zoom = (float) width / height;
        }


        //touch.CustomZoom();
        cropView.setMinZoom(zoom);

        cropView.getLayoutParams().height = (int) deviceWidth;
        cropView.getLayoutParams().width = (int) deviceWidth;

        /** Crop Accept listener */
        btnDoneCropping.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    if (bitmap != null) {
                        bitmap = getBitmapFromView(cropView);

                        Intent i = new Intent();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        cameraImageSave.saveBitmapToFile(bitmap);
                        setResult(RESULT_OK, i);
                        bitmap = null;
                        finish();
//						}
                    } else {
                        Toast.makeText(ZoomCroppingActivityold.this, "The image wasnt loaded properly", Toast.LENGTH_LONG).show();
                        Intent i = new Intent();
                        setResult(RESULT_CANCELED, i);
                        finish();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        /** Image rotate listener */
        btnRotate.setOnClickListener(new OnClickListener() {


            public void onClick(View arg0) {
                bitmap = ImageEffects.rotate(bitmap, 90);
                cropView.setImageBitmap(bitmap);
            }
        });
    }

    void initViews() {
        cropView = (TouchImageView) findViewById(R.id.img);
        btnDoneCropping = (LinearLayout) findViewById(R.id.imageViewAccept);
        btnRotate = (LinearLayout) findViewById(R.id.imageViewRotate);
        textViewCropInstructions = (TextView) findViewById(R.id.textViewCropInstructions);

        cameraImageSave = new CameraImageSave();
    }


    Bitmap changeImageOrientation(int orientation, Bitmap bitmap) {

        if (orientation == 0) {
            bitmap = ImageEffects.rotate(bitmap, 90);
        } else if (orientation == 180) {
            bitmap = ImageEffects.rotate(bitmap, -90);
        } else if (orientation == 90) {
            bitmap = ImageEffects.rotate(bitmap, 180);
        }

        return bitmap;
    }


    public Bitmap getThumbnail(Uri uri, int THUMBNAIL) throws FileNotFoundException, IOException {
        InputStream input = ZoomCroppingActivityold.this.getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > THUMBNAIL) ? (originalSize / THUMBNAIL) : 1.0;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true;
        bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        input = ZoomCroppingActivityold.this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);


        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 2;
        else return k;
    }


    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = null;
        try {
            //Define a bitmap with the same size as the view
            returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            //Bind a canvas to it
            Canvas canvas = new Canvas(returnedBitmap);
            //Get the view's background
            Drawable bgDrawable = view.getBackground();
            if (bgDrawable != null)
                //has background drawable, then draw it on the canvas
                bgDrawable.draw(canvas);
            else
                //does not have background drawable, then draw white background on the canvas
                canvas.drawColor(Color.WHITE);
            // draw the view on the canvas
            view.draw(canvas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //return the bitmap
        return returnedBitmap;
    }

}
