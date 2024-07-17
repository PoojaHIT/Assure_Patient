package com.hit.assure.Activity.MyJourney;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hit.assure.ImageCrop.CameraImageSave;
import com.hit.assure.ImageCrop.ZoomCroppingActivity;
import com.hit.assure.Model.Note.NoteResponse;
import com.hit.assure.Model.PatientBookingDetails.PatientBookingDetailsResponse;
import com.hit.assure.Model.Prescriptionlist.Prescriprionlistdata;
import com.hit.assure.Model.Prescriptionlist.PrescriptionlistResponse;
import com.hit.assure.Model.UploadSessionimageResponse;
import com.hit.assure.Model.UplodedPrescriptionResponse;
import com.hit.assure.Model.ViewDietPlanPrescription.ViewDietplanPrescriptionResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.APIServices;
import com.hit.assure.Retrofit.RetrofitBase;
import com.hit.assure.Util.PreferenceServices;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadSessionImageActivity extends AppCompatActivity {

    private TextView txt_choose_images, txt_upload_images;
    private ProgressDialog progressDialog;
    private LinearLayout ll_session_images, ll_view_diet_plan;
    private ImageView img_prescription, img_zoom;
    private CardView card_prescription_img;
    private FrameLayout fl_back, fl_selected_prescription, fl_clear;
    private String patient_id = "";
    private String booking_id = "";
    private String strprescimg = "";
    private String doctor_specialization = "";
    public static APIServices apiServices;
    private ImageView img_type, img_session1;
    private RecyclerView rec_prescription;
    private EditText edtxt_doctor_note;
    private LinearLayout ll_doctor_note;
    private TextView txt_booking_no, txt_clinic_name, txt_clinic_date, txt_time, txt_type;

    private TextView txt_viewplan_optimum_hair_skin, txt_viewplan_loss_weight, txt_viewplan_medical_condition, txt_viewplan_gain_weight_muscle_build, txt_viewplan_eating_disorder, txt_viewplan_athletic_performance,
            txt_viewplan_improve_and_maintain, txt_viewplan_pcod, txt_viewplan_recovery_surgery;

    private RelativeLayout rl_optimum_hair_skin, rl_loss_weight, rl_medical_condition, rl_gain_weight_muscle_build, rl_eating_disorder, rl_athletic_performance,
            rl_improve_maintain_health, rl_pcod, rl_recovery_surgery;

    String strOptimumHairSkinFilePath = "";
    String strLossWeightFilePath = "";
    String strMedicalConditionFilePath = "";
    String strGainWeightMusclebuildFilePath = "";
    String strEatingDisorder = "";
    String strAthleticPerformance = "";
    String strImprove_and_maintain = "";
    String strPCOD = "";
    String strRecoverySurgery = "";

    private byte[] mByteArr = null;
    public static String ImageUri1 = "";
    Uri img_uri_profile;

    String strImagePath = "";
    private static final int PICK_FILE_GALLERY = 13;
    private static final int PICK_CAMERA_IMG = 101;
    private static final int CAMERA_REQUEST_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 11101;
    private static final int REQUEST_CODE_POSTCROPGALLERY = 1331;
    String camerapermission[];
    Uri camera_gallery_uri;
    String FILE_PATH = "";
    String FILE_NAME = "";
    String FILE_TYPE = "";
    public static File image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_session_image);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        camerapermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        apiServices = RetrofitBase.getClient().create(APIServices.class);
        if (getIntent() != null) {
            booking_id = getIntent().getStringExtra("booking_id");
            patient_id = getIntent().getStringExtra("patient_id");
            doctor_specialization = getIntent().getStringExtra("doctor_specialization");
        }
        inita();
        callPatientbookingdetailsapi();
        callViewDietplanlistapi();
        callGetNoteapi();
    }

    private void inita() {

        edtxt_doctor_note = findViewById(R.id.edtxt_doctor_note);
        ll_doctor_note = findViewById(R.id.ll_doctor_note);

        txt_viewplan_optimum_hair_skin = findViewById(R.id.txt_viewplan_optimum_hair_skin);
        txt_viewplan_loss_weight = findViewById(R.id.txt_viewplan_loss_weight);
        txt_viewplan_medical_condition = findViewById(R.id.txt_viewplan_medical_condition);
        txt_viewplan_gain_weight_muscle_build = findViewById(R.id.txt_viewplan_gain_weight_muscle_build);
        txt_viewplan_eating_disorder = findViewById(R.id.txt_viewplan_eating_disorder);
        txt_viewplan_athletic_performance = findViewById(R.id.txt_viewplan_athletic_performance);
        txt_viewplan_improve_and_maintain = findViewById(R.id.txt_viewplan_improve_and_maintain);
        txt_viewplan_pcod = findViewById(R.id.txt_viewplan_pcod);
        txt_viewplan_recovery_surgery = findViewById(R.id.txt_viewplan_recovery_surgery);

        txt_viewplan_optimum_hair_skin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strOptimumHairSkinFilePath)
                        .putExtra("header", "For optimum hair and skin health")
                );
            }
        });

        txt_viewplan_loss_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strLossWeightFilePath)
                        .putExtra("header", "Loss weight")
                );
            }
        });

        txt_viewplan_medical_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strMedicalConditionFilePath)
                        .putExtra("header", "Deal with a medical condition"));
            }
        });

        txt_viewplan_gain_weight_muscle_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strGainWeightMusclebuildFilePath)
                        .putExtra("header", "Gain weight / Muscle Building"));
            }
        });

        txt_viewplan_eating_disorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strEatingDisorder)
                        .putExtra("header", "Help with an eating disorder"));
            }
        });

        txt_viewplan_athletic_performance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strAthleticPerformance)
                        .putExtra("header", "Improve athletic performance"));
            }
        });

        txt_viewplan_improve_and_maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strImprove_and_maintain)
                        .putExtra("header", "Improve and maintain health"));
            }
        });

        txt_viewplan_pcod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strPCOD)
                        .putExtra("header", "PCOD"));
            }
        });

        txt_viewplan_recovery_surgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, PDFPreviewActivity.class)
                        .putExtra("pdfurl", strRecoverySurgery)
                        .putExtra("header", "Recovery fromsurgery"));
            }
        });


        rl_optimum_hair_skin = findViewById(R.id.rl_optimum_hair_skin);
        rl_loss_weight = findViewById(R.id.rl_loss_weight);
        rl_medical_condition = findViewById(R.id.rl_medical_condition);
        rl_gain_weight_muscle_build = findViewById(R.id.rl_gain_weight_muscle_build);
        rl_eating_disorder = findViewById(R.id.rl_eating_disorder);
        rl_athletic_performance = findViewById(R.id.rl_athletic_performance);
        rl_improve_maintain_health = findViewById(R.id.rl_improve_maintain_health);
        rl_pcod = findViewById(R.id.rl_pcod);
        rl_recovery_surgery = findViewById(R.id.rl_recovery_surgery);


        ll_view_diet_plan = findViewById(R.id.ll_view_diet_plan);

        if (doctor_specialization.equalsIgnoreCase("Diet Consultation")) {
            ll_view_diet_plan.setVisibility(View.VISIBLE);
        } else {
            ll_view_diet_plan.setVisibility(View.GONE);
        }

        ll_view_diet_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ll_session_images = findViewById(R.id.ll_session_images);
        rec_prescription = findViewById(R.id.rec_prescription);
        LinearLayoutManager prescriptionllayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rec_prescription.setLayoutManager(prescriptionllayoutmanager);
        rec_prescription.setHasFixedSize(true);
        rec_prescription.setNestedScrollingEnabled(false);

        fl_clear = findViewById(R.id.fl_clear);
        img_zoom = findViewById(R.id.img_zoom);
        card_prescription_img = findViewById(R.id.card_prescription_img);
        img_prescription = findViewById(R.id.img_prescription);
        fl_selected_prescription = findViewById(R.id.fl_selected_prescription);
        img_type = findViewById(R.id.img_type);
        img_session1 = findViewById(R.id.img_session1);
        txt_type = findViewById(R.id.txt_type);
        txt_booking_no = findViewById(R.id.txt_booking_no);
        txt_clinic_name = findViewById(R.id.txt_clinic_name);
        txt_clinic_date = findViewById(R.id.txt_clinic_date);
        txt_time = findViewById(R.id.txt_time);

        fl_back = findViewById(R.id.fl_back);
        fl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txt_choose_images = findViewById(R.id.txt_choose_images);
        txt_choose_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(UploadSessionImageActivity.this);
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert layoutInflater != null;
                View dialogview = layoutInflater.inflate(R.layout.dialog_choose_upload_img, null);
                dialog.setCanceledOnTouchOutside(true);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                LinearLayout ll_camera = dialogview.findViewById(R.id.ll_camera);
                ll_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!checkCameraPermission()) {
                            requestCameraPermission();
                        } else {
                            pickCamera_front();
                        }
                        dialog.dismiss();
                    }
                });

                LinearLayout ll_folder = dialogview.findViewById(R.id.ll_folder);
                ll_folder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!checkCameraPermission()) {
                            requestGalleryPermission();
                        } else {
                            proceedAfterPermissionPickGallery(PICK_FILE_GALLERY);
                        }

                        dialog.dismiss();
                    }
                });

                dialog.setContentView(dialogview);
                dialog.getWindow().setGravity(Gravity.CENTER);
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                dialog.getWindow().setAttributes(params);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        txt_upload_images = findViewById(R.id.txt_upload_images);
        txt_upload_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!strImagePath.isEmpty()) {
                    uploadSessionimage();
                }
            }
        });

        fl_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strImagePath = "";
                txt_choose_images.setVisibility(View.VISIBLE);
                txt_upload_images.setVisibility(View.GONE);
                fl_selected_prescription.setVisibility(View.GONE);
            }
        });
        img_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UploadSessionImageActivity.this, GalleryActivity.class)
                        .putExtra("img", strprescimg));
            }
        });
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, camerapermission, CAMERA_REQUEST_CODE);
    }

    private void requestGalleryPermission() {
        ActivityCompat.requestPermissions(this, camerapermission, GALLERY_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean resultCamera = ContextCompat.checkSelfPermission(UploadSessionImageActivity.this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);
        boolean resultStorage = ContextCompat.checkSelfPermission(UploadSessionImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return resultCamera && resultStorage;
    }

    private void proceedAfterPermissionPickGallery(int code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), code);
    }

    private void pickCamera_front() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "newpic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "image to text");
        img_uri_profile = UploadSessionImageActivity.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, img_uri_profile);
        startActivityForResult(cameraintent, PICK_CAMERA_IMG);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);

        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "KW", null);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_", null);
        return Uri.parse(path);
    }

    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case PICK_CAMERA_IMG:
                    if (resultCode == Activity.RESULT_OK) {
                        String[] projection = {
                                MediaStore.MediaColumns._ID,
                                MediaStore.Images.ImageColumns.ORIENTATION,
                                MediaStore.Images.Media.DATA
                        };
                        Cursor c = UploadSessionImageActivity.this.getContentResolver().query(img_uri_profile, projection, null, null, null);
                        c.moveToFirst();

                        Bitmap bmp = MediaStore.Images.Media.getBitmap(UploadSessionImageActivity.this.getContentResolver(), img_uri_profile);
                        rotateImage(bmp, c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                        strImagePath = c.getString(c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                        Uri tempUri = getImageUri(UploadSessionImageActivity.this, bmp);

                        Intent intent = new Intent(UploadSessionImageActivity.this, ZoomCroppingActivity.class);
                        intent.putExtra("imagepath", getPath(UploadSessionImageActivity.this, tempUri));
                        intent.putExtra("comingfrom", "gallery");
                        startActivityForResult(intent, REQUEST_CODE_POSTCROPGALLERY);
                    }
                    break;


                case PICK_FILE_GALLERY:
                    if (resultCode == Activity.RESULT_OK) {
                        try {
                            Uri selectedFileUri = data.getData();
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(UploadSessionImageActivity.this.getContentResolver(), selectedFileUri);
                            Uri tempUri = getImageUri(UploadSessionImageActivity.this, imageBitmap);
                            strImagePath = getPath(UploadSessionImageActivity.this, tempUri);

                            Log.e("Gallerygsergbsedrf", strImagePath);

                            Intent intent = new Intent(UploadSessionImageActivity.this, ZoomCroppingActivity.class);
                            intent.putExtra("imagepath", strImagePath);
                            intent.putExtra("comingfrom", "gallery");
                            startActivityForResult(intent, REQUEST_CODE_POSTCROPGALLERY);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case REQUEST_CODE_POSTCROPGALLERY:
                    if (resultCode == Activity.RESULT_OK) {
                        FILE_TYPE = "image/jpeg";
                        FILE_NAME = "temp_photo.jpg";
                        FILE_PATH = "/sdcard/temp_photo.jpg";

                        CameraImageSave cameraSaveImage = new CameraImageSave();

                        String filePath = cameraSaveImage.getImagePath();

                        try {
                            ContextWrapper cw = new ContextWrapper(this);
                            File directory = cw.getDir("TmsDb", Context.MODE_PRIVATE);

                            Bitmap bitmap = cameraSaveImage.getBitmapFromFile(720);

                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            File imagesFolder = new File(directory, "MyImages");
                            imagesFolder.mkdirs();
                            image = new File(imagesFolder, "IMG_" + timeStamp + ".jpg");
                            if (image.exists()) image.delete();
                            try {
                                FileOutputStream out = new FileOutputStream(image);
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                out.flush();
                                out.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            strImagePath = "" + image;
                            if (bitmap != null) {
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                                cameraSaveImage.deleteFromFile();
                                mByteArr = stream.toByteArray();
                                bitmap = null;
                                cameraSaveImage = null;
                            }
                            Bitmap bmp = BitmapFactory.decodeByteArray(mByteArr, 0, mByteArr.length);
                            img_session1.setImageBitmap(bmp);
                            img_session1.setVisibility(View.VISIBLE);
                            fl_selected_prescription.setVisibility(View.VISIBLE);
                            txt_upload_images.setVisibility(View.VISIBLE);
                            txt_choose_images.setVisibility(View.GONE);

                        } catch (Exception e) {
                            Log.e("Exception", "Exception at Create broacast class");
                            e.printStackTrace();
                        }
                    }
                    break;

            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Bitmap rotateImage(Bitmap bmp, String path) {
        Matrix matrix = new Matrix();

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientstring = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientstring != null ? Integer.parseInt(orientstring) : ExifInterface.ORIENTATION_NORMAL;
        int rotateangle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            rotateangle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            rotateangle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            rotateangle = 270;

        matrix.setRotate(rotateangle);

        Bitmap newBit = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

        return newBit;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        pickCamera_front();
                    } else {
                        Toast.makeText(UploadSessionImageActivity.this, "permission Denied.!!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case GALLERY_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        proceedAfterPermissionPickGallery(PICK_FILE_GALLERY);
                    } else {
                        Toast.makeText(UploadSessionImageActivity.this, "permission Denied.!!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void callPatientbookingdetailsapi() {
        showProgressDialog();
        Call<PatientBookingDetailsResponse> call = apiServices.getPatientbookingdetails(booking_id
        );
        call.enqueue(new Callback<PatientBookingDetailsResponse>() {
            @Override
            public void onResponse(Call<PatientBookingDetailsResponse> call, Response<PatientBookingDetailsResponse> response) {
                if (response.body() != null) {
                    PatientBookingDetailsResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        hideProgressDialog();
                        txt_booking_no.setText(resource.getPatientBookingDetailsData().getBooking_id());
                        txt_clinic_name.setText(resource.getPatientBookingDetailsData().getClinic_name());
                        txt_clinic_date.setText(resource.getPatientBookingDetailsData().getDate());
                        txt_time.setText(resource.getPatientBookingDetailsData().getTime());
                        if (resource.getPatientBookingDetailsData().getType().equalsIgnoreCase("Video")) {
                            img_type.setImageResource(R.drawable.icon_video);
                            txt_type.setText("Virtual Consultion Appointment");
                        } else {
                            img_type.setImageResource(R.drawable.img_clinic);
                            txt_type.setText("In Clinic Appointment");
                        }

                        if (resource.getPatientBookingDetailsData().getSession_image().isEmpty()) {
                            ll_session_images.setVisibility(View.VISIBLE);
                        } else {
                            ll_session_images.setVisibility(View.GONE);
                        }

                        callPrescriptionlistapi();
                        callgetUploadedPrescapi();
                    } else {
                        hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<PatientBookingDetailsResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    private void callGetNoteapi() {
        showProgressDialog();
        Call<NoteResponse> call = apiServices.getNote(booking_id
        );
        call.enqueue(new Callback<NoteResponse>() {
            @Override
            public void onResponse(Call<NoteResponse> call, Response<NoteResponse> response) {
                if (response.body() != null) {
                    NoteResponse noteResponse = response.body();
                    if (noteResponse.getResponseCode() == 200) {
                        ll_doctor_note.setVisibility(View.VISIBLE);
                        edtxt_doctor_note.setText(noteResponse.getNoteData().getNote());
                    } else {
                        ll_doctor_note.setVisibility(View.GONE);
                        Toast.makeText(UploadSessionImageActivity.this, "" + noteResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NoteResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    private void uploadSessionimage() {
        showProgressDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("booking_id", booking_id);
        builder.addFormDataPart("patient_image", new File(strImagePath).getName(), RequestBody.create(MultipartBody.FORM, new File(strImagePath)));

        RequestBody requestBody = builder.build();

        Log.e("check", String.valueOf(requestBody));
        Call<UploadSessionimageResponse> call = apiServices.getUpdate(requestBody);
        call.enqueue(new Callback<UploadSessionimageResponse>() {
            @Override
            public void onResponse(Call<UploadSessionimageResponse> call, Response<UploadSessionimageResponse> response) {
                UploadSessionimageResponse resource = response.body();
                if (resource.getResponseCode() == 200) {
                    hideProgressDialog();
                    Toast.makeText(UploadSessionImageActivity.this, "" + resource.getResponseMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadSessionImageActivity.this, "" + resource.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UploadSessionimageResponse> call, Throwable t) {
                call.cancel();
            }
        });

    }


    private void callPrescriptionlistapi() {
        showProgressDialog();
        Call<PrescriptionlistResponse> call = apiServices.getPrescriptionlist(booking_id
        );
        call.enqueue(new Callback<PrescriptionlistResponse>() {
            @Override
            public void onResponse(Call<PrescriptionlistResponse> call, Response<PrescriptionlistResponse> response) {
                if (response.body() != null) {
                    PrescriptionlistResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        if (resource.getPrescriprionlistdataList() != null && !resource.getPrescriprionlistdataList().isEmpty()) {
                            hideProgressDialog();
                            PrescriptionlistAdapter prescriptionlistAdapter = new PrescriptionlistAdapter(UploadSessionImageActivity.this, resource.getPrescriprionlistdataList());
                            rec_prescription.setAdapter(prescriptionlistAdapter);
                        } else {
                            hideProgressDialog();
                        }
                    } else {
                        hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<PrescriptionlistResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    private void callgetUploadedPrescapi() {
        showProgressDialog();
        Call<UplodedPrescriptionResponse> call = apiServices.getUploadedPrescriptionIMg(booking_id
        );
        call.enqueue(new Callback<UplodedPrescriptionResponse>() {
            @Override
            public void onResponse(Call<UplodedPrescriptionResponse> call, Response<UplodedPrescriptionResponse> response) {
                if (response.body() != null) {
                    UplodedPrescriptionResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        hideProgressDialog();
                        Picasso.get().load(resource.getData()).into(img_prescription);
                        card_prescription_img.setVisibility(View.VISIBLE);
                        img_prescription.setVisibility(View.VISIBLE);
                        strprescimg = resource.getData();
                    } else {
                        hideProgressDialog();
                        card_prescription_img.setVisibility(View.GONE);
                        img_prescription.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<UplodedPrescriptionResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }


    private void callViewDietplanlistapi() {
        showProgressDialog();
        Call<ViewDietplanPrescriptionResponse> call = apiServices.getDietplanPrescriptoin(booking_id
        );
        call.enqueue(new Callback<ViewDietplanPrescriptionResponse>() {
            @Override
            public void onResponse(Call<ViewDietplanPrescriptionResponse> call, Response<ViewDietplanPrescriptionResponse> response) {
                if (response.body() != null) {
                    ViewDietplanPrescriptionResponse resource = response.body();
                    if (resource.getResponseCode() == 200) {
                        hideProgressDialog();
                        if (resource.getViewDietplanPrescriptionData().getOptimum_hair_skin().isEmpty()) {
                            rl_optimum_hair_skin.setVisibility(View.GONE);
                        } else {
                            strOptimumHairSkinFilePath = resource.getViewDietplanPrescriptionData().getOptimum_hair_skin();
                            rl_optimum_hair_skin.setVisibility(View.VISIBLE);
                        }

                        if (resource.getViewDietplanPrescriptionData().getLoss_weight().isEmpty()) {
                            rl_loss_weight.setVisibility(View.GONE);
                        } else {
                            strLossWeightFilePath = resource.getViewDietplanPrescriptionData().getLoss_weight();
                            rl_loss_weight.setVisibility(View.VISIBLE);
                        }

                        if (resource.getViewDietplanPrescriptionData().getDeal_with_medical_condition().isEmpty()) {
                            rl_medical_condition.setVisibility(View.GONE);
                        } else {
                            strMedicalConditionFilePath = resource.getViewDietplanPrescriptionData().getDeal_with_medical_condition();
                            rl_medical_condition.setVisibility(View.VISIBLE);
                        }

                        if (resource.getViewDietplanPrescriptionData().getGain_weight_muscle_build().isEmpty()) {
                            rl_gain_weight_muscle_build.setVisibility(View.GONE);
                        } else {
                            strGainWeightMusclebuildFilePath = resource.getViewDietplanPrescriptionData().getGain_weight_muscle_build();
                            rl_gain_weight_muscle_build.setVisibility(View.VISIBLE);
                        }

                        if (resource.getViewDietplanPrescriptionData().getEating_disorder().isEmpty()) {
                            rl_eating_disorder.setVisibility(View.GONE);
                        } else {
                            strEatingDisorder = resource.getViewDietplanPrescriptionData().getEating_disorder();
                            rl_eating_disorder.setVisibility(View.VISIBLE);
                        }

                        if (resource.getViewDietplanPrescriptionData().getAthletic_performance().isEmpty()) {
                            rl_athletic_performance.setVisibility(View.GONE);
                        } else {
                            strAthleticPerformance = resource.getViewDietplanPrescriptionData().getAthletic_performance();
                            rl_athletic_performance.setVisibility(View.VISIBLE);
                        }

                        if (resource.getViewDietplanPrescriptionData().getMaintain_health().isEmpty()) {
                            rl_improve_maintain_health.setVisibility(View.GONE);
                        } else {
                            strImprove_and_maintain = resource.getViewDietplanPrescriptionData().getMaintain_health();
                            rl_improve_maintain_health.setVisibility(View.VISIBLE);
                        }

                        if (resource.getViewDietplanPrescriptionData().getPcod().isEmpty()) {
                            rl_pcod.setVisibility(View.GONE);
                        } else {
                            strPCOD = resource.getViewDietplanPrescriptionData().getPcod();
                            rl_pcod.setVisibility(View.VISIBLE);
                        }

                        if (resource.getViewDietplanPrescriptionData().getRecovery_surgery().isEmpty()) {
                            rl_recovery_surgery.setVisibility(View.GONE);
                        } else {
                            strRecoverySurgery = resource.getViewDietplanPrescriptionData().getRecovery_surgery();
                            rl_recovery_surgery.setVisibility(View.VISIBLE);
                        }

                    } else {
                        hideProgressDialog();
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewDietplanPrescriptionResponse> call, Throwable t) {
                hideProgressDialog();
                call.cancel();
            }
        });
    }

    public class PrescriptionlistAdapter extends RecyclerView.Adapter<PrescriptionlistAdapter.ViewHolder> {

        //vars
        private Context mContext;
        private List<Prescriprionlistdata> prescriprionlistdataList;

        public PrescriptionlistAdapter(Context mContext, List<Prescriprionlistdata> prescriprionlistdataList) {
            this.mContext = mContext;
            this.prescriprionlistdataList = prescriprionlistdataList;
        }

        @NonNull
        @Override
        public PrescriptionlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_added_medicine, parent, false);
            PrescriptionlistAdapter.ViewHolder holder = new PrescriptionlistAdapter.ViewHolder(view);
            return holder;

        }

        @Override
        public void onBindViewHolder(PrescriptionlistAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            holder.txt_medicine_name.setText(prescriprionlistdataList.get(position).getMedicine_name());
            holder.txt_date.setText(prescriprionlistdataList.get(position).getDate());
            holder.txt_dosage.setText(prescriprionlistdataList.get(position).getDosage());
            holder.txt_taken.setText(prescriprionlistdataList.get(position).getTaken());
            holder.txt_medicine_note.setText(prescriprionlistdataList.get(position).getNote());
        }

        @Override
        public int getItemCount() {
            return prescriprionlistdataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView txt_medicine_name, txt_date, txt_dosage, txt_taken, txt_medicine_note;

            public ViewHolder(View itemView) {
                super(itemView);

                txt_medicine_name = itemView.findViewById(R.id.txt_medicine_name);
                txt_date = itemView.findViewById(R.id.txt_date);
                txt_dosage = itemView.findViewById(R.id.txt_dosage);
                txt_taken = itemView.findViewById(R.id.txt_taken);
                txt_medicine_note = itemView.findViewById(R.id.txt_medicine_note);

            }
        }


    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }
}