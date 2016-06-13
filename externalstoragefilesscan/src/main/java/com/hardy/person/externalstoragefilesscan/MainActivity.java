package com.hardy.person.externalstoragefilesscan;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



//注意在AndroidManifest文件中配置相关关于sd卡的权限

public class MainActivity extends AppCompatActivity {


    //ToolBar
    Toolbar toolBar ;
    //显示当前目录的路径
    TextView fileTitle ;
    //显示当前目录下的文件清单
    ListView filesListView;
    //内容提供适配器
    SimpleAdapter mSimpleAdapter;
    //内容集合
    ArrayList<Map<String, Object>> mArrayList;
    //当前要显示的文件数组
    File[] files;
    //当前显示的目录清单的父目录
    File currentFile ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setToolBar();
        fileTitle = (TextView) findViewById(R.id.file_title);
        filesListView = (ListView) findViewById(R.id.files_list);
        mArrayList = new ArrayList<Map<String, Object>>();
        //获取sd卡根目录
        obtainRootDirectory();
        //更新List集合
        updateList();
        //设置ListView的适配器
        setAdapter();
    }


    /*
    * 设置ToolBar
    * */
    private void  setToolBar(){
        toolBar.setTitle("SD卡目录查看");
        toolBar.setNavigationIcon(R.drawable.up);
        setSupportActionBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFile.getParentFile() != null) {
                    currentFile = currentFile.getParentFile();
                    files = currentFile.listFiles();
                    updateList();
                    mSimpleAdapter.notifyDataSetChanged();
                }
            }
        });

    }


/*
* //获取sd卡根目录
* */
    private void obtainRootDirectory() {
        //是否有内存卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//如果有
            //获取sd卡根目录
            File root = Environment.getExternalStorageDirectory();
            //当前父目录是sd卡根目录
            currentFile = root ;
            //获取根目录下所有子文件
            files = root.listFiles();
        }

        /*
        * 测试往sd卡里读写文件
        * */
       /* if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = Environment.getExternalStorageDirectory();
            try {
                File fileqwe = new File(file.getCanonicalPath() + File.separator + "tempTest");
                Log.d("文件名", fileqwe.getPath());
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(fileqwe);
                byte[] bytes = "你好啊".getBytes();
                fileOutputStream.write(bytes);
                fileOutputStream.close();

                FileInputStream fileInputStream = new FileInputStream(fileqwe);
                byte[] byteas = new byte[1024];
                int len = fileInputStream.read(byteas);
                fileInputStream.close();
                Log.d("文件名", new String(byteas,0,len));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

    }

    /*
    * 设置ListView的Adapter
    * */
    private void setAdapter() {
        //实例化适配器对象
        mSimpleAdapter = new SimpleAdapter(this, mArrayList, R.layout.list_item_layout, new String[]{"fileIcon", "fileName"}, new int[]{R.id.file_icon, R.id.file_name});
        //连接适配器
        filesListView.setAdapter(mSimpleAdapter);
        //设置选项点击监听器
        filesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //当前目录变为点击的目录
                currentFile = files[position];
                //如果点击的选项是文件，则不执行任何操作
                if (currentFile.isFile()) return ;
                //否则获取其子目录
                File[] hasFiles = currentFile.listFiles();
                //判断子目录是否为空
                if (hasFiles == null||hasFiles.length ==0){
                    //若为空则提示
                    Toast.makeText(MainActivity.this, "此目录下没有文件！", Toast.LENGTH_SHORT).show();
                    return;
                }
                //不为空就更新当前要显示的文件数组
                files = hasFiles;
                //更新内容集合
                updateList();
                //通知适配器数据已经改变，注意更新
                mSimpleAdapter.notifyDataSetChanged();



            }
        });
    }

    /*
    * 更新ListView的集合内容
    * */
    private void updateList() {
        //显示当前目录路径
        fileTitle.setText(currentFile.getPath());
        //更新新数据前先清空之前的数据，很重要的一步！！！
        if (!mArrayList.isEmpty()){
            mArrayList.clear();
        }
        //更新操作
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> file = new HashMap<String, Object>();
            if (files[i].isDirectory()) {//如果是目录，则显示目录的图标
                file.put("fileIcon", R.drawable.directory);
            } else {//如果是文件，则显示文件的图标
                file.put("fileIcon", R.drawable.document);
            }
            //把文件的名字存入集合
            file.put("fileName", files[i].getName());
            //添加文件的Map集合
            mArrayList.add(file);
        }


    }

}
