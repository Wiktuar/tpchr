<#import "../common.ftl" as c>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/css/reset.css">
    <link rel="stylesheet" href="/css/common/common.css">
    <link rel="stylesheet" href="/css/cabinet/loginPage.css"">
    <link rel="stylesheet" href="/css/modal.css">
    <title>Вход на сайт</title>
</head>
<body>
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <div class="container">
        <#-- Форма логирования в личном кабинете    -->
        <div class="wrapLoginForm">
            <H2 class="greeting">Добро пожаловать!</H2>
            <div class="authenticationWarning">
                <p class="authMessage"></p>
                <div class="repeatBtn">Отправить письмо повторно</div>
            </div>
            <form method="post" action="/login">
                <div class="fName">
                    <label for="username">Ваша почта:</label>
                    <input type="email" id="username" name="username" value="${username?has_content?then(username, "")}" autofocus/>
                </div>
                <div class="fName">
                    <label for="password">Ваш пароль:</label>
                    <input type="password" id="password" name="password" value="${password?has_content?then(password, "")}"/>
                    <span class="showPassword" data-target="password"></span>
                </div>
                <div class="remember">
                    <input id="remember" type="checkbox" name="remember-me">
                    <label for="remember">Запомнить меня</label>
                </div>
                <button type="submit" id="sendLoginForm">Войти на сайт</button>
             </form>
        </div> <#-- end wrapLoginForm -->

    <#--Блок напоминания пароля-->
        <div class="remindEmail unVisible">
            <form method="post" action="/reset/remindPassword" class="remindForm">
                <label for="email">Укажите Вашу почту для восстановления пароля<br></label>
                <input type="email" id="email" name="email">
                <button type="submit" class="remindBtn">Напомнить пароль</button>
            </form>
        </div><#-- end remindEmmail -->
        <#--атрибут "href" удален, поскольку при пустом его значении не отображается окно напоминания пароля -->
        <a class="loginBtn">Я забыл(-а) пароль</a>
        <#-- этот блок появляется в сообщении authenticationWarning, когда пользователь не активировал аккаунт        -->
    </div>

    <#--блок добавления футера-->
    <@c.footer></@c.footer>

<#-- Скрипты, относящиеся к модальному окну -->
<script src="/js/modal/base.js"></script>
<script src="/js/modal/plugins/modal.js"></script>
<script src="/js/modal/attention.js"></script>

<#-- Скрипты, относящиеся к отправке запроса на восстановление пароля   -->
<script src="/js/loginPage/loginPage.js"></script>
<script src="/js/loginPage/remindPassword.js"></script>
<script src="/js/loginPage/repeatActivationCode.js"></script>

<script src="/js/header.js"></script>
</body>
</html>