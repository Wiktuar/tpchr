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
  <link rel="stylesheet" href="/css/poems.css">
  <link rel="stylesheet" href="/css/musics.css">
  <link rel="stylesheet" href="/css/index.css">
  <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
  <title>Главная страница</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>
    <#--блок с основным контентом-->
    <div class="container">
        <div class="compose_container">
            <#list allComposDTO as acd>
                <#if acd.compType == 1>
                    <div class="poem_box">
                        <div class="poem_box_header">
                            <div class="poem_header">
                                <#-- .inner_poem_header испльзуется в js коде для поиска элемента.  -->
                                <#-- .ph отвечает непосредственно за стиль заголовка.  -->
                                <div class="inner_poem_header${acd.id} ph">${acd.header}</div>
                            </div>
                            <div class="author_block">
                                <img src="/upload/${acd.pathToAvatar}" class="avatar" alt="аватар автора">
                                <h2 class="author-name">${acd.firstName} ${acd.lastName}</h2>
                            </div>
                        </div>

                        <div class="poem_box_body">
                            <img src="/upload/${acd.fileName}" class="poem-image" alt="тематическая картинка">
                            <div class="poem_content">
                                ${acd.textPreview}
                            </div>
                            <a href="/main/poem/${acd.id}">
                            <div class="full_reading">
                                читать полностью
                            </div></a>
                        </div>

                        <div class="poem_box_footer">
                            <div class="like_comment">
                                <#if acd.meLiked>
                                    <i class="fa-solid fa-heart" style="color: #e60f0f;"></i>
                                <#else>
                                    <i class="fa-regular fa-heart" style="color: #e60f0f;"></i>
                                </#if>
                                <span class="digit">${acd.likes}</span>
                                <img src="/img/comments.png" class="comment" alt="комментарий">
                                <span class="digit">${acd.comments}</span>
                            </div>
                            <div class="time-stamp">
                                ${acd.releaseDate?truncate(11, "")}
                            </div>
                        </div>
                    </div>
                <#else>
                    <div class="album_box">
                        <div class="album_box_header">
                            <div class="album_header">
                                <#-- .inner_album_header испльзуется в js коде для поиска элемента.  -->
                                <#-- .ah отвечает непосредственно за стиль заголовка.  -->
                                <div class="inner_album_header${acd.id} ah">${acd.header}</div>
                            </div>
                            <div class="author_block">
                                <img src="/upload/${acd.pathToAvatar}" class="avatar" alt="аватар автора">
                                <h2 class="author-name">${acd.firstName} ${acd.lastName}</h2>
                            </div>
                        </div>


                        <div class="album_box_body">
                            <img src="/upload/${acd.fileName}" class="album-image" alt="тематическая картинка">
                            <div class="album_content">
                                <div class="player">
                                    <div class="first_song_title">${acd.song.header}</span></div>
                                    <div class="meta-data">
                                        <div class="current_time">00:00</div>
                                        <audio class="audio" src="/upload/${acd.song.urlToMusicFile}" preload="metadata" data-status="pause"></audio>
                                        <div class="buttons">
                                            <div class="btn play"><img src="/img/musicButtons/play.png" class="img_src" alt="play png"></div>
                                        </div>
                                        <div class="duration">${acd.song.duration}</div>
                                    </div>
                                    <div class="progress_container">
                                        <div class="progress"></div>
                                    </div>
                                </div>
                            </div>
                            <a href="/main/music/${acd.id}">
                                <div class="full_learning">слушать полностью</div>
                            </a>
                        </div>

                        <div class="album_box_footer">
                            <div class="like_comment">
                                <#if acd.meLiked>
                                    <i class="fa-solid fa-heart" style="color: #e60f0f;"></i>
                                <#else>
                                    <i class="fa-regular fa-heart" style="color: #e60f0f;"></i>
                                </#if>
                                <span class="digit">${acd.likes}</span>
                                <img src="/img/comments.png" class="comment" alt="комментарий">
                                <span class="digit">${acd.comments}</span>
                            </div>
                            <div class="time-stamp">
                                ${acd.releaseDate?truncate(11, "")}
                            </div>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>
    </div>

    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>

<script src="/js/header.js"></script>
<script type="module" src="/js/music/indexPageMusic.js"></script>
</body>
</html>