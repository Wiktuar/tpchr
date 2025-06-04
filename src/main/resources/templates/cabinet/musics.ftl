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
    <link rel="stylesheet" href="/css/musics.css">
    <link rel="stylesheet" href="/css/modal.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Save Music</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <#--<лок основного контента-->
        <div class="cabinet_container">
            <@c.cabinetButtons></@c.cabinetButtons>

            <div class="right-sb">
                <div class="choose-music">
                    <div class="create-single">
                        <img class="add_music_img" src="/img/music/cd_disk.png" alt="создать сингл">
                        <h3 class="create_music_header">Создать сингл</h3>
                    </div>
                    <div class="create-album">
                        <img class="add_music_img" src="/img/music/album.png" alt="создать сингл">
                        <h3 class="create_music_header">Создать альбом</h3>
                    </div>
                </div>

        <#--    здесь через JS будут размещаться формы для добавления музыки    -->
                <div class="form_container">

                </div>

                <#if albums?has_content>
                    <div class="albums-container">
                        <#list albums as album>
                            <div class="album_box">
                                <div class="album_box_header">
                                    <div class="album_header">
                                    <#-- .inner_album_header испльзуется в js коде для поиска элемента.  -->
                                    <#-- .ah отвечает непосредственно за стиль заголовка.  -->
                                        <div class="inner_album_header${album.id} ah">${album.header}</div>
                                    </div>
                                    <div class="author_block">
                                        <img src="/upload/${album.pathToAvatar}" class="avatar" alt="аватар автора">
                                        <h2 class="author-name">${album.firstName} ${album.lastName}</h2>
                                    </div>
                                </div>


                                <div class="album_box_body">
                                    <img src="/upload/${album.fileName}" class="album-image" alt="тематическая картинка">
                                    <div class="album_content">
                                       <div class="player">
                                            <div class="first_song_title">${album.song.header}</span></div>
                                            <div class="meta-data">
                                                <div class="current_time">00:00</div>
                                                <audio class="audio" src="/upload/${album.song.urlToMusicFile}" preload="metadata" data-status="pause"></audio>
                                                <div class="buttons">
                                                    <div class="btn play"><img src="/img/musicButtons/play.png" class="img_src" alt="play png"></div>
                                                </div>
                                                <div class="duration">${album.song.duration}</div>
                                            </div>
                                            <div class="progress_container">
                                                <div class="progress"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <a href="/cabinet/music/${album.id}">
                                        <div class="full_learning">слушать полностью</div>
                                    </a>
                                </div>

                                <div class="album_box_footer">
                                    <div class="like_comment">
                                        <#if album.meLiked>
                                            <i class="fa-solid fa-heart" style="color: #e60f0f;"></i>
                                        <#else>
                                            <i class="fa-regular fa-heart" style="color: #e60f0f;"></i>
                                        </#if>
                                        <span class="digit">${album.likes}</span>
                                            <img src="/img/comments.png" class="comment" alt="комментарий">
                                        <span class="digit">${album.comments}</span>
                                    </div>
                                    <div class="time-stamp">
                                        ${album.releaseDate?truncate(11, "")}
                                    </div>
                                </div>
                                <div class="footer_buttons">
                                    <span class="update_link ${album.id}" >Обновить</span>
                                    <span class="delete_link ${album.id}" >Удалить</span>
                                </div>
                            </div>
                        </#list>
                    </div>
                <#else>
                    <div class="empty_list">
                        Здесь пока ничего нет. <br> Ждем Вашего творчества!
                    </div>
                </#if>
            </div>
        </div>
    <#--    мобильное меню для сайта   -->
    <@c.bottomMenu></@c.bottomMenu>
    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>
<#-- Скрипты, относящиеся к модальному окну -->
<script src="/js/modal/base.js"></script>
<script src="/js/modal/plugins/modal.js"></script>
<script src="/js/modal/plugins/confirm.js"></script>
<#-- Скрипты, относящиеся к редактированию альбомов -->
<script type="module" src="/js/music/editAlbums.js"></script>
<script type="module" src="/js/music/music.js"></script>
<script src="/js/header.js"></script>
</body>
</html>