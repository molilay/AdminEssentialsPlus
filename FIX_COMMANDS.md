# Исправление новых команд

Все новые команды нужно исправить - они используют неправильный базовый класс.

## Проблема:
Новые команды наследуются от FXCommand неправильно.

## Решение:
Изменить их на CommandExecutor + TabCompleter

## Команды для исправления:
1. TimeCommand
2. SpeedCommand  
3. RepairCommand
4. InvseeCommand
5. EnderChestCommand
6. ClearInventoryCommand
7. WarpCommand
8. SetWarpCommand
9. DelWarpCommand
10. WarpsCommand

## Исправлено:
✅ WeatherCommand

## Компиляция после исправления:
```bash
gradlew.bat clean shadowJar
```
