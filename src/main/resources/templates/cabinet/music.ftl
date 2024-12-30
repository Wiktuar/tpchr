<#import "../common.ftl" as c>
<#assign known = SPRING_SECURITY_CONTEXT??>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/music.css">
    <link rel="stylesheet" href="/css/common/likesAndComments.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Музыкальный альбом</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <div class="cabinet_container">
        <@c.cabinetButtons></@c.cabinetButtons>
        <div class="right-sb">
            <#if album.firstName??>
                <img src="/upload/${album.pathToAvatar}" class="author_avatar" alt="Аватар автора">
                <div class="author_name">${album.firstName} ${album.lastName}</div>
            </#if>
            <h3>${album.header}</h3>
            <div class="player_wrapper">
                <div class="player">
                    <div class="cover"><img src="/upload/${album.fileName}" class="cover_img" alt="картинка песни"></div>
                    <div class="meta-data">
                        <div class="title_time">
                            <div class="title">Smoke on thw water</div>
                            <div class="time"><span class="current_time">02:38</span> / <span class="full_time">05:35</span></div>
                        </div>
                        <audio class="audio" preload="metadata"></audio>
                        <div class="progress_container">
                            <div class="progress"></div>
                        </div>
                        <div class="buttons">
                            <div class="btn prev"><img class="img_src" src="/img/music/buttons/previous.png" alt="prev png"></div>
                            <div class="btn play"><img class="img_src playing" src="/img/music/buttons/play.png" alt="play png"></div>
                            <div class="btn next"><img class="img_src" src="/img/music/buttons/next.png" alt="next png"></div>
                        </div>
                    </div>
                </div>
                <div class="song_list">
                    <!--    Сюда будет приходить контент из js       -->
                </div>
            </div>

            <div class="meta">
                <div class="like_comment">
                    <span class="like_btn"></span>
                    <span class="p_digit_l">${album.likes}</span>
                    <img src="/img/comments.png" class="p_comment" alt="комментарии">
                    <span class="p_digit_c">${album.comments}</span>
                </div>
                <div class="releaseDate">${album.releaseDate?truncate(11, "")}</div>
            </div>

            <img src="/img/vin.png" class="vignette" alt="виньетка">
            <div class="comments_container">
                <#--Здесь загружаются комментарии из JavaScript -->
            </div>

            <#if known>
                <div class="new_comment">
                    <input type="hidden" id="comment_input">
                    <textarea placeholder="Ваш комментарий" maxlength="700" rows="8" id="text_area"></textarea>
                    <div class="comments_footer">
                        <a href="" class="reset_btn"> Отмена </a>
                        <a href="" class="send_comment_btn"> Отправить </a>
                    </div>
                </div>
            <#else>
                Пожалуйста, зарегистрируйтесь!
            </#if>
        </div>
    </div>

    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>
    <#--Необходимо для получения ID стихотворения для запроса комментариев -->
    <script>
        let compID = ${album.id};
        let meLiked = ${album.meLiked?string("1", "0")};
        let knownUser = ${known?string('1', '0')};
    </script>
    <script type="module" src="/js/music/albumPlayer.js"></script>
    <script src="/js/header.js"></script>
    <script src="/js/comments.js"></script>
    <script src="/js/likes.js"></script>
</body>
</html>