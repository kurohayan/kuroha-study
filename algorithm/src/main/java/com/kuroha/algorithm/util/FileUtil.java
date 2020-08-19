package com.kuroha.algorithm.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * 文件操作工具类
 * @author kuroha
 * @date 2019-12-06 17:54:26
 */
@Slf4j
public class FileUtil {

	private static boolean PATH_FLAG;

	public static String qrcodePath = "/tmp/qrCode";

	static {
		File file = new File(qrcodePath);
		if (!file.exists()) {
			if (file.mkdirs()) {
				log.debug("创建目录成功");
			} else {
				log.debug("创建失败");
			}
		}
		file = new File("/tmp/privatekey");
		PATH_FLAG = file.exists();
	}

	public static void download(String urlString, String filename) {
		try {
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 输入流
			InputStream is = con.getInputStream();
			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			makeParentDir(filename);
			OutputStream os = new FileOutputStream(filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载网络图片到本地
	 *
	 * @param is       输入流
	 * @param filename 输出路径
	 */
	public static void download(InputStream is, String filename) {
		makeParentDir(filename);
		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		OutputStream os = null;
		try {
			makeParentDir(filename);
			os = new FileOutputStream(filename);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 创建父级目录
	 *
	 * @param baseFile
	 */
	public static void makeParentDir(String baseFile) {
		String parentDir = baseFile.substring(0, baseFile.lastIndexOf("/"));
		File file = new File(parentDir);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 　　* 将字符串转化为输入流
	 */
	public static InputStream getStringStream(String sInputString) {
		if (sInputString != null && !sInputString.trim().equals("")) {
			try {
				ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
				return tInputStringStream;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 读取文件的字节
	 * @return
	 */
	public static byte[] getFileByte(String inputFile) {
		if (StringUtil.isBlank(inputFile)) {
			try {
				InputStream inputStream = new FileInputStream(inputFile);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] bytes = new byte[1024];
				int len;
				while ((len = inputStream.read(bytes)) >0) {
					baos.write(bytes,0,len);
				}
				return baos.toByteArray();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 读取输入流的字节
	 * @return
	 */
	public static byte[] getFileByte(InputStream inputStream) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int len;
			while ((len = inputStream.read(bytes)) >0) {
				baos.write(bytes,0,len);
			}
			return baos.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getUrlFile(String url) throws IOException {

		URL urlObj = new URL(url);
		InputStream is = urlObj.openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		while (true) {
			String line = reader.readLine();
			if (line == null) {
				break;
			}
			sb.append(line);
		}
		is.close();
		return sb.toString();
	}

	public static String getImageStr(InputStream in) {
		//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		byte[] data = null;
		//读取图片字节数组
		try {
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (data == null) {
			return null;
		}
		//对字节数组Base64编码
		byte[] encode = Base64.getEncoder().encode(data);
		//返回Base64编码过的字节数组字符串
		return new String(encode);
	}

	public static String[] getAllFileName(String path) {
		File file = new File(path);
		return file.list();
	}
	public static File[] getAllFile(String path) {
		File file = new File(path);
		return file.listFiles();
	}

	/**
	 * 判断是否是服务器
	 * @return
	 */
	public static boolean whetherServer(){
		return PATH_FLAG;
	}

	public static void createPath(String path) {
		File file = new File(path);
		if (file.exists()){
			return;
		}
		file.mkdirs();
 	}

	public static void deletePath(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
		file.delete();

	}

	public static byte[] readFile(String filePath)
	{
		FileInputStream fileIn = null;
		try
		{
			fileIn = new FileInputStream(filePath);
			byte[] fileByte = new byte[fileIn.available()];
			fileIn.read(fileByte);
			fileIn.close();
			return fileByte;
		}
		catch (Exception ignored) {
			ignored.printStackTrace();
		}finally {
			if(fileIn != null){
				try {
					fileIn.close();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	// /上传图片素材/上传图文消息内的图片获取URL
	// url - 路径
	//filePath 图片绝对路径
	public static String postFile(String url, String filePath) throws ClientProtocolException, IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		String result = "";
		FileInputStream input = null;
		try {
			URL url1 = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Cache-Control", "no-cache");
			String boundary = "-----------------------------" + System.currentTimeMillis();
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			OutputStream output = conn.getOutputStream();
			output.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
			output.write(
					String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r%n", file.getName())
							.getBytes("UTF-8"));
			output.write("Content-Type: image/jpeg \r\n\r\n".getBytes("UTF-8"));
			byte[] data = new byte[1024];
			int len = 0;
			input = new FileInputStream(file);
			while ((len = input.read(data)) > -1) {
				output.write(data, 0, len);
			}
			output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
			output.flush();
			output.close();
			input.close();
			InputStream resp = conn.getInputStream();
			StringBuffer sb = new StringBuffer();
			while ((len = resp.read(data)) > -1){
				sb.append(new String(data, 0, len, StandardCharsets.UTF_8));
			}
			resp.close();
			result = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(input != null){
				input.close();
			}
		}
		return result;
	}

	public static boolean deleteFile(String path) {
		boolean flag = false;
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			flag = file.delete();
		}
		return flag;
	}


	public static boolean deleteDirectory(String path) {
		boolean flag = false;
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.isDirectory() && file.exists()) {
			flag = file.delete();
		}
		return flag;
	}

	public static String getQrcodePath() {
		return qrcodePath;
	}

	public static String readDataFile(String path) {
		String result = "";
		BufferedReader writer = null;
		try {
			writer = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(path), StandardCharsets.UTF_8));
			int ch = 0;
			while ((ch = writer.read()) != -1) {//fr.read()读取一个字节，判断此字节的值为-1表示读到文件末尾了。
				result = StringUtils.join(result, (char) ch);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(writer != null){
				try {
					writer.close();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static boolean writeDataFile(String path, String data) {
		FileOutputStream fop = null;
		File file;
		try {
			makeParentDir(path);
			file = new File(path);
			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				if(file.createNewFile()){
					log.info("成功创建文件");
				}else{
					log.info("创建文件失败");
				}
			}

			// get the content in bytes
			byte[] contentInBytes = data.getBytes(StandardCharsets.UTF_8);

			fop.write(contentInBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static String getType(MultipartFile file){
		String fileName = file.getOriginalFilename();
		String[] strs = fileName.split("\\.");
		String suffix = strs[strs.length-1];
		return suffix;
	}

	public static InputStream readFileToInputSteam(String fileName) throws Exception {
		if (StringUtil.isBlank(fileName)) {
			return null;
		}
		File file = new File(fileName);
		if (!file.exists()) {
			return null;
		}
		FileInputStream fileInputStream = new FileInputStream(file);
		int len;
		byte[] bs = new byte[8196];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((len = fileInputStream.read(bs)) > 0) {
			baos.write(bs,0,len);
		}
		return new ByteArrayInputStream(baos.toByteArray());
	}

	public static void initPath() {
		String[] path = new String[]{
				"/tmp/template",
				"/tmp/pdf"
		};
		for (String s : path) {
			File file = new File(s);
			if (!file.exists()) {
				if (file.mkdirs()) {
					log.debug(s + "创建成功");
				}
			}
		}
	}

	public static void byte2File(byte[] buff, String dest) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			file = new File(dest);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buff);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
	}
}
