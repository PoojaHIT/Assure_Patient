package com.hit.assure.ImageCrop;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utils {
	
	Context context;
	
	public Utils(Context c){
		context = c;
	}
	
	/* Function to calculate the screen dimensions of the mobile screen */
	public float[] calculateScreenDimen(){

		DisplayMetrics metric = getContext().getResources().getDisplayMetrics();
		int width = metric.widthPixels;
		int height = metric.heightPixels;
	
	    
	    /** Array that stores the height and width values */
	    float dimen[]=new float[2];
	    
	    dimen[0]=height; dimen[1]=width;
	    
		return dimen;
	}
	
	/* Function to get the density of the screen */
	public float getScreenDPI(){
		
		
		/**
		 * .75 - ldpi
		 * 1.0 - mdpi
		 * 1.5 - hdpi
		 * 2.0 - xhdpi
		 * 3.0 - xxhdpi
		 * 4.0 - xxxhdpi 
		 **/
		
		DisplayMetrics metrics = new DisplayMetrics();
		
		float dpi=getContext().getResources().getDisplayMetrics().density;
		
		return dpi;
	}
	
	Context getContext(){
		return context;
	}

}
