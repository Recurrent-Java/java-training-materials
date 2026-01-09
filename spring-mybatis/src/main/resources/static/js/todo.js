/**
 * ページ読み込み時のエントリーポイント
 * scriptタグの defer 属性により、DOM構築後に実行されます。
 */
const page = document.body.dataset.page;
if (page === "todo") {
  initTodo();
}

/*------------------------------------------------------------*
*  ToDoリストページの処理群
*-------------------------------------------------------------*/
/**
 * 1. 初期化処理（イベントリスナーの登録）
 */
function initTodo() {
  const addBtn = document.getElementById("add-task");
  const clearBtn = document.getElementById("clear-completed");
  const todoList = document.getElementById("todo-list");
console.log("initTodo called");
  // リスナー登録（オプショナルチェイニングで安全に）
  addBtn?.addEventListener("click", handleAddTask);
  clearBtn?.addEventListener("click", handleClearCompleted);

  // チェックボックスの変更（イベントデリゲート）
  todoList?.addEventListener("change", handleStatusChange);

  // 初期データの読み込み
  loadTodoList();
}

/**
 * 2. タスク追加の処理
 */
async function handleAddTask() {
  const taskInput = document.getElementById("task");
  const taskName = taskInput.value.trim();
  
  if (!taskName) {
    alert("タスクを入力してください");
    return;
  }

  try {
    const todo = await apiFetch("/spring-mybatis/api/todo/todo-task", {
      method: "POST",
      headers: { "Content-Type": "text/plain" },
      body: taskName
    });
    
    const todoList = document.getElementById("todo-list");
    todoList?.appendChild(createTodoElement(todo));
    taskInput.value = "";
  } catch (err) {
    console.error(err);
    alert(err.message);
  }
}

/**
 * 3. 完了タスクのクリア処理
 */
async function handleClearCompleted() {
  const todoList = document.getElementById("todo-list");
  if (!todoList) return;

  // 完了済みIDの抽出
  const completedIds = Array.from(todoList.querySelectorAll("li"))
    .filter(li => li.querySelector(".completed")?.checked)
    .map(li => li.querySelector('input[type="hidden"][name="id"]')?.value)
    .filter(id => id !== undefined);

  if (completedIds.length === 0) {
    alert("完了したタスクがありません");
    return;
  }

  try {
    await apiFetch('/spring-mybatis/api/todo/clear-completed', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(completedIds)
    });

    // 画面から削除
    completedIds.forEach(id => {
      todoList.querySelector(`input[value="${id}"]`)?.closest("li")?.remove();
    });
  } catch (err) {
    console.error(err);
    alert("削除に失敗しました");
  }
}

/**
 * 4. チェックボックス変更時の表示切り替え
 */
function handleStatusChange(e) {
  if (!e.target.classList.contains("completed")) return;
  
  const statusLabel = e.target.closest("ol")?.querySelector(".fontRed");
  if (statusLabel) {
    statusLabel.textContent = e.target.checked ? "完了" : "";
  }
}

/**
 * 5. 初期データ読み込み
 */
async function loadTodoList() {
  try {
    const todos = await apiFetch("/spring-mybatis/api/todo");
    const todoList = document.getElementById("todo-list");
    
    todos.forEach(todo => {
      todoList?.appendChild(createTodoElement(todo));
    });
  } catch (err) {
    console.error("データ読み込み失敗:", err);
  }
}

/**
 * 6. TODO要素の生成 (共通パーツ)
 */
function createTodoElement(todo) {
  const li = document.createElement("li");
  li.innerHTML = `
    <input type="hidden" name="id" value="${todo.id}">
    <ol>
      <li>
        <input type="checkbox" class="completed" ${todo.completed ? "checked" : ""}>
      </li>
      <li>${todo.taskName}</li>
      <li class="fontRed">${todo.completed ? "完了" : ""}</li>
    </ol>
  `;
  return li;
}
/*------------------------------------------------------------*
*  会員登録ページの処理群
*-------------------------------------------------------------*/
