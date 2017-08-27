package http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class Client {

    /**
     * 发送get请求
     * @param url 目标地址
     * @param paramsMap 请求参数map类型
     * @return 远程响应结果
     */
    public static String sendGet(String url,Map<String,String> paramsMap){

        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = codingReqParams(paramsMap);// 编码之后的参数
        try {
            String full_url = url + "?" + params;
            System.out.println(full_url);

            //创建URL
            URL connURL = new URL(full_url);
            //打开url链接
            HttpURLConnection httpConn = (HttpURLConnection) connURL.openConnection();
            //设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");

            // 建立实际的连接
            httpConn.connect();

            // 响应头部获取
            final Map<String, List<String>> headers = httpConn.getHeaderFields();

            // 遍历所有的响应头字段
            for(String key : headers.keySet()){
                System.out.println(key + "\t：\t" + headers.get(key));
            }

            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }

        } catch (MalformedURLException e) {
            // 创建 url 异常
            e.printStackTrace();
        } catch (IOException e) {
            //打开http url connection
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 发送Post请求
     * @param url 目标地址
     * @param paramsMap 请求参数map类型
     * @return 远程响应参数
     */
    public static String sendPost(String url, Map<String,String> paramsMap){
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;

        String params = codingReqParams(paramsMap);// 编码之后的参数
        try {
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            HttpURLConnection httpConn = (HttpURLConnection) connURL.openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");

            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;

    }

    public static String codingReqParams(Map<String,String> params) {

        StringBuffer sb = new StringBuffer();// 处理请求参数
        for (String key : params.keySet()) {
            //参数组装
            try {
                sb.append(key).append("=").append(URLEncoder.encode(params.get(key), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                System.out.println("参数编码异常");
                e.printStackTrace();
            }
            sb.append("&");
        }
        String tmp_params = sb.toString();
        //去掉最后一个&
        return tmp_params.substring(0, tmp_params.length() - 1);
    }

    public static void main(String[] args) {

    }
}
