/*
  Beginner-friendly vanilla JS app.

  How it works:
  - Loads employees from backend via fetch
  - Renders them in a table
  - Uses the same form for Add + Edit
  - Shows simple success/error messages
*/

// API_BASE_URL is defined in config.js
// Example: http://localhost:8080/api

const employeesTbody = document.getElementById("employeesTbody");
const emptyState = document.getElementById("emptyState");
const messageBox = document.getElementById("message");
const apiBaseEl = document.getElementById("apiBase");

const form = document.getElementById("employeeForm");
const formTitle = document.getElementById("formTitle");
const submitBtn = document.getElementById("submitBtn");
const cancelEditBtn = document.getElementById("cancelEditBtn");

const employeeIdEl = document.getElementById("employeeId");
const firstNameEl = document.getElementById("firstName");
const lastNameEl = document.getElementById("lastName");
const emailEl = document.getElementById("email");
const departmentEl = document.getElementById("department");
const salaryEl = document.getElementById("salary");

const searchInput = document.getElementById("searchInput");
const refreshBtn = document.getElementById("refreshBtn");

apiBaseEl.textContent = API_BASE_URL;

let employeesCache = [];

function showMessage(text, type) {
  // type: "success" | "error"
  messageBox.textContent = text;
  messageBox.className = `message ${type}`;
  messageBox.hidden = false;

  // Auto-hide after a short time
  window.clearTimeout(showMessage._t);
  showMessage._t = window.setTimeout(() => {
    messageBox.hidden = true;
  }, 3500);
}

function clearMessage() {
  messageBox.hidden = true;
}

function sanitize(str) {
  // Simple HTML escaping to avoid inserting raw HTML into the table.
  return String(str)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&#039;");
}

function formatSalary(value) {
  const n = Number(value);
  if (Number.isNaN(n)) return "-";
  return n.toLocaleString(undefined, { style: "currency", currency: "USD" });
}

function resetFormToAdd() {
  employeeIdEl.value = "";
  formTitle.textContent = "Add Employee";
  submitBtn.textContent = "Add Employee";
  cancelEditBtn.hidden = true;
  form.reset();
}

function setFormToEdit(emp) {
  employeeIdEl.value = emp.id;
  firstNameEl.value = emp.firstName;
  lastNameEl.value = emp.lastName;
  emailEl.value = emp.email;
  departmentEl.value = emp.department;
  salaryEl.value = emp.salary;

  formTitle.textContent = `Edit Employee #${emp.id}`;
  submitBtn.textContent = "Update Employee";
  cancelEditBtn.hidden = false;

  // Scroll to top so user sees the form
  window.scrollTo({ top: 0, behavior: "smooth" });
}

function getFilteredEmployees() {
  const q = searchInput.value.trim().toLowerCase();
  if (!q) return employeesCache;

  return employeesCache.filter((e) => {
    const full = `${e.firstName} ${e.lastName}`.toLowerCase();
    return full.includes(q);
  });
}

function renderEmployees() {
  const employees = getFilteredEmployees();

  employeesTbody.innerHTML = "";

  if (!employees.length) {
    emptyState.hidden = false;
    return;
  }

  emptyState.hidden = true;

  for (const e of employees) {
    const tr = document.createElement("tr");

    tr.innerHTML = `
      <td>${sanitize(e.id)}</td>
      <td>${sanitize(e.firstName)}</td>
      <td>${sanitize(e.lastName)}</td>
      <td>${sanitize(e.email)}</td>
      <td>${sanitize(e.department)}</td>
      <td class="num">${sanitize(formatSalary(e.salary))}</td>
      <td>
        <div class="row-actions">
          <button class="btn btn-secondary" data-action="edit" data-id="${sanitize(e.id)}">Edit</button>
          <button class="btn btn-danger" data-action="delete" data-id="${sanitize(e.id)}">Delete</button>
        </div>
      </td>
    `;

    employeesTbody.appendChild(tr);
  }
}

async function apiRequest(path, options = {}) {
  const url = `${API_BASE_URL}${path}`;

  const res = await fetch(url, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {}),
    },
    ...options,
  });

  // Try to read JSON error message if provided
  let body = null;
  const contentType = res.headers.get("content-type") || "";
  if (contentType.includes("application/json")) {
    body = await res.json().catch(() => null);
  } else {
    body = await res.text().catch(() => null);
  }

  if (!res.ok) {
    const msg =
      (body && body.message) ||
      (typeof body === "string" && body) ||
      `Request failed (${res.status})`;
    throw new Error(msg);
  }

  return body;
}

async function loadEmployees() {
  clearMessage();
  try {
    const data = await apiRequest("/employees", { method: "GET" });
    employeesCache = Array.isArray(data) ? data : [];
    renderEmployees();
  } catch (err) {
    showMessage(`Failed to load employees: ${err.message}`, "error");
  }
}

function validateForm() {
  const firstName = firstNameEl.value.trim();
  const lastName = lastNameEl.value.trim();
  const email = emailEl.value.trim();
  const department = departmentEl.value.trim();
  const salary = Number(salaryEl.value);

  if (firstName.length < 2 || firstName.length > 50) {
    throw new Error("First name must be 2-50 characters.");
  }
  if (lastName.length < 2 || lastName.length > 50) {
    throw new Error("Last name must be 2-50 characters.");
  }
  if (!email.includes("@") || email.length > 120) {
    throw new Error("Please enter a valid email.");
  }
  if (department.length < 2 || department.length > 80) {
    throw new Error("Department must be 2-80 characters.");
  }
  if (Number.isNaN(salary) || salary < 0) {
    throw new Error("Salary must be a number greater than or equal to 0.");
  }

  return { firstName, lastName, email, department, salary };
}

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  try {
    const payload = validateForm();
    const id = employeeIdEl.value;

    if (!id) {
      // Create
      await apiRequest("/employees", {
        method: "POST",
        body: JSON.stringify(payload),
      });
      showMessage("Employee created successfully.", "success");
    } else {
      // Update
      await apiRequest(`/employees/${encodeURIComponent(id)}`, {
        method: "PUT",
        body: JSON.stringify(payload),
      });
      showMessage("Employee updated successfully.", "success");
    }

    resetFormToAdd();
    await loadEmployees();
  } catch (err) {
    showMessage(err.message, "error");
  }
});

cancelEditBtn.addEventListener("click", () => {
  resetFormToAdd();
  showMessage("Edit canceled.", "success");
});

refreshBtn.addEventListener("click", () => {
  loadEmployees();
});

searchInput.addEventListener("input", () => {
  renderEmployees();
});

employeesTbody.addEventListener("click", async (e) => {
  const btn = e.target.closest("button");
  if (!btn) return;

  const action = btn.dataset.action;
  const id = btn.dataset.id;

  if (!action || !id) return;

  if (action === "edit") {
    const emp = employeesCache.find((x) => String(x.id) === String(id));
    if (!emp) {
      showMessage("Employee not found in table (try refresh).", "error");
      return;
    }
    setFormToEdit(emp);
    return;
  }

  if (action === "delete") {
    const ok = window.confirm(`Delete employee #${id}?`);
    if (!ok) return;

    try {
      await apiRequest(`/employees/${encodeURIComponent(id)}`, {
        method: "DELETE",
      });
      showMessage("Employee deleted successfully.", "success");
      resetFormToAdd();
      await loadEmployees();
    } catch (err) {
      showMessage(`Delete failed: ${err.message}`, "error");
    }
  }
});

// Initial load
resetFormToAdd();
loadEmployees();
