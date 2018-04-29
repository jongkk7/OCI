package com.yjk.ociproject;

import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.yjk.ociproject.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity{
    private final String TAG = "### MainActivity";
    private ActivityMainBinding binding;

    private Bitmap image01,image02,image03,image04,image05,image06,image07,image08;
    private TessBaseAPI tessBaseAPI;
    private String dataPath = "";

    private int count = 1;
    private final String language = "kor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();

        setTessApi();
    }

    private void init(){
        image01 = BitmapFactory.decodeResource(getResources(), R.drawable.img01);
        image02 = BitmapFactory.decodeResource(getResources(), R.drawable.img02);
        image03 = BitmapFactory.decodeResource(getResources(), R.drawable.img03);
        image04 = BitmapFactory.decodeResource(getResources(), R.drawable.img04);
        image05 = BitmapFactory.decodeResource(getResources(), R.drawable.img05);
        image06 = BitmapFactory.decodeResource(getResources(), R.drawable.img06);
        image07 = BitmapFactory.decodeResource(getResources(), R.drawable.img07);
        image08 = BitmapFactory.decodeResource(getResources(), R.drawable.img08);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (count){
                    case 1:
                        binding.imageview.setImageBitmap(image01);
                        getTextString(image01);
                        break;
                    case 2:
                        binding.imageview.setImageBitmap(image02);
                        getTextString(image02);
                        break;
                    case 3:
                        binding.imageview.setImageBitmap(image03);
                        getTextString(image03);
                        break;
                    case 4:
                        binding.imageview.setImageBitmap(image04);
                        getTextString(image04);
                        break;
                    case 5:
                        binding.imageview.setImageBitmap(image05);
                        getTextString(image05);
                        break;
                    case 6:
                        binding.imageview.setImageBitmap(image06);
                        getTextString(image06);
                        break;
                    case 7:
                        binding.imageview.setImageBitmap(image07);
                        getTextString(image07);
                        break;
                    case 8:
                        binding.imageview.setImageBitmap(image08);
                        getTextString(image08);
                        break;
                }
                count++;
                if(count > 8) count = 1;
            }
        });

    }

    // 이미지에서 글자 얻어오기
    private void getTextString(Bitmap image){
        String result = null;
        tessBaseAPI.setImage(image);
        result = tessBaseAPI.getUTF8Text();
        binding.textview.setText(result);
    }

    // tess api 초기화 및 설정 ( eng or kor )
    private void setTessApi(){
        dataPath = getFilesDir() + "/tesseract/";

        checkFile(new File((dataPath + "tessdata/")));

        tessBaseAPI = new TessBaseAPI();
        tessBaseAPI.init(dataPath, language);
    }

    private void checkFile(File dir){
        if(!dir.exists() && dir.mkdirs()){
            copyFiles();
        }

        if(dir.exists()){
            String datafilepath = dataPath + "/tessdata/" + language + ".traineddata";
            File datafile = new File(datafilepath);
            if(!datafile.exists()){
                copyFiles();
            }
        }
    }
    private void copyFiles(){
        try{
            String filepath = dataPath + "/tessdata/" + language + ".traineddata";
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open("tessdata/" + language + ".traineddata");
            OutputStream outputStream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }catch (FileNotFoundException e){
            Log.d(TAG, "file not found error : " + e.getMessage());
        }catch (Exception e){
            Log.d(TAG, "copyFiles error : " + e.getMessage());
        }
    }

}
