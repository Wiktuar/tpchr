<#--https://stackoverflow.com/questions/28347589/checking-spring-security-roles-and-logged-username-in-freemarker-template-->
<#--https://stackoverflow.com/questions/47637166/spring-security-taglib-loading-error-in-freemarker-page-->
<#--https://vorba.ch/2018/spring-boot-freemarker-security-jsp-taglib.html-->
<#import "../common.ftl" as c>
<#assign known = SPRING_SECURITY_CONTEXT??>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/poem.css">
    <link rel="stylesheet" href="/css/common/pagination.css">
    <link rel="stylesheet" href="/css/common/likesAndComments.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Редактирование стихотворения</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <div class="cabinet_container">
        <@c.cabinetButtons></@c.cabinetButtons>

        <div class="right-sb">
           <h3 id="fulltext">${poem.header}</h3>
            <div>
                <img src="/upload/${poem.fileName}" class="poem_fool_image" alt="тематическая картинка">
                <div class="poem_fool_content">
                    ${poem.content}
                </div>
            </div>

            <div class="meta">
                <div class="like_comment">
                    <span class="like_btn"></span>
                    <span class="p_digit_l">${poem.likes}</span>
                    <img src="/img/comments.png" class="p_comment" alt="комментарии">
                    <span class="p_digit_c">${poem.comments}</span>
                </div>
                <div class="releaseDate">${poem.releaseDate?truncate(11, "")}</div>
            </div>

    <#--    пагинация. Рендеринг из JS    -->
            <ul class="pag_list">

            </ul>

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

    <#--    мобильное меню для сайта   -->
    <@c.bottomMenu></@c.bottomMenu>
    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>
<#--Необходимо для получения ID стихотворения для запроса комментариев -->
<script>
    let compID = ${poem.id};
    let meLiked = ${poem.meLiked?string("1", "0")};
    let knownUser = ${known?string('1', '0')};
</script>
<script src="/js/header.js"></script>
<script src="/js/pagination.js"></script>
<script src="/js/comments.js"></script>
<script src="/js/likes.js"></script>
</body>
</html>