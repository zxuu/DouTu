package com.zxu.picturesxiangce.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zxu.picturesxiangce.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.tencent.liteav.demo.videorecord.view.TCAudioControl.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCollectionFragment extends Fragment {

    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://10.0.116.20:3306/runoob";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&serverTimezone=UTC";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "1234";

    Connection conn = null;
    Statement stmt = null;
    String result = "null";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_collection, container, false);
        Toast.makeText(getContext(), "haha", Toast.LENGTH_SHORT).show();

        lianjie();
        return view;
    }

    private void lianjie() {

        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // 执行查询
            Toast.makeText(getContext(), "实例化Statement对象...", Toast.LENGTH_SHORT).show();
            //System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name, url FROM websites";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");
                // 输出数据
                Toast.makeText(getContext(), name+url, Toast.LENGTH_SHORT).show();
//                System.out.print("ID: " + id);
//                System.out.print(", 站点名称: " + name);
//                System.out.print(", 站点 URL: " + url);
//                System.out.print("\n");
            }
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){

            // 处理 JDBC 错误
            se.printStackTrace();
            Toast.makeText(getContext(), "1"+se.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "lianjie: "+se.getMessage());
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
            Toast.makeText(getContext(), "2"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
                Toast.makeText(getContext(), "3"+se2.getMessage(), Toast.LENGTH_SHORT).show();
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
                Toast.makeText(getContext(), "4"+se.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        //System.out.println("Goodbye!");
    }

}
