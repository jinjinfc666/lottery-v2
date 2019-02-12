package com.jll.sys.deposit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.jll.common.constants.Message;  
@Service
@Transactional
@PropertySource("classpath:resource-server.properties")
public class FTPServiceImpl implements FTPService {    
	@Value("${resource.host}")
	String resHost;
	
	@Value("${resource.port}")
	Integer resPort;
	
	@Value("${resource.root}")
	String resRoot;
	
	@Value("${resource.login.username}")
	String resLoginUser;
	
	@Value("${resource.login.pwd}")
	String resLoginPwd;
	
	@Value("${resource.publish.url}")
	String resPublishUrl;
	
    private  FTPClient ftp;    
    /** 
     *  
     * @param path 上传到ftp服务器哪个路径下    
     * @param addr 地址 
     * @param port 端口号 
     * @param username 用户名 
     * @param password 密码 
     * @return 
     * @throws Exception 
     */  
    @Override
    public  boolean connect(String path,String addr,int port,String username,String password) throws Exception{    
        boolean result = false;    
        ftp = new FTPClient();    
        int reply; 
    	ftp.connect(addr,port);    
        ftp.login(username,password);    
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);    
        reply = ftp.getReplyCode();    
        if (!FTPReply.isPositiveCompletion(reply)) {    
            ftp.disconnect();    
            return result;    
        }    
        ftp.changeWorkingDirectory(path);
        result = true;    
        return result;    
    }    
    /** 
     *  
     * @param file 上传的文件或文件夹 
     * @throws Exception 
     */  
    @Override
    public void upload(File file) throws Exception{
        if(file.isDirectory()){
    		ftp.makeDirectory(file.getName());
            ftp.changeWorkingDirectory(file.getName());
            String[] files = file.list();
            for (int i = 0; i < files.length; i++) {
                File file1 = new File(file.getPath()+"\\"+files[i] );
                if(file1.isDirectory()){
                    upload(file1);
                    ftp.changeToParentDirectory();
                }else{
                    File file2 = new File(file.getPath()+"\\"+files[i]);    
                    FileInputStream input = new FileInputStream(file2);    
                    ftp.storeFile(file2.getName(), input);    
                    input.close();                          
                }
            }
        }else{   
            File file2 = new File(file.getPath());    
            FileInputStream input = new FileInputStream(file2);
            ftp.storeFile(file2.getName(), input);
            input.close();
        }    
    }    

    @Override
    public Map<String,Object>  springUpload(HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	
    	 Map<String,Object> map=new HashMap<String,Object>();
    	 
         //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        
        String fileName = null;
        
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request)){
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator<String> iter = multiRequest.getFileNames();
             
            while(iter.hasNext()){
            	InputStream ins = null;
            	try {
            		//一次遍历所有文件
            		MultipartFile file = multiRequest.getFile(iter.next());
            		if(file!=null){
            			map= new HashMap<String,Object>();
            			fileName = URLEncoder.encode(file.getOriginalFilename());
            			fileName = fileName.replaceAll("\\%", "");
            			//上传
            			connect(resRoot, resHost, 
            					resPort, resLoginUser, 
            					resLoginPwd);
            			ins = file.getInputStream();
            			ftp.storeFile(fileName, ins);
            			map.clear();
            			map.put(Message.KEY_DATA, resPublishUrl + "/" + fileName);
            			map.put(Message.KEY_STATUS, Message.status.SUCCESS.getCode());
            		}
            		
            	}catch(Exception ex) {
            		
            	}finally {
            		if(ins != null) {
            			ins.close();
            		}
            	}
            	
            }
        }
        
		return map;
    }
	public static void inputStreamToFile(InputStream ins,File file) {
		OutputStream os = null;
		int bytesRead = 0;
		byte[] buffer = new byte[8192];
		try {
			os = new FileOutputStream(file);
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e) {
				}
			}
		}
	}
}  