package com.delta_inductions.delta_task_3.view;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.delta_inductions.delta_task_3.Api.ThedogApi;
import com.delta_inductions.delta_task_3.R;
import com.delta_inductions.delta_task_3.Utils;
import java.io.File;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImgaeUploadAndAnalysis extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG ="upload" ;
    private static final String BASE_URL = "https://api.thedogapi.com/";
    private boolean isSelected = false;
    private Button upload;
    private Button select;
    private String filepath;
    private int  SELECT_FILE = 1;
    private ImageView ivImage;
    private String userChoosenTask;
    private Retrofit retrofit;
    private ThedogApi thedogapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgae_upload_and_analysis);
       thedogapi = getretrofit();
        select = findViewById(R.id.selectbutton);
        upload = findViewById(R.id.uploadbutton);
        ivImage = findViewById(R.id.imageView);
        select.setOnClickListener(this);
        upload.setOnClickListener(this);
    }
    private ThedogApi getretrofit() {
        if(retrofit==null)
        {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return (retrofit.create(ThedogApi.class));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.selectbutton)
     selectImage();
        if(v.getId()==R.id.uploadbutton)
     uploadimage(isSelected);
    }
    private void uploadimage(boolean isSelected) {
        if(isSelected)
        {
            File file = new File(filepath);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part =
                    MultipartBody.Part.createFormData("file", "dogimage", requestFile);
            RequestBody sub_id =
                    RequestBody.create(MediaType.parse("text/plain"), "uploadedimage1");
            Call call = (Call) thedogapi.uploadimage("4998955b-fd71-48df-b0a3-e59382b293e8",part,sub_id);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful())
                    {
                        Toast.makeText(ImgaeUploadAndAnalysis.this, "Successfully uploaded ", Toast.LENGTH_SHORT).show();
                    }

                    Log.d(TAG, "onResponse: "+response.code());
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.d(TAG, "onFailure: "+t.getMessage());

                }
            });

        }
        else
        {
            Toast.makeText(this, "Please select the image first", Toast.LENGTH_SHORT).show();
        }
        ivImage.setVisibility(View.INVISIBLE);
        this.isSelected = false;
    }

    @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (userChoosenTask.equals("Yes"))
                            galleryIntent();
                    } else {
                        //code for deny
                    }
                    break;
            }
        }

        private void selectImage() {
            final CharSequence[] items = {  "Yes",
                    "No" };
            AlertDialog.Builder builder = new AlertDialog.Builder(ImgaeUploadAndAnalysis.this);
            builder.setTitle("Select the image from gallery?");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    boolean result=Utils.checkPermission(ImgaeUploadAndAnalysis.this);
                    if (items[item].equals("Yes")) {
                        userChoosenTask ="Yes";
                        if(result)
                            galleryIntent();
                    }
                    else
                    {
                    dialog.dismiss();
                    }
                }
            });
            builder.show();
        }

        private void galleryIntent()
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == SELECT_FILE)
                    onSelectFromGalleryResult(data);
            }
        }

        @SuppressWarnings("deprecation")
        private void onSelectFromGalleryResult(Intent data) {

            Bitmap bm=null;
            if (data != null) {
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    Uri uri = data.getData();
                    Log.d(TAG, "onSelectFromGalleryResult: "+getRealPathFromUriAboveApi19(ImgaeUploadAndAnalysis.this,uri));
                    filepath = getRealPathFromUriAboveApi19(ImgaeUploadAndAnalysis.this,uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ivImage.setImageBitmap(bm);
            ivImage.setVisibility(View.VISIBLE);
            isSelected = true;
        }
    @SuppressLint("NewApi")
    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        String filePath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // If this is the type of document uri, then be handled by the document id
            String documentId = DocumentsContract.getDocumentId(uri);
            if (isMediaDocument(uri)) { // MediaProvider
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = {id};
                filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs);
            } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                filePath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())){
            // If the content is of type Uri
            filePath = getDataColumn(context, uri, null, null);
        } else if ("file".equals(uri.getScheme())) {
            // If the file is a type of Uri, direct access path corresponding picture
            filePath = uri.getPath();
        }
        return filePath;
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }
        private static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

    }
