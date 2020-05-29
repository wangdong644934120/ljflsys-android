package com.dommy.qrcode.util;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetUtil {

    /**
     * 使用GET访问去访问网络
     * @param lx
     * @param news
     * @return 服务器返回的结果
     */
    public static String loginOfGet(String lx,String news){
        HttpURLConnection conn=null;
        try {
            String data="lx="+lx+"&news="+news;
            URL url=new URL("http://localhost:8080/cs/csServlet"+data);
            conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
            conn.connect();
            int code=conn.getResponseCode();
            if(code==200){
                InputStream is=conn.getInputStream();
                String state=getStringFromInputStream(is);
                return state;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }


        return null;
    }


    /**
     * 使用POST访问去访问网络
     * @param lx
     * @param news
     * @return
     */
    public static String LoginOfPost(String lx,String news){
        HttpURLConnection conn=null;
        try {
            URL url=new URL("http://192.168.0.104:8080/cs/csServlet");
            conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            //post请求的参数
            String data="lx="+lx+"&news="+news;
            OutputStream out=conn.getOutputStream();
            out.write(data.getBytes());
            out.flush();
            out.close();

            conn.connect();
            int code=conn.getResponseCode();
            if(code==200){
                //请求成功
                InputStream is=conn.getInputStream();
                String state=getStringFromInputStream(is);
                return state;
            }else{
                //请求失败
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(conn!=null){
                conn.disconnect();
            }
        }
        return null;
    }



    /**
     * 根据输入流返回一个字符串
     * @param is
     * @return
     * @throws Exception
     */
    private static String getStringFromInputStream(InputStream is) throws Exception{

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        byte[] buff=new byte[1024];
        int len=-1;
        while((len=is.read(buff))!=-1){
            baos.write(buff, 0, len);
        }
        is.close();
        String html=baos.toString();
        baos.close();
        return html;
    }
}
