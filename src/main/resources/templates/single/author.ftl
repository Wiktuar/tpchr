<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
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

        <#if author.description??>
            <div class="description">${author.description}</div>
        <#else>
            <div class="description">Я расскажу о себе чуть позднее :))</div>
        </#if>
    </div>

    <div class="compose_links">
        <button type="button" id="poem_btn" class="compose_btn">Стихи</button>
        <button type="button" id="prose_btn" class="compose_btn">Проза</button>
        <button type="button" id="music_btn" class="compose_btn">Музыка</button>
        <button type="button" id="draw_btn" class="compose_btn">Рисунки</button>
    </div>
    <div class="compose_container">

    </div>
</div>

<#--блок добавления футера-->
<@c.footer></@c.footer>
<script>
    let authorId = ${author.id}
</script>
    <script src="/js/header.js"></script>
    <script type="module" src="/js/addDiffCompose.js"></script>
</body>
</html>