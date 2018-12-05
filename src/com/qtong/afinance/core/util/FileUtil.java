package com.qtong.afinance.core.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传工具类  
 */
public class FileUtil {
	
	
	/**
	 * 
	 * 方法: base64 <br>
	 * 描述: 文件转换二进制 <br>
	 * @param img
	 * @return
	 * @throws IOException
	 */
	public static byte[] base64(File img) throws IOException {
		BufferedImage bi = ImageIO.read(img);    
		ByteArrayOutputStream baos = new ByteArrayOutputStream();    
		ImageIO.write(bi, "jpg", baos);    
		byte[] bytes = baos.toByteArray();   
		return bytes;
	}
	
	/**
	 * 
	 * 方法: fileUpload <br>
	 * 描述: TODO <br>
	 * @param srcFile
	 * @param srcFileName
	 * @param path
	 * @return
	 */
	public static String fileUpload(File srcFile, String srcFileName, String path) {
		// 文件上传
		FileInputStream is = null;
		FileOutputStream os = null;
		String newFilName = null;
		try {
			// 读取源文件创建读取流
			is = new FileInputStream(srcFile);
			// 创建新文件 创建写入流
			// 后缀
			String hz = srcFileName.substring(srcFileName.lastIndexOf("."));
			newFilName = UUID.randomUUID() + hz;
			// 新文件路径(如果文件夹不存在 则创建)
			File pathFile = new File(path);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			// 创建新的文件
			File newFile = new File(path + "\\" + newFilName);
			// 写入流
			os = new FileOutputStream(newFile);
			// 循环写入
			int b = is.read();
			while (b != -1) {
				os.write(b);
				b = is.read();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {// 如果写入流不为null 则关闭
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {// 如果读取流不为null 则关闭
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return newFilName;
	}
	
	public static String upFile(MultipartFile img, String filePath) {

		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String realName = img.getOriginalFilename();
		String type = realName.substring(realName.lastIndexOf("."),realName.length());
		String uuidName = UUID.randomUUID().toString()+type;
		File f = new File(filePath + "/" + uuidName);
		try {
			is = (InputStream) img.getInputStream();
			bis = new BufferedInputStream(is);
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead  = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			try {
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != fos) {
					fos.close();
					fos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}

				if (null != bis) {
					bis.close();
					bis = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return uuidName;
	}
	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param downloadFile
	 */
	public static void downloadFile(HttpServletRequest request,
			HttpServletResponse response, String downloadFile, String fileName) {

		BufferedInputStream bis = null;
		InputStream is = null;
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			File file = new File(downloadFile); // :文件的声明
			is = new FileInputStream(file); // :文件流的声明
			os = response.getOutputStream(); // 重点突出
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);

			if (request.getHeader("User-Agent").toLowerCase()
					.indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
			} else {
				// 对文件名进行编码处理中文问题
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
				fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
			}

			response.reset(); // 重点突出
			response.setCharacterEncoding("UTF-8"); // 重点突出
			response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
																// // 重点突出
			// inline在浏览器中直接显示，不提示用户下载
			// attachment弹出对话框，提示用户进行下载保存本地
			// 默认为inline方式
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			// response.setHeader("Content-Disposition",
			// "attachment; filename="+fileName); // 重点突出
			int bytesRead = 0;
			byte[] buffer = new byte[4096];// 4k或者8k
			while ((bytesRead = bis.read(buffer)) != -1) { // 重点
				bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {
			// 特别重要
			// 1. 进行关闭是为了释放资源
			// 2. 进行关闭会自动执行flush方法清空缓冲区内容
			try {
				if (null != bis) {
					bis.close();
					bis = null;
				}
				if (null != bos) {
					bos.close();
					bos = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
				if (null != os) {
					os.close();
					os = null;
				}
			} catch (Exception ex) {
				throw new RuntimeException(ex.getMessage());
			}
		}
	}
	/**
	 * <pre>
	 * fileDownLoad(文件下载)   
	 * &#64;param request
	 * &#64;param response
	 * &#64;param fileName
	 * </pre>
	 */
	public static void fileDownLoad(HttpServletRequest request, HttpServletResponse response, String fileName) {
		InputStream is = null;// 文件读取流
		OutputStream os = null;// 文件输出流
		BufferedInputStream bis = null;// 文件读取缓冲流;
		BufferedOutputStream bos = null;// 文件输出缓冲流;
		try {
			is = new FileInputStream(request.getRealPath("upload") + "/" + fileName);// 读取文件
			bis = new BufferedInputStream(is);// 文件读取缓冲流;
			os = response.getOutputStream();// 重点突出输出到浏览器
			bos = new BufferedOutputStream(os);
			// 解决浏览器兼容问题
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
			} else {
				// 对文件名进行编码处理中文问题
				fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
				fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
			}

			response.reset();
			response.setContentType("application/x-msdownload");/// 不同类型的文件对应不同的MIME类型
			// inline在浏览器中直接显示，不提示用户下载
			// attachment弹出对话框，提示用户进行下载保存本地
			// 默认为inline方式
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			// svn
			//
			byte[] b = new byte[4096];// 缓冲区
			int s = 0;
			while ((s = bis.read(b)) != -1) {
				bos.write(b, 0, s);
				bos.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (bos != null) {
					bos.flush();
					bos.close();
					bos = null;
				}

				if (os != null) {
					os.flush();
					os.close();
					os = null;
				}

				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
