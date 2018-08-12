# Тестовое задание для трудоустройства в Soramitsu

## Задача
Необходимо написать мобильное приложение для Android, которое при открытии приложения загружает изображения с любого ресурса (например, https://www.flickr.com) и выводит списком в 2 столбца. Должна быть возможность поиска по картинкам. При нажатии на изображение — оно должно открываться в новом окне в полном размере. Использовать в работе обоснованную архитектуру + использовать инструмент DI.

### Уточнения
1. Ресурс вшит в мобильное приложение, т.е. универсальный парсинг не нужен.
2. Поиск по картинкам по тегу, а не по картике самой, как сделано в Google Images.

## Решение
**Архитектура:** MVP по Clean Architecture

**Unit тестирование:** всё, кроме Activity/Fragment и базовых/абстрактных/утилитарных классов

**Интеграционное тестирование:** от слоя данных (ответ сервера подставляется локальным файлом) до слоя презентационного (презентер)

**UI тестирование:** TODO

### Особенности решения
- Корректная работа в условиях смены конфигурации (например, поворот экрана)
- Проверка на наличие интернет соединения
- Оповещение пользователя об ошибках

### Использованные библиотеки/зависимости
* Android Support Libraries:
  - Constraint Layout
  - RecyclerView
  - CardView
  - Design
* Third-party libraries:
  - Dagger
  - Moxy
  - RxJava, RxAndroid
  - Retrofit
  - Picasso
* Kotlin
* Testing:
  - JUnit
  - Mockito, PowerMockito
