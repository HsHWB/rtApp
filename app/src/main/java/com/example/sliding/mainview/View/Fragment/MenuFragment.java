package com.example.sliding.mainview.View.Fragment;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sliding.mainview.R;
import com.example.sliding.mainview.Utils.RoundImage;
import com.example.sliding.mainview.Utils.WindowsUtils;
import com.example.sliding.mainview.View.Adapter.MenuAdapter;
import com.example.sliding.mainview.View.CustomView.ListViewForScrollView;
import com.example.sliding.mainview.View.CustomView.SlidingMenu;

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
public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener{

    private View menuView;
    private ImageView headImage;
    private ListViewForScrollView listViewForScrollView;
    private MenuAdapter menuAdapter;
    private SlidingMenu slidingMenu;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private FrameLayout childContent;
    private ContentFragment contentFragment;
    private IOUDataFragment iouDataFragment;
    private boolean isIOUFirst = true;

    private float screenWidth;
    private SharedPreferences sharedPreferences;
    private static final String IMAGE_FILE_NAME = "header.jpg";
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;

    public MenuFragment() {
        // Required empty public constructor
    }

    public void setSlidingMenu(SlidingMenu slidingMenu){
        this.slidingMenu = slidingMenu;
    }

    public void setFragmentController(FragmentManager fm, FragmentTransaction transaction,
                                      FrameLayout childContent, ContentFragment contentFragment){
        this.fm = fm;
        this.transaction = transaction;
        this.childContent = childContent;
        this.contentFragment = contentFragment;
        this.iouDataFragment = new IOUDataFragment();
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
        listViewForScrollView = (ListViewForScrollView) menuView.findViewById(R.id.layout_leftitem_listview);
        menuAdapter = new MenuAdapter(getActivity(),slidingMenu);
        menuAdapter.setFragmentController(fm, transaction, childContent);
        listViewForScrollView.setOnItemClickListener(this);
        listViewForScrollView.setAdapter(menuAdapter);
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

    /**
     * data里面储存了图片信息
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
     * 获取选择的图片,并且进行裁剪，输出大小为180
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

    /**
     * 把选中的头像经过圆形头像处理后set进imageView，在data储存这个头像
     * @param data
     * @throws IOException
     */
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
//            slidingMenu.menuClose();
            contentFragment.netWork();
            fm = getFragmentManager();
            transaction = fm.beginTransaction();
            if (!isIOUFirst){
                /**
                 * 若第一次加载ioudata，则不需要隐藏
                 */
                transaction.hide(iouDataFragment);
            }
            transaction.show(contentFragment);
            transaction.commit();
        }else if (position == 1){
//            slidingMenu.menuClose();
            iouDataFragment.netWork();
            fm = getFragmentManager();
            transaction = fm.beginTransaction();
            if (isIOUFirst){
                transaction.add(this.childContent.getId(), iouDataFragment,"iouData");
                isIOUFirst = false;
            }else {

                transaction.show(iouDataFragment);
                transaction.hide(contentFragment);
            }
            transaction.commit();
            view.setBackgroundColor(getResources().getColor(R.color.blueblue));
        }else if (position == 2){
        }else if (position == 3){
        }

        menuAdapter.setItemColor(position);
    }
}
