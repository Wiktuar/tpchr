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
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/modal.css">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <title>Регистрация на сайте</title>
</head>
<body>
<div class="wrapper">
    <#--pаголовок сайта-->
    <@c.header></@c.header>

    <div class="container">
        <h3>Для того, что стать автором на сайте, пожалуйста, заполните эту форму</h3>
    <#-- параметры формы method и action задаются через js -->
        <form id="regForm">
            <div class="flex-container">
                <div class="flex-wrapper">
                    <div class="fName">
                        <label for="firstName">Пожалуйста, укажите Ваше имя</label>
                        <input type="text" id="firstName" name="firstName" placeholder="Ваше имя" required>
                    </div>
                    <div class="fName">
                        <label for="lastName">Пожалуйста, укажите Вашу фамилию</label>
                        <input type="text" id="lastName" name="lastName" placeholder="Ваша фамилия">
                    </div>
                    <div class="fName">
                        <label for="email">Пожалуйста, сообщите нам Вашу почту</label>
                        <input type="email" id="email" name="email" placeholder="Ваша почта">
                    </div>
                    <div class="fName">
                        <label for="password">Придумайте пароль</label>
                        <input type="password" id="password" name="password" placeholder="пароль">
                        <span class="showPassword" data-target="password" ></span>
                    </div>
                    <div class="fName">
                        <label for="confirm_password">Повторите пароль еще раз</label>
                        <input type="password" id="confirm_password" name="passwordConfirm" placeholder="повторите пароль">
                        <span class="showPassword" data-target="confirm_password"></span>
                    </div>
                    <input type="hidden" id="inp_img" name="pathToAvatar" value="defaultAva.png">
                </div>

                <div class="flex-wrapper">
                    <#-- Блок добавления фотографии -->
                    <div class="image-wrapper">
                        <div class="fileInput">
                            <p>Добавьте аватар, чтобы Вас узнавали на улице</p>
                            <label for="file">Загрузите картинку для аватарки</label>
                            <input type="file"  id="file" name="file" accept="image/*">
                        </div>  <!-- end of .fileInput -->
                        <div class="imgPreview">
                            <img src="/img/defaultAva.png" class="imageAva" alt="Ваша аватарка">
                        </div> <#-- end of .imgPreview -->
                    </div>  <#--end of .image-wrapper -->
                </div> <#--end of .flex-wrapper -->
            </div> <#--end of .flex-container -->
            <div class="g-recaptcha" data-sitekey = "6LfWK9snAAAAANq4m051wQ-VPZP3z9UA_4ERO9Sj"></div>

            <#-- Блок добавления социальных сетей -->
            <div class="button">
                <input type="button"  value="Дополнительно" class="additionalBtn" />
            </div>
            <div class="additional">
                <div class="about_me">
                    <p class="about_me_header">Расскажите немного о себе. Где вы живете? Чем увлекаетесь? Что вдохновляет Вас на творчество? Что для Вас Православие? и т.д.</p>
                    <textarea name="description" maxlength="2000" placeholder="Всего 2000 символов"></textarea>
                </div>
                <uL class="social-box">
                    <li class="sc-block">
                        <label for="vk">Добавьте Вашу страницу Вконтакте</label>
                        <input type="text" id="vk" name="vk" placeholder="Ваша страница Вконтакте">
                    </li>
                    <li class="sc-block">
                        <label for="tg">Добавьте Ваш аккаунт в Telegram</label>
                        <input type="text" id="tg" name="tg" placeholder="Ваш аккаунт в  Telegram">
                    </li>
                    <li class="sc-block">
                        <label for="yt">Добавьте Ваш канал на youTube</label>
                        <input type="text" id="yt" name="yt" placeholder="Ваш канал на youtube">
                    </li>
                    <li class="sc-block">
                        <label for="rt">Добавьте Ваш канал на RuTube</label>
                        <input type="text" id="rt" name="rt" placeholder="Ваш канал на rutube">
                    </li>
                </uL>
            </div>

            <input type="submit" id="regBtn" class="regBtn" value="Отправить данные">
        </form>
    </div> <#--end of .container -->
    <#--блок добавления футера-->
    <@c.footer></@c.footer>
</div>

<#-- Скрипты, относящиеся к модальному окну -->
<script src="/js/modal/base.js"></script>
<script src="/js/modal/plugins/modal.js"></script>
<script src="/js/modal/index.js"></script>
<script src="/js/modal/attention.js"></script>
<#--Скрипты добавления аватара и социальных сетей-->
<script src="/js/imagePreview.js"></script>
<script src="/js/socialBox.js"></script>
<#--Скрипты отправки формы-->
<script src="/js/registerControl.js"></script>
<script src="/js/sendForm.js"></script>
</body>
</html>