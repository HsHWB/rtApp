package com.example.sliding.mainview.View.Fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.RoundImage;
import com.example.sliding.mainview.Utils.WindowsUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 侧拉菜单item页fragment
 *
 */
public class MenuFragment extends Fragment {

    private View menuView;
    private ImageView headImage;
    private float screenWidth;
    private SharedPreferences sharedPreferences;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        menuView = inflater.inflate(R.layout.fragment_menu, container, false);
        init();
        return menuView;
    }

    public void init(){
        sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        screenWidth = WindowsUtils.getWindowWidth(getActivity());
        headImage = (ImageView) menuView.findViewById(R.id.leftitem_image);
        /**
         * 如果data下存在头像
         */
        if (sharedPreferences.contains("hasHeadImage")){
            if (sharedPreferences.getBoolean("hasHeadImage", false)) {
                String headImagePath = "headImage.png";

                FileInputStream localStream = null;
                try {
                    localStream = this.getActivity().openFileInput(headImagePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("hasHeadImage", false);
                    editor.commit();
                }

                Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                headImage.setImageBitmap(bitmap);
            }
        }else {
            System.out.println("不存在头像");
        }
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, IMAGE_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    resizeImage(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (isSdcardExisting()) {
                        resizeImage(getImageUri());
                    } else {
                        Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }
                    break;

                case RESIZE_REQUEST_CODE:
                    Toast.makeText(getActivity(), data.toString(),
                            Toast.LENGTH_LONG).show();
                    if (data != null) {
                        try {
                            showResizeImage(data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 判断是否存在sd卡
     * @return
     */
    private boolean isSdcardExisting() {
        final String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 获取选择的图片
     * @param uri
     */
    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 180);
        intent.putExtra("outputY", 180);
        intent.putExtra("return-data", true);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }
    private void showResizeImage(Intent data) throws IOException {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = RoundImage.getRoundBitmap(photo);
            headImage.setImageBitmap(photo);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putBoolean("hasHeadImage", true);
            editor.commit();
            String imagePath = "headImage.png";
            FileOutputStream localFileOutputStream1 = getActivity().openFileOutput(imagePath, 0);
            Bitmap.CompressFormat localCompressFormat = Bitmap.CompressFormat.PNG;
            photo.compress(localCompressFormat, 100, localFileOutputStream1);
            localFileOutputStream1.close();
        }
    }

    private Uri getImageUri() {
        return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                IMAGE_FILE_NAME));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
