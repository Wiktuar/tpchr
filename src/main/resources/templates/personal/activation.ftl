<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Страница активации</title>
    <link rel="stylesheet" href="/css/reset.css/css/cabinet/reset.css/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/activation.css">
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <#--  Основной блок  -->
    <div class="container">
        <h3>${activate}</h3>
        <div class="references">
            <a href="/cabinet" class="ref">Войти в личный кабинет</a>
            <a href="/" class="ref">На главную</a>
        </div>
    </div><#--  end of container   -->

    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>
<#--<script src="/js/header.js"></script>-->
</body>
</html>