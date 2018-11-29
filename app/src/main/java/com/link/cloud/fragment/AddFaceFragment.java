package com.link.cloud.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.guo.android_extend.java.ExtByteArrayOutputStream;
import com.guo.android_extend.java.ExtOutputStream;
import com.hwangjr.rxbus.RxBus;
import com.link.cloud.Events;
import com.link.cloud.R;
import com.link.cloud.User;
import com.link.cloud.activity.BindActivity;
import com.link.cloud.base.BaseActivity;
import com.link.cloud.base.BaseFragment;
import com.link.cloud.controller.AddFaceContrller;
import com.link.cloud.network.response.MemberdataResponse;
import com.link.cloud.utils.FaceDB;
import com.link.cloud.widget.CameraFrameData;
import com.link.cloud.widget.CameraGLSurfaceView;
import com.link.cloud.widget.CameraSurfaceView;
import com.link.cloud.widget.camera.CameraListener;
import com.link.cloud.widget.camera.CameraPreview;
import com.link.cloud.widget.camera.CircleCameraLayout;
import com.zitech.framework.utils.ToastMaster;
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
public class AddFaceFragment extends BaseFragment implements CameraSurfaceView.OnCameraListener, View.OnTouchListener, AddFaceContrller.AddFaceContrllerListener {


    private android.widget.TextView backButton;
    private android.widget.TextView nextButton;
    private TextView takePhoto;
    private byte[] clone;
    private AFR_FSDKFace mAFR_FSDKFace;
    private Camera mCamera;
    private CameraGLSurfaceView svCameraSurfaceview;
    private int mWidth, mHeight, mFormat;
    private CameraSurfaceView mGLSurfaceView;
    int mCameraRotate;
    boolean mCameraMirror;
    public static String TAG = "Camera";
    private AddFaceContrller addFaceContrller;
    private MemberdataResponse memberdataResponse;


    @Override
    public void onInflateView(View contentView) {
        super.onInflateView(contentView);
        initView(contentView);
        memberdataResponse = ((BindActivity) getActivity()).getMemberdataResponse();
        ((BindActivity) getActivity()).speak(getResources().getString(R.string.face_camere));
        addFaceContrller = new AddFaceContrller(this, getActivity());

    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_addface;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    public void saveData(Bitmap mBitmap) {

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
            Log.d("com.arcsoft", "Face=" + result1.getFeatureData()[0] + "," + result1.getFeatureData()[1] + "," + result1.getFeatureData()[2] + "," + error1.getCode() + "<<<<<<<<<" + result.get(0).getDegree());
            mBitmap = null;
            if (error1.getCode() == error1.MOK) {
                mAFR_FSDKFace = result1.clone();
                ((BaseActivity) getActivity()).speak(getResources().getString(R.string.face_success));
                savefaceinfo();
            } else {
                ((BaseActivity) getActivity()).speak(getResources().getString(R.string.none_face));
            }
            error1 = engine1.AFR_FSDK_UninitialEngine();
            Log.d("com.arcsoft", "AFR_FSDK_UninitialEngine : " + error1.getCode());
        } else {
            ((BaseActivity) getActivity()).speak(getResources().getString(R.string.none_face));
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
            addFaceContrller.bindFace(User.get().getNumberType(), memberdataResponse.getUserInfo().getPhone(), Integer.parseInt(memberdataResponse.getUserInfo().getUserType()), Environment.getExternalStorageDirectory() + "/register.jpg", getActivity().getExternalCacheDir().getPath() + "/face.data");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View contentView) {


        backButton = contentView.findViewById(R.id.backButton);
        nextButton = contentView.findViewById(R.id.nextButton);
        takePhoto = contentView.findViewById(R.id.takePhoto);
        mGLSurfaceView = (CameraSurfaceView) contentView.findViewById(R.id.surfaceView);
        svCameraSurfaceview = contentView.findViewById(R.id.svCameraSurfaceview);

        mCameraRotate = 0;
        mCameraMirror = false;
        mWidth = 640;
        mHeight = 480;
        mFormat = ImageFormat.NV21;
        svCameraSurfaceview.setOnTouchListener(this);

        mGLSurfaceView.setOnCameraListener(this);
        mGLSurfaceView.setupGLSurafceView(svCameraSurfaceview, true, mCameraMirror, mCameraRotate);
        mGLSurfaceView.debug_print_fps(false, false);

        ViewUtils.setOnClickListener(backButton, this);
        ViewUtils.setOnClickListener(nextButton, this);
        ViewUtils.setOnClickListener(takePhoto, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                RxBus.get().post(new Events.BackView());
                getActivity().onBackPressed();
                break;

            case R.id.nextButton:

                break;

            case R.id.takePhoto:
                ExtByteArrayOutputStream ops = new ExtByteArrayOutputStream();
                YuvImage yuv = new YuvImage(clone, ImageFormat.NV21, 640, 480, null);
                yuv.compressToJpeg(new Rect(0, 0, 640, 480), 85, ops);
                final Bitmap bitmap = BitmapFactory.decodeByteArray(ops.getByteArray(), 0, ops.getByteArray().length);
                try {
                    File file = new File(Environment.getExternalStorageDirectory() + "/register.jpg");
                    if (file.exists()) {
                        file.delete();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream);
                    saveData(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    ToastMaster.longToast(e.getMessage());
                }
                break;
        }
    }

    @Override
    public Camera setupCamera() {
        // TODO Auto-generated method stub
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        try {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(mWidth, mHeight);
            parameters.setPreviewFormat(mFormat);
            mCamera.setDisplayOrientation(90);
            for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
                Log.d(TAG, "SIZE:" + size.width + "x" + size.height);
            }
            for (Integer format : parameters.getSupportedPreviewFormats()) {
                Log.d(TAG, "FORMAT:" + format);
            }

            List<int[]> fps = parameters.getSupportedPreviewFpsRange();
            for (int[] count : fps) {
                Log.d(TAG, "T:");
                for (int data : count) {
                    Log.d(TAG, "V=" + data);
                }
            }
            mCamera.setParameters(parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCamera != null) {
            mWidth = mCamera.getParameters().getPreviewSize().width;
            mHeight = mCamera.getParameters().getPreviewSize().height;
        }
        return mCamera;
    }

    @Override
    public void setupChanged(int format, int width, int height) {

    }

    @Override
    public boolean startPreviewImmediately() {
        return true;
    }

    @Override
    public Object onPreview(byte[] data, int width, int height, int format, long timestamp) {
        clone = data.clone();
        return null;
    }

    @Override
    public void onBeforeRender(CameraFrameData data) {

    }

    @Override
    public void onAfterRender(CameraFrameData data) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    @Override
    public void addFaceSuccess() {

        RxBus.get().post(new Events.SuccessView());
    }

    @Override
    public void addFaveFaild(String message) {

    }

    @Override
    public void newWorkFail() {

    }
}
