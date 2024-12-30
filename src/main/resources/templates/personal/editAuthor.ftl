<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/cabinet/editAuthor.css">
    <link rel="stylesheet" href="/css/modal.css">
    <title>Редактирование данных</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>
    <#--  Основной блок  -->
    <div class="cabinet_container">
        <@c.cabinetButtons></@c.cabinetButtons>

        <div class="right-sb">
            <h3>Редактирование профиля</h3>
            <form id="regForm">
                <input type="hidden" name="id" value="${author.id}">
                <input type="hidden" name="oldPath" value="${author.pathToAvatar}">
                <div class="flex-container">
                    <div class="flex-wrapper">
                        <div class="fName">
                            <label for="firstName">Ваше имя</label>
                            <input type="text" id="firstName" name="firstName" placeholder="Ваше имя" value="${author.firstName}">
                        </div>
                        <div class="fName">
                            <label for="lastName">Ваша фамилию</label>
                            <input type="text" id="lastName" name="lastName" placeholder="Ваша фамилия" value="${author.lastName}">
                        </div>
                        <div class="fName">
                            <label for="email">Электронная <почта></почта></label>
                            <input type="email" id="email" name="email" placeholder="Ваша почта" value="${author.email}">
                        </div>
                        <input type="hidden" id="inp_img" name="pathToAvatar" value="${author.pathToAvatar}">
                    </div>

                    <div class="flex-wrapper">
                        <#-- Блок добавления фотографии -->
                        <div class="image-wrapper">
                            <div class="imgPreview">
                                <img src="/upload/${author.pathToAvatar}" class="imageAva" alt="Ваша аватарка">
                                <img src="/img/close.png" class="default" title="Аватарка по умолчанию" alt="Аватарка по умолчанию">
                            </div> <#-- end of .imgPreview -->
                            <div class="fileInput">
                                <label for="file">Загрузите картинку для аватарки</label>
                                <input type="file"  id="file" name="file" accept="image/*">
                            </div>  <!-- end of .fileInput -->
                        </div>  <#--end of .image-wrapper -->
                    </div> <#--end of .flex-wrapper -->
                </div> <#--end of .flex-container -->

                <div class="additional">
                    <div class="about_me">
                        <p class="about_me_header">Расскажите немного о себе. Где вы живете? Чем увлекаетесь? Что вдохновляет Вас на творчество? Что для Вас Православие? и т.д.</p>
                        <textarea name="description" maxlength="2000" placeholder="Всего 2000 символов" spellcheck="true" wrap="soft">${author.description}</textarea>
                    </div>
                    <uL class="social-box">
                        <li class="sc-block">
                            <label for="vk">Страница Вконтакте</label>
                            <input type="text" id="vk" name="vk" placeholder="Ваша страница Вконтакте" value="<#if author.vk??>${author.vk}</#if>">
                        </li>
                        <li class="sc-block">
                            <label for="tg">Канал в Telegram</label>
                            <input type="text" id="tg" name="tg" placeholder="Ваш аккаунт в  Telegram" value="<#if author.tg??>${author.tg}</#if>">
                        </li>
                        <li class="sc-block">
                            <label for="yt">Канал на youTube</label>
                            <input type="text" id="yt" name="yt" placeholder="Ваш канал на youtube" value="<#if author.yt??>${author.yt}</#if>">
                        </li>
                        <li class="sc-block">
                            <label for="rt">Канал на RuTube</label>
                            <input type="text" id="rt" name="rt" placeholder="Ваш канал на rutube" value="<#if author.rt??>${author.rt}</#if>">
                        </li>
                    </uL>
                </div>

                <input type="submit" id="regBtn" class="regBtn" value="Отправить данные">
            </form>
        </div>
    </div>
<@c.footer></@c.footer>
<#-- Скрипты, относящиеся к модальному окну -->
<script src="/js/modal/base.js"></script>
<script src="/js/modal/plugins/modal.js"></script>
<script src="/js/modal/index.js"></script>
<script src="/js/modal/attention.js"></script>
<script src="/js/modal/logout.js"></script>
<#--Скрипт добавления аватара и отправки формы-->
<script src="/js/imagePreview.js"></script>
<script src="/js/header.js"></script>
<script src="/js/sendEditAuthorForm.js"></script>
</body>
</html>