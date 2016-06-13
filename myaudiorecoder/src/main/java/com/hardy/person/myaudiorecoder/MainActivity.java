package com.hardy.person.myaudiorecoder;

import android.app.AlertDialog;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //录制音频和视频的类
    private MediaRecorder mMediaRecorder;
    //音频保存地址
    private final String audioPath = "/mnt/sdcard/MyRecordeAudio.mp3";
    //视频保存地址
    private final String videoPath = "/mnt/sdcard/MyRecordVideo.mp4";
    //用来显示录制视频的预览SurfaceView
    private SurfaceView mSurface;
    //开始录像的按钮
    private Button mRecordVideo;
    //开始录音的按钮
    private Button mStart;
    //停止录音的按钮
    private Button mStop;
    //播放所录音频的按钮
    private Button mPlay;
    //停止录像的按钮
    private Button mStopVideo;
    //播放所录视频的按钮
    private Button mPlayVideo;
    private Camera mCamera ;
    //初始化组件
    private void assignViews() {
        mSurface = (SurfaceView) findViewById(R.id.surface);
        mRecordVideo = (Button) findViewById(R.id.recordVideo);
        mStart = (Button) findViewById(R.id.start);
        mStop = (Button) findViewById(R.id.stop);
        //一开始停止录音按钮不可用
        mStop.setEnabled(false);
        mPlay = (Button) findViewById(R.id.play);
        mStopVideo = (Button) findViewById(R.id.stopVideo);
        //一开始停止录像按钮不可用
        mStopVideo.setEnabled(false);
        mPlayVideo = (Button) findViewById(R.id.playVideo);
        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);
        mPlay.setOnClickListener(this);
        mRecordVideo.setOnClickListener(this);
        mStopVideo.setOnClickListener(this);
        mPlayVideo.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开始录音按钮业务
            case R.id.start:
                try {
                    mMediaRecorder = new MediaRecorder();
                    //重置MediaRecorder对象，不起眼但很必要
                    mMediaRecorder.reset();
                    //设置音频来源，此处来自于麦克风
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //设置输出格式，此处是3gp
                    //必须在编码格式设置之前
                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //设置音频编码格式
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //设置音频输出位置
                    mMediaRecorder.setOutputFile(audioPath);
                    //准备录音
                    mMediaRecorder.prepare();
                    //开始录音
                    mMediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //录音时开始录音按钮失效
                mStart.setEnabled(false);
                //停止录音按钮恢复可用
                mStop.setEnabled(true);
                ///录音过程中不允许录像
                mRecordVideo.setEnabled(false);
                break;
            //停止录音按钮业务
            case R.id.stop:
                //停止录音
                mMediaRecorder.stop();
                //停止录音按钮失效
                mStop.setEnabled(false);
                //开始录音按钮恢复可用
                mStart.setEnabled(true);
                //开始录像按钮恢复可用
                mRecordVideo.setEnabled(true);
                break;
            //播放所录音频按钮业务
            case R.id.play:
                //播放音频的类的对象
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    File file = new File(audioPath);
                    if (file.exists()&&file.length()!=0) {//如果已经录音或者录音文件存在，就开始播放
                        mediaPlayer.setDataSource(audioPath);
                        //调用setDatSource方法就必须调用prepare，如果是create传入的播放资源就不用prepare
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            //开始录像按钮业务
            case R.id.recordVideo:
                try {
//                    getServiceSupportedVideoSizes();
                    mMediaRecorder = new MediaRecorder();
                    mMediaRecorder.reset();
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //设置视频来源，此处来源于摄像头
                    mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    //此处是mp4
                    //必须在编码格式设置之前
                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                    //设置视频编码格式
                    mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
                    //设置视频大小,这里很重要，参数不能随便设置，一定是要手机摄像头能够支持的分辨率
                    mMediaRecorder.setVideoSize(480, 320);
                    //设置每秒多少帧
                    mMediaRecorder.setVideoFrameRate(4);
                    //设置视频输出位置
                    mMediaRecorder.setOutputFile(videoPath);
                    //设置视频预览显示在哪
                    mMediaRecorder.setPreviewDisplay(mSurface.getHolder().getSurface());
                    //实际录制的视频顺时针旋转90度（竖屏视角）
                    //和拍照的Camera正相反，那个是可以设置预览视角，但实际存储的还是横屏视角的照片
                    mMediaRecorder.setOrientationHint(90);
                    mMediaRecorder.prepare();
                    mMediaRecorder.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //开始录像，停止录像按钮恢复可用
                mStopVideo.setEnabled(true);
                //开始录像按钮暂时失效
                mRecordVideo.setEnabled(false);
                //录像过程中不允许录音
                mStart.setEnabled(false);
                break;
            //停止录像业务
            case R.id.stopVideo:
                //停止录像
                mMediaRecorder.stop();
                //停止录像按钮暂时失效
                mStopVideo.setEnabled(false);
                //又可以录制新视频了
                mRecordVideo.setEnabled(true);
                //也可以选择录制新音频
                mStart.setEnabled(true);
                break;
            //播放录制的视频的按钮业务
            case R.id.playVideo:
                File file = new File(videoPath);
                if (file.exists()&&file.length()!=0) {//如果视频文件存在，播放
                    VideoView videoView = new VideoView(this);
                    ViewGroup.LayoutParams  params = new ViewGroup.LayoutParams(480,320);
                    videoView.setLayoutParams(params);
                    //设置视频资源文件地址
                    videoView.setVideoPath(videoPath);
                    //开始播放
                    videoView.start();
                    //弹出一个对话框来盛放播放视频的VideoView
                    new AlertDialog.Builder(this)
                            .setView(videoView)
                            .setTitle("所录制视频：")
                            .setPositiveButton("关闭",null).show();

                }
                break;
        }
    }

/*
    查看手机摄像头支持的VideoSize分辨率有哪些，不能随便设置，一定要设置能支持的，否则会出RunTime错误,start fail-19
*/
    private void getServiceSupportedVideoSizes(){
        Camera camera = Camera.open();
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> strings = parameters.getSupportedVideoSizes();
        for (Camera.Size size:strings) {
            Log.d("@@@", "宽："+size.width+"||高："+size.height);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity关闭时释放MediaRecorder对象内存
        mMediaRecorder.release();
        mMediaRecorder = null;
    }
}
