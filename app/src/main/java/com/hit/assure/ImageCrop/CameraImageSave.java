package com.hit.assure.ImageCrop;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.hit.assure.Util.MyApplication;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CameraImageSave
{
	private String fileName = "temp_photo_crop.jpg";
	ContextWrapper cw = new ContextWrapper(MyApplication.getInstance().getApplicationContext());
	File directory = cw.getDir("TmsDb", Context.MODE_PRIVATE);

	public void saveBitmapToFile(Bitmap bitmap)
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

		//String filePath = "/sdcard/temp_photo.jpg";

		File f = new File(directory + File.separator + fileName);
		
		try
		{
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
			fo.close();
		}
		catch(Exception e)
		{
			Log.e("Bitmap error", "File not found to save image");
		}
	}
	
	
	public void deleteFromFile()
	{
		
		File f = new File(directory + File.separator + fileName);
		f.delete();
		
	}
	
	public Bitmap getBitmapFromFile(int size)
	{
		Bitmap bitmap = null;
		String filePath = directory	+ File.separator + fileName;

		try
		{
			FileInputStream in = new FileInputStream(filePath);
			BufferedInputStream buf = new BufferedInputStream(in);
			bitmap = BitmapFactory.decodeStream(buf);
			bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
		}
		catch(Exception e)
		{
			Log.e("Exception", "getBitmapFromFile() -> CameraSaveImage.java");
			e.printStackTrace();
		}

		return bitmap;
	}
	
	public String getImagePath()
	{
		return directory + File.separator + fileName;
	}

}
