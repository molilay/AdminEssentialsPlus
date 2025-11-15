# 🚀 Загрузка на GitHub

## 📋 Подготовка

Плагин готов к загрузке на GitHub!

---

## 🔧 Шаги для загрузки

### 1. Инициализация Git

```bash
git init
git add .
git commit -m "Initial commit: AdminEssentialsPlus v1.0.0"
```

### 2. Создание репозитория на GitHub

1. Перейдите на https://github.com/new
2. Название: `AdminEssentialsPlus`
3. Описание: `Advanced admin tools for Minecraft servers with AI support`
4. Публичный репозиторий
5. Не добавляйте README, .gitignore, LICENSE (уже есть)

### 3. Подключение к GitHub

```bash
git remote add origin https://github.com/YOUR_USERNAME/AdminEssentialsPlus.git
git branch -M main
git push -u origin main
```

### 4. Создание релиза

1. Перейдите в Releases
2. Нажмите "Create a new release"
3. Tag: `v1.0.0`
4. Title: `AdminEssentialsPlus v1.0.0`
5. Описание: см. ниже
6. Прикрепите `target/AdminEssentialsPlus-1.0.0.jar`
7. Опубликуйте

---

## 📝 Описание релиза

```markdown
# AdminEssentialsPlus v1.0.0

Первый стабильный релиз!

## ✨ Возможности

- 🎮 84+ команд для управления сервером
- 🤖 AI ассистент с облачной поддержкой
- 🎨 GUI система с красивыми меню
- 📝 Полное логирование действий
- 🗺️ Готовые менеджеры (Warps, Cooldowns, ActionLogger)
- 📊 База данных SQLite
- 🔧 Полная конфигурация

## 📦 Установка

1. Скачайте `AdminEssentialsPlus-1.0.0.jar`
2. Поместите в папку `plugins/`
3. Перезапустите сервер
4. Настройте `config.yml` и `ai-config.yml`

## 📋 Требования

- Paper/Spigot 1.20.4+
- Java 17+

## 📚 Документация

См. [README.md](README.md) для полной документации.

## 🐛 Известные проблемы

Нет критических багов.

## 🔮 Планы

- Интеграция WarpManager
- Интеграция CooldownManager
- Расширенное логирование
- Больше AI функций
```

---

## 🎯 Настройка GitHub Actions

GitHub Actions уже настроен! Файл `.github/workflows/build.yml` автоматически:
- Собирает проект при каждом push
- Запускает тесты
- Создает артефакты

---

## 📊 Badges для README

Добавьте в начало README.md:

```markdown
[![Build](https://github.com/YOUR_USERNAME/AdminEssentialsPlus/actions/workflows/build.yml/badge.svg)](https://github.com/YOUR_USERNAME/AdminEssentialsPlus/actions)
[![Release](https://img.shields.io/github/v/release/YOUR_USERNAME/AdminEssentialsPlus)](https://github.com/YOUR_USERNAME/AdminEssentialsPlus/releases)
[![Downloads](https://img.shields.io/github/downloads/YOUR_USERNAME/AdminEssentialsPlus/total)](https://github.com/YOUR_USERNAME/AdminEssentialsPlus/releases)
[![License](https://img.shields.io/github/license/YOUR_USERNAME/AdminEssentialsPlus)](LICENSE)
```

---

## 🔒 .gitignore

Уже настроен! Исключает:
- `target/` - Скомпилированные файлы
- `.idea/` - IntelliJ IDEA
- `*.iml` - IntelliJ модули
- `.vscode/` - VS Code
- `*.jar` (кроме релизов)
- Временные файлы

---

## 📞 После загрузки

1. **Добавьте Topics:**
   - minecraft
   - minecraft-plugin
   - paper
   - spigot
   - admin-tools
   - ai
   - bukkit

2. **Настройте About:**
   - Website: Ваш сайт
   - Topics: См. выше
   - Description: Advanced admin tools for Minecraft servers with AI support

3. **Создайте Wiki:**
   - Документация
   - Примеры использования
   - FAQ

4. **Настройте Issues:**
   - Bug report template
   - Feature request template

---

## 🎉 Готово!

Ваш плагин теперь на GitHub и доступен всем!

**URL:** `https://github.com/YOUR_USERNAME/AdminEssentialsPlus`

---

## 💡 Дополнительно

### Автоматические релизы

Создайте `.github/workflows/release.yml` для автоматических релизов при создании тега.

### Документация

Используйте GitHub Pages для хостинга документации.

### Сообщество

- Включите Discussions для общения
- Настройте Code of Conduct
- Добавьте Contributing guidelines

---

**Удачи с проектом!** 🚀
