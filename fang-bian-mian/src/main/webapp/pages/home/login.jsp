<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ taglib prefix="s" uri="/struts-tags" %>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
  <head>  
    <title>登录</title>  
    <meta http-equiv="pragma" content="no-cache">  
    <meta http-equiv="cache-control" content="no-cache">  
    <meta http-equiv="expires" content="0">      
  </head>  
  <body>  
   <s:form name="form1" action="/login" >  
       <s:textfield  name="username" label="username" ></s:textfield>  
       <s:password name="password" label="password" ></s:password>  
       <s:submit label="submit"></s:submit>  
   </s:form>  
   <s:actionerror/>  
  </body>  
</html>