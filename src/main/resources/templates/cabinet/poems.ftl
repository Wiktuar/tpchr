<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/poems.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/modal.css">
    <script src="https://kit.fontawesome.com/7535b878e8.js" crossorigin="anonymous"></script>
    <title>Мои стихи</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <div class="cabinet_container">
    <#-- импорт левого сайдбара с кнопками меню личного кабинета   -->
        <@c.cabinetButtons></@c.cabinetButtons>

        <div class="right-sb">
    <#--  кнопка добавления стихотврения      -->
            <button type="button" class="add_poem_btn">Добавить стихотворение</button>
<#--      Форма добавления стихотворения-->
            <div class="add_poem_box">
                <form id="poem_form">
                        <input type="hidden" class="id" name="id" value="0">
<#--        Поскольку картинка может измениться, на сервер перадаем и имя файла старой картинки, чтобы ее можно было удалить              -->
                        <input type="hidden" class="old_file_name" name="oldFileName">
                        <input type="hidden" class="release_date_input" name="releaseDate">
                        <input type="text" id="text" name="header" placeholder="Введите название стихотворения" maxlength="33">
                        <textarea id="content" name="content" placeholder="Введите текст стихотворения"></textarea>
                        <div class="view_cover">
                            <img src="/img/poemCover.jpg" class="cover" alt="Картинка стихотворения">
                            <span class="default_cover">*Это изображение будет установлено по умолчанию. Лучше его заменить.</span>
                        </div>
                        <div class="fileInput">
                            <label for="cover_input">Загрузите картинку для стихотворения</label>
                            <input type="file" id="cover_input" accept="image/*">
                            <input type="hidden" id="result_cover_input" name="cover" value="poemCover.jpg" data-form>
                        </div>
                        <div class="edit_cover_container">

                        </div>
                        <input type="submit" class="savePoem" value="Сохранить стизотворение">
                    </form>
            </div>
    <#--   flex контейнер. а внутри него стихотворения   -->
            <#if poems?has_content>
                <div class="poems-container">
                    <#list poems as poem>
                        <div class="poem_box">
                            <div class="poem_box_header">
                                <div class="poem_header">
                                    <#-- .inner_poem_header испльзуется в js коде для поиска элемента.  -->
                                    <#-- .ph отвечает непосредственно за стиль заголовка.  -->
                                    <div class="inner_poem_header${poem.id} ph">${poem.header}</div>
                                </div>
                                <div class="author_block">
                                    <img src="/upload/${poem.pathToAvatar}" class="avatar" alt="аватар автора">
                                    <h2 class="author-name">${poem.firstName} ${poem.lastName}</h2>
                                </div>
                            </div>

                            <div class="poem_box_body">
                                <img src="/upload/${poem.fileName}" class="poem-image" alt="тематическая картинка">
                                <div class="poem_content">
                                    ${poem.poemPreview}
                                </div>
                                   <a href="/cabinet/poem/${poem.id}"><div class="full_reading">
                                    читать полностью
                                </div></a>
                            </div>

                            <div class="poem_box_footer">
                                <div class="like_comment">
                                    <#if poem.meLiked>
                                        <i class="fa-solid fa-heart" style="color: #e60f0f;"></i>
                                    <#else>
                                        <i class="fa-regular fa-heart" style="color: #e60f0f;"></i>
                                    </#if>
                                    <span class="digit">${poem.likes}</span>
                                    <img src="/img/comments.png" class="comment" alt="комментарий">
                                    <span class="digit">${poem.comments}</span>
                                </div>
                                <div class="time-stamp">
                                    ${poem.releaseDate?truncate(11, "")}
                                </div>
                            </div>
                            <div class="footer_buttons">
                                <span class="update_link ${poem.id}" >Обновить</span>
                                <span class="delete_link ${poem.id}" >Удалить</span>
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

    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>
<#-- Скрипты, относящиеся к модальному окну -->
<script src="/js/modal/base.js"></script>
<script src="/js/modal/plugins/modal.js"></script>
<script src="/js/modal/plugins/confirm.js"></script>
<#-- Скрипт, относящийся непосредственно к странице-->
<script src="/js/header.js"></script>
<script type="module" src="/js/poems.js"></script>
</body>
</html>