/*   
 * Copyright (c) 2010-2020 JUNE. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * JUNE. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with JUNE.   
 *   
 */ 

package com.june.common;

import java.io.IOException;  
import java.util.Enumeration;  
  
import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.apache.log4j.Logger;  
  
/**  
* @Description: 非法字符过滤器 
* 1.所有非法字符配置在web.xml中，如需添加新字符，请自行配置 
* 2.请注意请求与相应时的编码格式设置，否则遇到中文时，会出现乱码(GBK与其子集应该没问题) 
* @author caiyang
* @date 2016年2月18日 下午4:44:53 
* @version V1.0  
 */
public class CharFilter implements Filter {  
    private Logger log = Logger.getLogger(CharFilter.class);  
    private String encoding;  
    private String[] legalNames;  
    private String[] illegalChars;  
      
    public void init(FilterConfig filterConfig) throws ServletException {  
        encoding = filterConfig.getInitParameter("encoding");  
        legalNames = filterConfig.getInitParameter("legalNames").split(",");  
        illegalChars = filterConfig.getInitParameter("illegalChars").split(",");  
    }  
      
    public void destroy() {  
        encoding = null;  
        legalNames = null;  
        illegalChars = null;  
    }  
      
  
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain filterChain) throws IOException, ServletException {  
          
        HttpServletRequest req = (HttpServletRequest)request;  
        HttpServletResponse res = (HttpServletResponse) response;  
          
        //必须手动指定编码格式  
        req.setCharacterEncoding(encoding);  
        String tempURL = req.getRequestURI();   
        log.info(tempURL);  
        Enumeration<?> params = req.getParameterNames();  
          
        //是否执行过滤  true：执行过滤  false：不执行过滤  
        boolean executable = true;  
          
        //非法状态  true：非法  false；不非法  
        boolean illegalStatus = false;  
        String illegalChar = "";  
        //对参数名与参数进行判断  
        w:while(params.hasMoreElements()){  
              
            String paramName = (String) params.nextElement();  
              
            executable = true;  
              
            //密码不过滤  
            if(paramName.toLowerCase().contains("password")){  
                executable = false;  
            }else{  
                //检查提交参数的名字，是否合法，即不过滤其提交的值  
                f:for(int i=0;i<legalNames.length;i++){  
                    if(legalNames[i].equals(paramName)){  
                        executable = false;  
                        break f;  
                    }  
                }  
            }  
              
            if(executable){  
                String[] paramValues = req.getParameterValues(paramName);  
                  
                f1:for(int i=0;i<paramValues.length;i++){  
                      
                    String paramValue = paramValues[i];  
                      
                    f2:for(int j=0;j<illegalChars.length;j++){  
                          
                        illegalChar = illegalChars[j];  
                          
                        if(paramValue.indexOf(illegalChar) != -1){  
                            illegalStatus = true;//非法状态  
                            break f2;  
                        }  
                    }  
                      
                    if(illegalStatus){  
                        break f1;  
                    }  
                      
                }  
            }  
              
            if(illegalStatus){  
                break w;  
            }  
        }  
        //对URL进行判断  
        for(int j=0;j<illegalChars.length;j++){  
              
            illegalChar = illegalChars[j];  
              
            if(tempURL.indexOf(illegalChar) != -1){  
                illegalStatus = true;//非法状态  
                break;  
            }  
        }  
        if(illegalStatus){  
            //必须手动指定编码格式  
            res.setContentType("text/html;charset="+encoding);  
            res.setCharacterEncoding(encoding);  
            res.getWriter().print("<script>window.alert('当前链接中存在非法字符');window.history.go(-1);</script>");  
        }else{  
            filterChain.doFilter(request, response);  
        }  
    }  
  
}