<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/cabinet/cabinet.css">
    <title>Личный кабинет</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>
<#--  Основной блок  -->
    <div class="cabinet_container">
        <@c.cabinetButtons></@c.cabinetButtons>

        <div class="right-sb">
            <div class="flex-wrapper">
                <img src="/upload/${author.pathToAvatar}"  class="user-photo" alt="Фотография профиля">
                <div class="personal-data">
                    <h2 class="name_surname">${author.firstName} ${author.lastName}</h2>
                    <div class="email-block">
                        ${author.email}
                    </div>
                    <p>Мои социальные сети:</p>
                    <div class="flex-social-nets">
                        <ul class="social-nets">
                            <#if author.vk??>
                                <li><a href="${author.vk}" title="${author.vk}"><img src="/img/vk.png" alt="страница Вконтакте"></a></li>
                            </#if>
                            <#if author.tg??>
                                <li><a href="${author.tg}" title="${author.tg}"><img src="/img/tg.svg" alt="канал в Telegram"></a></li>
                            </#if>
                            <#if author.yt??>
                                <li><a href="${author.yt}" title="${author.yt}"><img src="/img/yt.png" title="канал на youTUbe" alt="канал на youTUbe"></a></li>
                            </#if>
                            <#if author.rt??>
                                <li><a href="${author.rt}" title="${author.rt}"><img src="/img/rt.jpg" title="канал на RuTube" alt="канал на youTUbe"></a></li>
                            </#if>
                        </ul>
                        <a href="/cabinet/editauthor" class="change-data">Изменить данные</a>
                    </div>
                </div>
            </div>
            <h3 class="am_header"> Немного обо мне:</h3>
            <#if author.description??>
                <div class="about_me">
                    ${author.description}
                </div>
            <#else>
                <div class="about_me">
                    Вы пока ничего не написали о себе <br>
                    Надеемся, что Вы сделаете это позже. :)
                </div>
            </#if>
        </div>
    </div>

    <#--    мобильное меню для сайта   -->
    <@c.bottomMenu></@c.bottomMenu>
    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>
<script src="/js/header.js"></script>
</body>
</html>