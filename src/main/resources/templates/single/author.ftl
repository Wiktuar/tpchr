<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/single/author.css">
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/poems.css">
    <link rel="stylesheet" href="/css/musics.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Страница автора</title>
</head>
<body>
<#--pаголовок сайта-->
<@c.header></@c.header>

<#--блок с основным контентом-->
<div class="container">
    <div class="author_data">
        <div class="personal">
            <div class="author_name">${author.firstName} ${author.lastName}</div>
            <img src="/upload/${author.pathToAvatar}" class="author_avatar" alt="Аватар автора">
        </div>

        <div class="desc_and_social">
            <#if author.description??>
                <div class="description">${author.description}</div>
            <#else>
                <div class="description">Я расскажу о себе чуть позднее :))</div>
            </#if>
            <button type="button" class="full_text_btn"><span class="inscription">Развернуть</span><img src="/img/arrow-down.png" alt="Стрелка вниз"></button>
            <div class="author_social_nets">
                Социальные сети автора:
                <div class="flex-social-nets">
                    <ul class="social-nets">
                        <#if author.vk??>
                            <li><a href="${author.vk}" title="${author.vk}"><img src="/img/vk.png" title="страница Вконтакте" alt="страница Вконтакте"></a></li>
                        </#if>
                        <#if author.tg??>
                            <li><a href="${author.tg}" title="${author.tg}"><img src="/img/tg.svg" title="канал в Telegram" alt="канал в Telegram"></a></li>
                        </#if>
                        <#if author.yt??>
                            <li><a href="${author.yt}" title="${author.yt}"><img src="/img/yt.png" title="канал на youTUbe" alt="канал на youTUbe"></a></li>
                        </#if>
                        <#if author.rt??>
                            <li><a href="${author.rt}" title="${author.rt}"><img src="/img/rt.jpg" title="канал на RuTube" alt="канал на youTUbe"></a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="compose_links">
        <button type="button" id="poem_btn" class="compose_btn">Стихи</button>
<#--        <button type="button" id="prose_btn" class="compose_btn">Проза</button>-->
        <button type="button" id="music_btn" class="compose_btn">Музыка</button>
<#--        <button type="button" id="draw_btn" class="compose_btn">Рисунки</button>-->
    </div>
    <div class="compose_container">

    </div>
</div>

<div class="bottom_menu">
    <button><img src="/img/bottom_menu/poems.png" class="bottom_menu_img bottom_poem_btn" alt="Стихи автора"></button>
    <button><img src="/img/bottom_menu/audio.png" class="bottom_menu_img bottom_music_btn" alt="Музыка автора"></button>
</div>

<#--блок добавления футера-->
<@c.footer></@c.footer>
<script>
    let authorId = ${author.id}
</script>
    <script src="/js/header.js"></script>
    <script type="module" src="/js/single/addDiffCompose.js"></script>
    <script src="/js/single/blocksHeight.js"></script>
</body>
</html>