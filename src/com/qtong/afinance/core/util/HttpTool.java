package com.qtong.afinance.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

public class HttpTool {
	
	static Logger logger = Logger.getLogger(HttpTool.class); 
	
	/**
	 * 封装HTTP通信，请求发起方提交请求报文后，获取服务提供方返回的响应报文+本次通信的耗时（ms）
	 * @param servUrl
	 * @param requestMessage
	 * @return
	 */
	public static HashMap<String,Object> getResponseMessage(String servUrl,String requestMessage){
		HashMap<String,Object> result = null;
		if(servUrl!=null){
	         long t1=System.currentTimeMillis();//>>>>>>>起始时间戳
	            //=======建立http连接，发送请求报文  ========
	            HttpURLConnection connection=javaProtogenesisHttpConnect(servUrl);
	            javaProtogenesisHttpRequest(connection, requestMessage);
	            //=======等待服务器端返回响应报文==========
	            String respMess =javaProtogenesisGetResponse(connection);//******响应报文   
	         long t2=System.currentTimeMillis();//<<<<<<<终止时间戳
	         
	            long encryptInterval=t2-t1;//*******通信耗时
	            
	            result=new HashMap<String,Object>();
	            result.put("responseMessage", respMess);
	            result.put("interval", encryptInterval);
	           
		}
		return result;
	}
	
	//=====HTTP协议======以下是采用JAVA原生API HttpURLConnection和相关配套类完成HTTP连接和发送请求、接受请求、响应数据的===========
	
	/**
	 * JAVA原生API--HTTP链接:HttpURLConnection类，使用完毕需要disconnect！！
	 * @param requestUrl
	 * @return
	 */
	public static HttpURLConnection javaProtogenesisHttpConnect(String requestUrl){
		 URL url=null;
		 HttpURLConnection connection=null;
		try {
			 url = new URL(requestUrl);
			 connection = (HttpURLConnection) url.openConnection();
			 connection.setDoInput(true);
			 connection.setDoOutput(true);
			 connection.setRequestMethod("POST");
			 connection.setUseCaches(false);
			 connection.setInstanceFollowRedirects(true);
			 connection.setRequestProperty("Content-Type","application/json");
			 connection.connect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} //请求url地址
		catch (IOException e) {
			e.printStackTrace();
		}
         return connection;
	}
	
	/**
	 * JAVA原生API--HTTP发送请求:借助HttpURLConnection类
	 * @param conn HttpURLConnection已建立连接的对象
	 * @param param 请求报文数据(支持String类型，可考虑json)
	 */
	public static void javaProtogenesisHttpRequest(HttpURLConnection conn,String param){
		OutputStream os=null;
		try{
			os = conn.getOutputStream();
            os.write( param.getBytes("utf-8"));
        } catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * JAVA原生API--HTTP接收请求
	 * @param req：HttpServletRequest对象，可获客户端的取请求信息
	 * @return 返回客户端请求的原始报文信息
	 */
	public static String javaProtogenesisGetRequest(HttpServletRequest req){
		String reqBody =null;
		BufferedReader br=null;
		try {
			br = new BufferedReader(new InputStreamReader(req.getInputStream(),"utf-8"));
long t0=System.currentTimeMillis();
			String line = null;
	        StringBuffer sb = new StringBuffer();
	        while((line = br.readLine())!=null){
	            sb.append(line);
	        }

	        // 将资料解码
	        reqBody = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return reqBody;
	}
	
	/**
	 * JAVA原生API--HTTP接收响应：借助HttpURLConnection类，需要关闭conn
	 * @param conn
	 * @return
	 */
	public static String javaProtogenesisGetResponse(HttpURLConnection conn){
		String resp=null;
		BufferedReader reader=null;
        try {
        	reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));
long t0=System.currentTimeMillis();
            String lines;
            StringBuffer sbf = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                sbf.append(lines);
            }
            
            resp = sbf.toString();    
            //System.out.println("返回来的报文："+resp);
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        //关闭HTTP链接
        conn.disconnect();
        return resp;
	}
	
	//=====HTTPS协议======以下是采用JAVA原生API HttpURLConnection和相关配套类完成HTTP连接和发送请求、接受请求、响应数据的===========
	/**
	 * JAVA原生API--HTTPS链接:CloseableHttpClient类，使用完毕需要disconnect！！
	 * 本方法使用试金石提供的Demo，创建Https连接对象，不需要请求地址，先建立Https通道，然后使用HttpPost对象创建请求地址、请求参数等！！
	 * @return
	 */
	public static CloseableHttpClient javaProtogenesisHttpsConnect(){
		SSLContext sslContext = null;
		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			}).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			SSLConnectionSocketFactory sslsf= new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
			@Override
			public void verify(String s, SSLSocket sslSocket) throws IOException {
			}
			@Override
			public void verify(String s, X509Certificate x509Certificate) throws SSLException {
			}
			@Override
			public void verify(String s, String[] strings, String[] strings1) throws SSLException {
			}
			@Override
			public boolean verify(String s, SSLSession sslSession) {
			return true;
			}
			});
			CloseableHttpClient httpsClient = HttpClients. custom ().setSSLSocketFactory(sslsf).build();
			return httpsClient;
	}
	
	/**
	 * JAVA原生API--HTTPS接收响应：借助CloseableHttpClient类，需要关闭conn
	 * @param httpsClient：创建好的Https连接对象
	 * @param requestURL：请求的服务地址
	 * @param reqMessToProvider：请求参数（json格式）
	 * @return 服务端的响应报文字符串
	 * @注意：释放各种资源！！！
	 */
	public static String javaProtogenesisGetHttpsResponse(CloseableHttpClient httpsClient,String requestURL,String reqMessToProvider){
		String responseBeforedecrypt=null;
		//1、设置Http的Post请求（HttpPost完成请求路径、请求参数的设置）
		HttpPost httpPost = new HttpPost(requestURL);
		StringEntity requestEntity =null;
		try {
			requestEntity = new StringEntity(reqMessToProvider);
			requestEntity.setContentType("application/json");
			httpPost.setEntity(requestEntity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//2、发送请求，并获取服务端响应
		CloseableHttpResponse response =null;
		try {
			response = httpsClient.execute(httpPost);
			HttpEntity responseEntity=response.getEntity();
			/**
			 * 方法一：使用官方工具类EntityUtils转换响应实体（官方不建议使用，除非响应实体来自可信HTTP服务器并且已知有限的长度！！！）	 
			try {
				responseBeforedecrypt = EntityUtils. toString (responseEntity, "utf-8");
			} catch (ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				EntityUtils.consume(responseEntity);
			}*/	
			
			/**
			 * 方法二：使用HttpEntity对象的getContent方法返回InputStream流，只是官方推荐的方式！！！
			 */	
			InputStream responseStream=responseEntity.getContent();
			BufferedReader reader=new BufferedReader(new InputStreamReader(responseStream));
			String lines;
            StringBuffer sbf = new StringBuffer();
            while ((lines = reader.readLine()) != null) {
                sbf.append(lines);
            }
            responseBeforedecrypt = sbf.toString();   
            if(responseStream!=null){
            	responseStream.close();//释放InputStream流
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(response!=null){
				try {
					response.close();//释放CloseableHttpResponse对象
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if(httpsClient!=null){
			try {
				httpsClient.close();//释放CloseableHttpClient对象！
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return responseBeforedecrypt;
	}

}
