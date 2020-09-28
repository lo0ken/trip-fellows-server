# trip-fellows-server

## Регламент работы с гитом
Ветка `master` - релизная, в <b>неё не коммитим и не пушим ничего!</b> В неё будем сливать изменения вместе!
От мастер создаётся ветка `dev` - в неё будем сливать изменения из веток под задачи

### Регламент наименований

#### Наименование веток
task-{№ задачи}
Например: task-1

#### Наименование коммитов
{MODULE_NAME}: description
Например:
ORDER SERVICE: GET /api/order endpoint

### Алгоритм выполнения задачи
- Взяли задачу из трелло, к примеру под наименованием task-1
- Создали ветку от актуальной `dev`
  - `git checkout dev`
  - `git pull`
  - `git checkout -b task-1`
- Внесли изменения, проверили что нету лишних строк
- Делаем коммит
  - CTRL + K в ide
  - Ввести корректное сообщение к коммиту, к примеру MainActivity: hello word message
  - Commit
- делаем `push` из терминала
  - git push origin task-1
- Переходим по ссылке для создания пулл реквеста
  - в Reviewers выбираем всех участников
  - в Assigne ставим того тко будет мерджить
  - Отправляем ссылку на пулл-реквест в общий чат
