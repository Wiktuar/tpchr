<#import "common.ftl" as c>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/authors.css">
    <title>Авторы проекта</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>
    <#--блок с основным контентом-->
    <div class="container">
        <h1>Авторы проекта "Творчество со смыслом"</h1>
        <div class="authors_container">
            <#list authors as author>
                <a href="/main/author/${author.id}" class="author_link">
                    <img src="/upload/${author.pathToAvatar}" alt="Аватар автора" class="avatar">
                    <div>${author.firstName} ${author.lastName}</div>
                </a>
            </#list>
        </div>
    </div>
    <#-- блок добавления футера   -->
    <@c.footer></@c.footer>
</div>

<script src="/js/header.js"></script>
</body>
</html>