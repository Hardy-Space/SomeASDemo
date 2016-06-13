package com.hardy.person.takepicture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //5.0之前的摄像头类
    private Camera mCamera;
    //是否已打开预览
    private boolean isPreview = false;
    //用来呈现预览界面
    private SurfaceView mSurfaceView;
    //SurfaceView的Holder
    private SurfaceHolder mSurfaceHolder;
    //记录当前为前置还是后置摄像头
    private int currentCam = 1;
    //持有调用系统相机拍的照片的输出Uri地址
    private Uri outputImageUri;
    //调用系统相机请求码
    public static final int TAKE_PHOTO = 1 ;
    //调用系统裁剪程序的请求码
    public static final int CROP_PHOTO = 2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        //添加CallBack回调方法
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
/*
                如果想要测试调用系统相机拍照就注释此行，否则会出现相机调用失败
*/
                //刚打开Activity时去初始化摄像头
                //initCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //如果摄像头已经打开
                if (mCamera != null) {
                    //如果正在预览
                    if (isPreview)
                        //关闭预览
                        mCamera.stopPreview();
                    //关闭摄像头
                    mCamera.release();
                    mCamera = null;
                    isPreview = false;
                }
            }
        });
    }

    private void initCamera() {
        //如果正在预览
        if (!isPreview) {
            //如果当前的是前置摄像头
            if (currentCam == 1) {
                //切换为后置摄像头，参数为0或者无(默认),1为前置
                mCamera = Camera.open(0);
                //标识位改变
                currentCam = 0;
            } else if (currentCam == 0) {//如果当前是后置摄像头
                //切换为前置
                mCamera = Camera.open(1);
                //标志位改变
                currentCam = 1;
            }
            //设置预览显示方向，顺时针旋转（参数）度，默认比竖屏视角逆时针偏90度
            mCamera.setDisplayOrientation(90);
        }
        if (mCamera != null && !isPreview) {//如果已经打开摄像头并且已经未开启预览
            try {
                //获得打开的摄像头的参数
                Camera.Parameters parameters = mCamera.getParameters();
                //设置所拍照片的大小
                parameters.setPictureSize(480, 480);
                //设置预览大小
                parameters.setPreviewSize(480, 480);
                //设置每秒多少帧
                parameters.setPreviewFpsRange(4, 10);
                //设置照片格式
                parameters.setPictureFormat(ImageFormat.JPEG);
                //设置照片质量
                parameters.set("jpeg-quality", 100);
                //把预览投放到SurfaceView上显示出来
                mCamera.setPreviewDisplay(mSurfaceHolder);
                //开启预览
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isPreview = true;
        }


    }

    /*
        拍照按钮关联方法
    */
    public void takePicture(View source) {
        //摄像头已打开
        if (mCamera != null) {
            //因为现在手机前置摄像头没有对焦功能，所以判断是直接拍照还是先对焦再拍照
            if (currentCam == 0) {
                //后置摄像头先对焦再拍照，参数是对焦回调接口对象
                mCamera.autoFocus(mAutoFocusCallback);
            } else if (currentCam == 1) {
                //前置摄像头直接拍照
                startCapture();
            }
        }
    }


    //定义对焦回调接口对象
    Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
                //对焦成功，拍照
                startCapture();
            }
        }
    };


    /*
        拍照实际操作方法
    */
    private void startCapture() {
        mCamera.takePicture(new Camera.ShutterCallback() {
            @Override
            public void onShutter() {
                //按下快门后回调此方法
            }
        }, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //用于处理保存前的照片
            }
        }, mPictureCallback);
    }

    //处理JPEG格式的照片的回调接口对象
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //这里定义成final类型是为了在下面的onClick里使用
            //拍成的照片形成的Bitmap对象
            final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            //获取显示保存照片的界面
            View view = getLayoutInflater().inflate(R.layout.save_picture_layout, null);
            //输入照片名字
            final EditText editText = (EditText) view.findViewById(R.id.pictureName);
            //显示拍的照片
            ImageView imageView = (ImageView) view.findViewById(R.id.showPicture);
            //显示照片
            imageView.setImageBitmap(bitmap);
            //显示一个Alert对话框来执行保存操作
            new AlertDialog.Builder(MainActivity.this)
                    .setView(view)
                    .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //保存文件地址
                            File file = new File("/mnt/sdcard/" + editText.getText().toString() + ".jpg");
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                //保存
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                fileOutputStream.close();
                                //下面这两句很重要，他会通知系统更新相册资源，没有这几句不会即时在相册中看到拍的照片
                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                //设置只更新这一张图片，如果不加这两句会遍历所有图片，会花很长时间
                                Uri uri = Uri.fromFile(file);
                                intent.setData(uri);
                                //发送更新广播
                                sendBroadcast(intent);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //保存完要关闭当前预览,然后重新打开，否则会停留在刚拍的那张照片的状态
                            mCamera.stopPreview();
                            mCamera.startPreview();
                            //当前状态是预览状态
                            isPreview = true;
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //保存完要关闭当前预览,然后重新打开，否则会停留在刚拍的那张照片的状态
                    mCamera.stopPreview();
                    mCamera.startPreview();
                    //当前状态是预览状态
                    isPreview = true;
                }
            }).show();

        }
    };

    /*
        和切换摄像头按钮关联的方法
    */
    public void switchCamera(View source) {
        //停止当前预览
        mCamera.stopPreview();
        //关闭当前摄像头
        mCamera.release();
        //标志位变为未打开预览
        isPreview = false;
        //初始化摄像头
        initCamera();
    }


    /*
        绑定在调用系统相机的按钮上的方法
    */
    public void openSystemCamera(View source) {
        File file = new File("/mnt/sdcard/output_image.jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputImageUri = Uri.fromFile(file);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outputImageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


/*
    处理系统相机拍照返回结果和裁剪程序处理结果
*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    //Android系统裁剪程序action，固定的
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    //这句一定要有，而且必须设置第二个参数表明Type是image，否则会出现找不到类似的Intent的错误
                    intent.setDataAndType(outputImageUri,"image/*");
//                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,outputImageUri);
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                //此处可以执行把图片显示到UI来
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }




}
