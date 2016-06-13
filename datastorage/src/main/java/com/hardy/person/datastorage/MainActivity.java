package com.hardy.person.datastorage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private AutoCompleteTextView mName;
    private AutoCompleteTextView mPassword;
    private Button mLogin;
    private Button mRegister;
    private CheckBox isRemember ;
    private SharedPreferences mSharedPreferences;

    private void assignViews() {
        mName = (AutoCompleteTextView) findViewById(R.id.name);
        mPassword = (AutoCompleteTextView) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);
        isRemember = (CheckBox) findViewById(R.id.isRemember);
        //获得指定文件名的sharedpreference对象
        mSharedPreferences = getSharedPreferences("guests", MODE_PRIVATE);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        checkIfRememberPassword();
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    private void checkIfRememberPassword(){
        boolean ifRemember = mSharedPreferences.getBoolean("isRemember",false);
        if (ifRemember){
            String lastName = mSharedPreferences.getString("lastName",null);
            String lastPassword = mSharedPreferences.getString("lastPassword",null);
            mName.setText(lastName);
            mPassword.setText(lastPassword);
            isRemember.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login://登陆事件
                //是否填写完整登录信息
                if(TextUtils.isEmpty(mName.getText())||TextUtils.isEmpty(mPassword.getText())){
                    Toast.makeText(MainActivity.this, "Please input whole information!", Toast.LENGTH_SHORT).show();
                    break;
                }else{//检查用户名和密码是否正确
                    String inputName = mName.getText().toString();
                    String isNameExist = mSharedPreferences.getString(inputName, null);
                    String inputPassword = mPassword.getText().toString();
                    String isPasswordExist = mSharedPreferences.getString(inputName+"password",null);
                    if (!TextUtils.isEmpty(isNameExist)&&isPasswordExist.equals(inputPassword)){
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        if (isRemember.isChecked()){
                            editor.putBoolean("isRemember",true);
                            editor.putString("lastName", inputName);
                            editor.putString("lastPassword", inputPassword);
                            editor.commit();
                        }
                        Intent intent = new Intent(MainActivity.this,FileActivity.class);
                        startActivity(intent);
                        break;
                    }
                    Toast.makeText(MainActivity.this, "Name or Password is incorrect!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register://注册事件
                //信息是否填写完整
                if(TextUtils.isEmpty(mName.getText())||TextUtils.isEmpty(mPassword.getText())){
                    Toast.makeText(MainActivity.this, "Please input whole information!", Toast.LENGTH_SHORT).show();
                    break;
                }else{                    //填写完整了
                    //用户名是否已存在
                    if (!TextUtils.isEmpty(mSharedPreferences.getString(mName.getText().toString(), null))){
                        Toast.makeText(MainActivity.this, "The name is already exist!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    //用SharedPreference文件来保存用户信息
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    //保存用户名
                    editor.putString(mName.getText().toString(),mName.getText().toString());
                    //保存该用户名下的密码
                    editor.putString(mName.getText().toString() + "password", mPassword.getText().toString());
                    //别忘了提交！！！
                    editor.commit();
                    //注册成功
                    Toast.makeText(MainActivity.this, "Register successed! Information has been saved to SharedPreference Directory!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
