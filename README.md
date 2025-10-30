
# 📅 Schedule App

Приложение расписание колледжа на **Jetpack Compose!**

[Rustore](https://www.rustore.ru/catalog/app/ru.xaori.schedule)

## 🔧 Запуск проекта

### 📱 Android

Собрать и запустить на эмуляторе/устройстве в Android Studio

## 📂 Структура проекта

```
ru.xaori.schedule/ 
├─ core/             // Общие модули приложения например UiState
├─ data/             // Слой данных, источники данных
│  ├─ api/           // Все что связано с api
│  ├─ repository/    // Реализации репозиториев
│  ├─ storage/       // Хранение даннх
│  ├─ di.kt          // Модуль иньекции зависимостей
├─ domain/           // Слой бизнес логики
│  ├─ model/         // DTO
│  ├─ repository/    // Интерфесы репозиториев
│  ├─ usecase/       // Юзкесы
│  ├─ di.kt          // Модуль иньекции зависимостей
├─ presentation/     // Слой пользовательского интерфейса
│  ├─ common/        // Общие компоненты
│  ├─ navigation/    // Навигация
│  ├─ screen/        // Экраны и специфичные для экранов компоненты
│  ├─ state/         // Состояния для отдельных экранов и компонентов
│  ├─ ui/            // Тема, Цвет, Типография, Анимации 
│  ├─ utils/         // Функции утилиты(Возможно стоит перенести в domain)
│  ├─ viewmodel/     // Модели представления данных
│  ├─ di.kt          // Модуль иньекции зависимостей
├─ App.kt            // Приложение для инициализации Koin
├─ di.kt             // Общим модуль иньекции зависмостей
├─ MainActivity.kt   // Главный активити

```

## 🛠 Используемые технологии

- [Kotlin Multiplatform](https://kotlinlang.org/lp/multiplatform/)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Ktor](https://ktor.io/)
- [Koin](https://insert-koin.io/)

## 📄 Лицензия

Этот проект распространяется под лицензией MIT. Подробности см. в LICENSE.a
