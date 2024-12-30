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
                <a href="/main/authors" class="authors">Авторы</a>
                <span class="search">Поиск по сайту</span>
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

<#--макрос футера сайта-->
<#macro footer>
    <footer>

    </footer>
</#macro>