﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title></title>
        <%@include file="../include/hearder.jsp" %>
        <script language="javascript" type="text/javascript">
           		$(function(){
           			$("#smit").click(function(){
            			$(".main_form").submit();
            		})
            		var msg = '${msg}';
            		if(msg != ""){
            			showResult(msg);
            		}
           		})       	
            //保存成功的提示消息
            function showResult(msg) {
                showResultDiv(true,msg);
                window.setTimeout("showResultDiv(false,'');", 3000);
            }
                function showResultDiv(flag,msg) { 
                    if (flag){
                    	$("#save_result_info").text(msg);
                    	$("#save_result_info").css("display","block");
                    }
                    else
                    	$("#save_result_info").css("display","none");
                }
        </script>
    </head>
    <body>
        <!--Logo区域开始-->
        <div id="header">当前用户${Ad.aname}
            <img src="${pageContext.request.contextPath}/assets/images/logo.png" alt="logo" class="left"/>
            <a href="${pageContext.request.contextPath}/exitAction.do">[退出]</a>            
        </div>
        <!--Logo区域结束-->
        <!--导航区域开始-->
         <%@include  file="../include/navi.jsp" %>  
        <!--导航区域结束-->
        <!--主要区域开始-->
        <div id="main">            
            <!--保存操作后的提示信息：成功或者失败-->
            <div id="save_result_info" class="save_success"></div><!--保存失败，角色名称重复！-->
            <form action="${pageContext.request.contextPath}/role/addRoleAction.do" method="post" class="main_form">
                <div class="text_info clearfix"><span>角色名称：</span></div>
                <div class="input_info">
                    <input type="text" class="width200" name="rname"/>
                    <span class="required">*</span>
                    <div class="validate_msg_medium"></div>
                </div>                    
                <div class="text_info clearfix"><span>设置权限：</span></div>
                <div class="input_info_high">
                    <div class="input_info_scroll">
                        <ul>
                        <c:forEach items="${privslist}" var="priv" >
                            <li><input type="checkbox" name="priv" value="${priv.pid}" />${priv.name}</li>
                            </c:forEach>
                        </ul>
                    </div>
                    <span class="required">*</span>
                    <div class="validate_msg_tiny">至少选择一个权限</div>
                </div>
                <div class="button_info clearfix">
                    <input type="button" value="保存"   id="smit" class="btn_save" />
                    <input type="button" value="取消" class="btn_save" />
                </div>
            </form>
        </div>
        <!--主要区域结束-->
        <div id="footer">
          
        </div>
    </body>
</html>
