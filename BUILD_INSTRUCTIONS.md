# 🔨 Инструкции по сборке AdminEssentialsPlus

## 📋 Требования

- **Java 21+** (обязательно!)
- **Gradle 8.0+** (встроен в проект)

## 🚀 Быстрая сборка

### Windows:
```bash
gradlew.bat clean shadowJar
```

### Linux/Mac:
```bash
./gradlew clean shadowJar
```

### Или используйте задачу buildPlugin:
```bash
gradlew.bat buildPlugin
```

## 📦 Результат

Готовый JAR файл будет в:
```
target/AdminEssentialsPlus-1.1.0.jar
```

## 🎯 Установка на сервер

### Windows:
```bash
copy target\AdminEssentialsPlus-1.1.0.jar "C:\server\plugins\"
```

### Linux/Mac:
```bash
cp target/AdminEssentialsPlus-1.1.0.jar /path/to/server/plugins/
```

## 🔧 Дополнительные команды

### Очистка:
```bash
gradlew.bat clean
```

### Только компиляция:
```bash
gradlew.bat compileJava
```

### Проверка зависимостей:
```bash
gradlew.bat dependencies
```

### Список задач:
```bash
gradlew.bat tasks
```

## ✅ Проверка сборки

После сборки проверьте:
1. Файл `target/AdminEssentialsPlus-1.1.0.jar` существует
2. Размер файла > 1 MB
3. Внутри JAR есть `plugin.yml`

## 🐛 Решение проблем

### Ошибка "Java version":
Убедитесь что установлена Java 21:
```bash
java -version
```

### Ошибка "Permission denied":
На Linux/Mac дайте права:
```bash
chmod +x gradlew
```

### Ошибка компиляции:
Очистите кэш Gradle:
```bash
gradlew.bat clean --refresh-dependencies
```
