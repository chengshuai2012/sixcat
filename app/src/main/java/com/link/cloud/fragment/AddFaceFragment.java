package com.link.cloud.fragment;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.arcsoft.facedetection.AFD_FSDKEngine;
import com.arcsoft.facedetection.AFD_FSDKError;
import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facedetection.AFD_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.image.ImageConverter;
import com.guo.android_extend.java.ExtOutputStream;
import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.activity.BindActivity;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.utils.FaceDB;
import com.link.cloud.widget.camera.CameraListener;
import com.link.cloud.widget.camera.CameraPreview;
import com.link.cloud.widget.camera.CircleCameraLayout;
import com.zitech.framework.utils.ViewUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：qianlu on 2018/10/24 16:03
 * 邮箱：zar.l@qq.com
 */
public class AddFaceFragment extends BaseFragment {


    private CircleCameraLayout rootLayout;
    private CameraPreview cameraPreview;
    private boolean resume = false;//解决home键黑屏问题
    private android.widget.TextView backButton;
    private android.widget.TextView nextButton;
    private ImageView image;

    private AFR_FSDKFace mAFR_FSDKFace;

    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        initView(contentView);
        ((BindActivity)getActivity()).speak(getResources().getString(R.string.please_sure));
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_addface;
    }

    @Override
    public void onResume() {
        super.onResume();

        resume = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != cameraPreview) {
            cameraPreview.releaseCamera();
        }
        rootLayout.release();
    }


    private void startCamera() {
        if (null != cameraPreview) cameraPreview.releaseCamera();
        cameraPreview = new CameraPreview(getActivity());
        rootLayout.removeAllViews();
        rootLayout.setCameraPreview(cameraPreview);
        if (resume) {
            rootLayout.startView();
        }
        cameraPreview.setCameraListener(new CameraListener() {
            @Override
            public void onCaptured(Bitmap bitmap) {
                if (null != bitmap) {
                    image.setImageBitmap(bitmap);
                    try {
                        File file= new File(Environment.getExternalStorageDirectory()+"/register.jpg");
                        if(file.exists()){
                            file.delete();
                        }
                        FileOutputStream fileOutputStream=new FileOutputStream(file.getAbsolutePath());
                        bitmap.compress(Bitmap.CompressFormat.JPEG,85,fileOutputStream);
                        saveData(bitmap);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        });
    }


    public void saveData(Bitmap mBitmap){

        byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight() * 3 / 2];
        ImageConverter convert = new ImageConverter();
        convert.initial(mBitmap.getWidth(), mBitmap.getHeight(), ImageConverter.CP_PAF_NV21);
        if (convert.convert(mBitmap, data)) {
        }
        convert.destroy();

        AFD_FSDKEngine engine = new AFD_FSDKEngine();
        AFD_FSDKVersion version = new AFD_FSDKVersion();
        List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
        AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.fd_key, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 16, 5);
        if (err.getCode() != AFD_FSDKError.MOK) {
            Toast.makeText(getActivity(), "FD初始化失败，错误码：" + err.getCode(), Toast.LENGTH_SHORT).show();
        }
        err = engine.AFD_FSDK_GetVersion(version);
        err = engine.AFD_FSDK_StillImageFaceDetection(data, mBitmap.getWidth(), mBitmap.getHeight(), AFD_FSDKEngine.CP_PAF_NV21, result);

        if (!result.isEmpty()) {
            AFR_FSDKVersion version1 = new AFR_FSDKVersion();
            AFR_FSDKEngine engine1 = new AFR_FSDKEngine();
            AFR_FSDKFace result1 = new AFR_FSDKFace();
            AFR_FSDKError error1 = engine1.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
            Log.d("com.arcsoft", "AFR_FSDK_InitialEngine = " + error1.getCode());
            if (error1.getCode() != AFD_FSDKError.MOK) {
                Toast.makeText(getActivity(), "FD初始化失败，错误码：" + error1.getCode(), Toast.LENGTH_SHORT).show();
            }
            error1 = engine1.AFR_FSDK_GetVersion(version1);
            Log.d("com.arcsoft", "FR=" + version.toString() + "," + error1.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
            error1 = engine1.AFR_FSDK_ExtractFRFeature(data, mBitmap.getWidth(), mBitmap.getHeight(), AFR_FSDKEngine.CP_PAF_NV21, new Rect(result.get(0).getRect()), result.get(0).getDegree(), result1);
            Log.d("com.arcsoft", "Face=" + result1.getFeatureData()[0] + "," + result1.getFeatureData()[1] + "," + result1.getFeatureData()[2] + "," + error1.getCode()+"<<<<<<<<<"+result.get(0).getDegree());
            mBitmap=null;
            if (error1.getCode() == error1.MOK) {
                mAFR_FSDKFace = result1.clone();
                Toast.makeText(getActivity(), R.string.face_success, Toast.LENGTH_SHORT).show();
                savefaceinfo();
            } else {
                Toast.makeText(getActivity(), R.string.none_face, Toast.LENGTH_SHORT).show();
            }
            error1 = engine1.AFR_FSDK_UninitialEngine();
            Log.d("com.arcsoft", "AFR_FSDK_UninitialEngine : " + error1.getCode());
        } else {
            Toast.makeText(getActivity(), R.string.none_face, Toast.LENGTH_SHORT).show();
        }
        err = engine.AFD_FSDK_UninitialFaceEngine();

    }



    //提交人脸数据
    public void savefaceinfo() {
        try {
            File file = new File(getActivity().getExternalCacheDir().getPath() + "/face.data");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fs = new FileOutputStream(getActivity().getExternalCacheDir().getPath() + "/face.data", true);
            ExtOutputStream bos = new ExtOutputStream(fs);
            bos.writeBytes(mAFR_FSDKFace.getFeatureData());
            bos.close();
            fs.close();
            RxBus.get().post(new Events.SuccessView());
//            presenter.bindFace(deviceID, 1, faceBindBean.getData().getUserInfo().getPhone(), faceBindBean.getData().getUserInfo().getUserType(), Environment.getExternalStorageDirectory() + "/register.jpg",getExternalCacheDir().getPath() + "/face.data");
        } catch (Exception e) {
            e.printStackTrace();
        }}

    private void initView(View contentView) {
        backButton = contentView.findViewById(R.id.backButton);
        nextButton = contentView.findViewById(R.id.nextButton);
        rootLayout = contentView.findViewById(R.id.rootLayout);
        image=contentView.findViewById(R.id.image);
        startCamera();
        ViewUtils.setOnClickListener(backButton, this);
        ViewUtils.setOnClickListener(nextButton, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
//                cameraPreview.releaseCamera();
                cameraPreview.releaseCamera();
                RxBus.get().post(new Events.BackView());
                getActivity().onBackPressed();
                break;

            case R.id.nextButton:
                cameraPreview.captureImage();//抓取照片
                break;
        }
    }
}
