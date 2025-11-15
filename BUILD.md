# 🔨 Сборка плагина

## Быстрая сборка

```bash
gradlew clean build
```

Результат: `build/libs/AdminEssentialsPlus-1.1.0.jar`

## Требования

- Java 21+ (установите с https://adoptium.net/)
- Gradle (встроен в проект)

## Команды

```bash
gradlew clean          # Очистка
gradlew build          # Сборка
gradlew shadowJar      # Создание JAR
gradlew tasks          # Список задач
```

## Проблемы

### Java не найдена
Установите Java 21 и добавьте в PATH

### Permission denied (Linux/Mac)
```bash
chmod +x gradlew
```

## Установка

```bash
copy build\libs\AdminEssentialsPlus-1.1.0.jar "C:\server\plugins\"
```

**Версия:** 1.1.0  
**Java:** 21  
**Paper:** 1.21.3
