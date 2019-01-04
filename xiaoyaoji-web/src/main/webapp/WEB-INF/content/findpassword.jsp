<%--
  User: zhoujingjie
  Date: 17/4/4
  Time: 13:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--[if lt IE 9]> <script>location.href='unsupport.html?refer='+encodeURIComponent(location.href)</script> <![endif]-->
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>找回密码 - 虎牙直播</title>
    <jsp:include page="/WEB-INF/includes/meta.jsp"/>
    <link rel="stylesheet" href="${assets}/css/index.css?v=${v}">
</head>
<body>
<div id="app">
    <div class="login-form mc" >
        <form class="form" v-on:submit.prevent>
            <div class="ta-c logo"><a href="${ctx}/"><img src="${assets}/img/logo/full.png"></a></div>
            <div class="item">
                <input type="text" autofocus="autofocus" name="email" v-model="email" v-validate="'required|email'"
                       class="text" placeholder="请输入注册邮箱"/>
                <p class="tip">{{errors.first('email')}}</p>
            </div>
            <div class="item">
                <input type="password" name="password" class="text" tabindex="2" v-model="password"
                       v-validate="'required'" initial="off" placeholder="请输入新密码"/>
                <p class="tip">{{errors.first('password')}}</p>
            </div>
            <div class="item">
                <input type="password" name="repassword" tabindex="3" v-model="rePassword" v-validate="'required'"
                       initial="off" maxlength="20" class="text" autocomplete="off" placeholder="请确认密码"/>
                <p class="tip">请输入确认密码</p>
            </div>
            <div class="item">
                <input type="submit" id="register-btn" tabindex="4" v-on:click="submit" class="btn" value="修改新密码"/>
            </div>
        </form>
        <div class="long-line"></div>
        <br/>
        <div>
            <a href="login">返回登陆</a>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/includes/js.jsp"/>
<script src="${assets}/js/findpassword.js"></script>

</body>
</html>