<#assign known = SPRING_SECURITY_CONTEXT??>
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
    <link rel="stylesheet" href="/css/common/likesAndComments.css">
    <link rel="stylesheet" href="/css/common/pagination.css">
    <link rel="stylesheet" href="/css/single/singlePoem.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Стихотворение</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <#--блок с основным контентом-->
    <div class="container">
        <div class="poem_container">
            <img src="/upload/${poem.pathToAvatar}" class="author_avatar" alt="Аватар автора">
            <div class="author_name">${poem.firstName} ${poem.lastName}</div>

            <h1 id="fulltext">${poem.header}</h1>
            <img src="/upload/${poem.fileName}" class="poem_fool_image" alt="тематическая картинка">
            <div class="poem_fool_content">
                ${poem.content}
            </div>

            <div class="meta">
                <div class="like_comment">
                    <span class="like_btn"></span>
                    <span class="p_digit_l">${poem.likes}</span>
                    <img src="/img/comments.png" class="p_comment" alt="комментарии">
                    <span class="p_digit_c">${poem.comments}</span>
                    <div class="enter_for_like">
                        <h4 class="enter_question">Понравилось произведение?</h4>
                        <p>Войдите в аккаунт, чтобы поставить отметку</p>
                        <a href="/target/poem/${poem.id}" class="login">Войти</a>
                    </div>
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
                <p class="enter_for_comment">Пожалуйста, <a href="/target/poem/${poem.id}" class="auth_link">авторизуйтесь,</a> чтобы оставить комментарий!</p>
            </#if>
        </div>
    </div>

    <#-- блок футера   -->
    <@c.footer></@c.footer>
</div>
<#--Необходимо для получения ID стихотворения для запроса комментариев -->
<script>
    let compID = ${poem.id};
    let meLiked = ${poem.meLiked?string("1", "0")};
    // если делать здесь не числовами, а словами "yes", "no", то выдает ошибку
    let knownUser = ${known?string('1', '0')};
</script>
<script src="/js/header.js"></script>
<script src="/js/pagination.js"></script>
<script src="/js/comments.js"></script>
<script src="/js/likes.js"></script>
</body>
</html>