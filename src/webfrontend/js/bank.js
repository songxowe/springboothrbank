// 前端访问后端服务器的路径
const BANK_URL = 'http://127.0.0.1:8086/api/'
const TOKEN_KEY = "jwtToken"

function getJwtToken() {
  return localStorage.getItem(TOKEN_KEY);
}

function setJwtToken(token) {
  localStorage.setItem(TOKEN_KEY, token);
}

function removeJwtToken() {
  localStorage.removeItem(TOKEN_KEY);
}

function createAuthorizationTokenHeader() {
  var token = getJwtToken();
  if (token) {
    return {"Authorization": "Bearer " + token};
  } else {
    return {};
  }
}

/**
 * 快速的将 form 表单数据转为 json 格式
 * @param $form
 */
function getFormData($form) {
  var unindexed_array = $form.serializeArray()
  var indexed_array = {}

  $.map(unindexed_array, (n, i) => {
    indexed_array[n['name']] = n['value']
  })

  return indexed_array
}