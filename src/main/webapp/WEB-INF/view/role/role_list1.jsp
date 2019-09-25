<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta charset="UTF-8">

<link type="text/css" rel="stylesheet" media="all"href="${pageContext.request.contextPath}/assets/css/global2.css" />
<link type="text/css" rel="stylesheet" media="all"href="${pageContext.request.contextPath}/assets/css/global_color.css" />
<link rel="stylesheet" type="text/css"
	href="https://www.jeasyui.com/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="https://www.jeasyui.com/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="https://www.jeasyui.com/easyui/themes/color.css">
<link rel="stylesheet" type="text/css"
	href="https://www.jeasyui.com/easyui/demo/demo.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="https://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
<%-- <%@include file="../include/hearder.jsp"%> --%>
<script language="javascript" type="text/javascript">

            function deleteRole(rid) {
                var r = window.confirm("确定要删除此角色吗？");
                if(r){
                	location.href="${pageContext.request.contextPath}/role/deleteRoleAction.do?rid="+rid;
                }
                var msg ='${msg}';
                window.confirm(msg)
				if(msg!=""){
					$("#operate_result_info").append(msg);
					$("#operate_result_info").css("display","block");
    	        }        	
            }

            function updateRole(rid){
            	location.href="${pageContext.request.contextPath}/role/updateRoleAction.do?rid="+rid;
            }
        </script>
</head>
<body>
	<!--Logo区域开始-->
	<div id="header">
		当前用户${Ad.aname} <img
			src="${pageContext.request.contextPath}/assets/images/logo.png"
			alt="logo" class="left" /> <a
			href="${pageContext.request.contextPath}/exitAction.do">[退出]</a>
	</div>
	<!--Logo区域结束-->
	<!--导航区域开始-->
	<%@include file="../include/navi.jsp"%>
	<!--导航区域结束-->
	<!--主要区域开始-->
	<div id="main">
		<table id="dg"  title="角色管理" class="easyui-datagrid" style="width: 930px; height: 410px" 
			url="/maventele/role/roleListAction.do"
			toolbar="#toolbar" pagination="true" rownumbers="true"
			fitColumns="true" singleSelect="true">
			<thead>
				<tr>
					<th field="rid" width="50" class="datagrid-header-over">角色ID</th>
					<th field="rname" width="50">角色名称</th>
					<th field="privs" width="50">拥有的权限</th>
					<th field="imgage" width="50">用户头像</th>
					<!-- 用户头像功能未启用 -->
				</tr>
			</thead>
		</table>
		
		<div id="toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="location.href='${pageContext.request.contextPath}/role/privListAction.do'">增加管理员</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editUser()">修改管理员</a> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="destroyUser()">删除管理员</a>
		</div>
		<div id="dlg" class="easyui-dialog" style="width: 400px"
			data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
			<form id="fm" method="post" novalidate
				style="margin: 0; padding: 20px 50px">
				<h3>User Information</h3>
				<div style="margin-bottom: 10px">
					<input name="rid" class="easyui-textbox" required="true"
						label="First Name:" style="width: 100%">
				</div>
				<div style="margin-bottom: 10px">
					<input name="rname" class="easyui-textbox" required="true"
						label="Last Name:" style="width: 100%">
				</div>
				<div style="margin-bottom: 10px">
					<input name="privs" class="easyui-textbox" required="true"
						label="name:" style="width: 100%">
				</div>
				<div style="margin-bottom: 10px">
					<input name="imgage" class="easyui-textbox" required="true"
						validType="email" label="Email:" style="width: 100%">
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
        var url;
        /* 增加管理员 */
        function newUser(){
        	location.href="${pageContext.request.contextPath}/role/updateRoleAction.do?rid="+rid;
        }
        
        function editUser(){
             var row = $('#dg').datagrid('getSelected');
             var rid;
             if (row){
                /* $('#dlg').dialog('open').dialog('center').dialog('setTitle','Edit User');
                $('#fm').form('load',row); 
                url = 'update_user.php?id='+row.id;
                */
        	location.href="${pageContext.request.contextPath}/role/updateRoleAction.do?rid="+row.id;
        	alert(rid);  
            } 
        }
        /*删除管理员*/
        function destroyUser(){
            var row = $('#dg').datagrid('getSelected');
            if (row){
            	           
            }
        }
    </script>
	<!--主要区域结束-->
	<div id="footer"></div>
</body>
</html>
