<#--макрос заголовка сайта-->
<#macro header>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <header>
        <div class="header_container">
            <div class="logo-block">
                <a href="/">
                    <img src="/img/logo.jpg" class="logo" alt="Логотип">
                    <h2 class="logo_text">Творчество православных христиан</h2>
                </a>
            </div>
            <div class="search-block">
                <a href="/main/authors" class="menu-link">Авторы</a>
                <a href="/main/review" class="menu-link">О проекте</a>
            </div>
            <#if known>
                <div class="user-data">
                    <button type="button" class="cabinet-button">
                        <img src="/upload/${authorDTO.pathToAvatar}" class="user-pic" alt="аватар пользователя"> ${authorDTO.firstName}
                    </button>
                    <div class="dropdown-box">
                        <a href="/cabinet">Личные данные</a>
                        <a href="/cabinet/poems">Мои стихи</a>
                        <a href="/cabinet/mock">Моя проза</a>
                        <a href="/cabinet/music">Моя музыка</a>
                        <a href="/cabinet/mock">Мои рисунки</a>
                        <a href="/logout">Выйти</a>
                    </div>
                </div>
            <#else>
                <div class="login-block">
                    <a href="/login" class="enter">Вход</a>
                    <a href="/registration" class="registration">Регистрация</a>
                </div>
            </#if>

            <#--        мобильное меню    -->
            <button class="header_burger_btn">
                <span></span><span></span><span></span>
            </button>
            <div class="dropdown_burger_box">
                <#if known>
                    <button type="button" class="cabinet-button-mobile">
                        <img src="/upload/${authorDTO.pathToAvatar}" class="user-pic" alt="аватар пользователя"> ${authorDTO.firstName}
                    </button>
                    <ul class="dropdown-box-mobile">
                        <li><a href="/cabinet">Личные данные</a></li>
                        <li><a href="/cabinet/poems">Мои стихи</a></li>
                        <li><a href="/cabinet/mock">Моя проза</a></li>
                        <li><a href="/cabinet/music">Моя музыка</a></li>
                        <li><a href="/cabinet/mock">Мои рисунки</a></li>
                        <li><a href="/logout">Выйти</a></li>
                    </ul>
                    <a href="/main/authors">Авторы</a>
                    <a href="/main/review">О проекте</a>
                <#else>
                    <a href="/login">Вход</a>
                    <a href="/registration">Регистрация</a>
                    <a href="/main/authors">Авторы</a>
                    <a href="/main/review">О проекте</a>
                </#if>
            </div>
        </div>
    </header>
</#macro>

<#macro cabinetButtons>
    <div class="left-sb">
        <ul>
            <li class="menu-item"><a href="/cabinet">Личные данные</a></li>
            <li class="menu-item"><a href="/cabinet/poems">Мои стихи</a></li>
            <li class="menu-item"><a href="/cabinet/mock">Моя проза</a></li>
            <li class="menu-item"><a href="/cabinet/music">Моя музыка</a></li>
            <li class="menu-item"><a href="/cabinet/mock">Мои рисунки</a></li>
        </ul>
    </div>
</#macro>

<#--макрос меню мобильного режима    -->
<#macro bottomMenu>
    <div class="bottom_menu">
        <a href="/cabinet"><img src="/img/defaultAva.png" class="bottom_menu_img" alt="Личные данные"></a>
        <a href="/cabinet/poems"><img src="/img/bottom_menu/poems.png" class="bottom_menu_img"></a>
        <a href="/cabinet/music"><img src="/img/bottom_menu/audio.png" class="bottom_menu_img"></a>
        <a href="/cabinet/mock"><img src="/img/bottom_menu/video.png" class="bottom_menu_img"></a>
    </div>
</#macro>

<#--макрос футера сайта-->
<#macro footer>
    <footer>

    </footer>
</#macro>