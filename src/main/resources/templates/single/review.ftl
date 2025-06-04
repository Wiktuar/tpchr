<#import "../common.ftl" as c>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/single/review.css">
    <link rel="stylesheet" href="/css/modal.css">
    <title>Страница отзывов</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>
    <#--блок с основным контентом-->
    <div class="container">
        <div class="author">
            <img src="/img/myPhoto.jpg" class="my-photo" alt="Фото автора сайта">
            <div>
                <h3 class="appeal">Дорогие посетители сайта!</h3>
                <p class="about_author">Всем привет! Меня зовут Виктор. Я руководитель и творческий вдохновитель
                    проекта "Творчество православных христиан". Сейчас мой проект на самом старте своего существования. Многое уже сделано,
                    и еще больше предстоит сделать. Если вы пишите стихи или музыку, а, может то и другое, присоединяйтесь к нам. Становитесь авторами.
                    Вместе мы сожем донести православную поэзию и музыку до многих людей и сделать этот мир прекраснее. Вы также можете написать мне отзыв или
                    пожелание. Я буду очень рад Вашей обратной связи.
            </div>
        </div>
        <h3>Напишите, пожалуйста, Ваш отзыв о нашем проекте или пожелание, как можно его улучшить</h3>
        <form id="review_form" method="post" action="/main/savereview">
            <div class="fName">
                <label for="firstName">Пожалуйста, укажите Ваше имя</label>
                <input type="text" id="firstName" name="name" placeholder="Ваше имя" value="<#if name??>${name}</#if>" data-text>
            </div>
            <div class="fName">
                <label for="email">Пожалуйста, сообщите нам Вашу почту, чтобы мы могли при необходимости с Вами связаться</label>
                <input type="email" id="email" name="email" placeholder="Ваша почта" value="<#if name??>${email}</#if>" data-text>
            </div>
            <div class="fName">
                <label for="header">Укажите тему Вашего сообщения</label>
                <input type="text" id="header" name="header" placeholder="Заголовок сообщения" data-text>
            </div>
            <div class="fName">
                <label for="text">Ваше сообщение:</label>
                <textarea id="text" name="text" maxlength="2000" placeholder="Всего 2000 символов" data-text></textarea>
            </div>
            <button type="submit" class="review_btn">Отправить</button>
        </form>
    </div>

    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>

<#-- Скрипты, относящиеся к модальному окну -->
<script src="/js/modal/base.js"></script>
<script src="/js/modal/plugins/modal.js"></script>
<script src="/js/modal/attention.js"></script>

<script src="/js/header.js"></script>
<script type="module" src="/js/review.js"></script>
</body>
</html>