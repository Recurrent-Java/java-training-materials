/*------------------------------------------------------------*
*  ページ共通の処理群
*-------------------------------------------------------------*/
/**
 * 1. 共通APIクライアント (Fetchの共通化)
 */
async function apiFetch(url, options = {}) {
  const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;

  const config = {
    // ...optionsはoptionに入っている全てのプロパティ(method, body など)を
    // configオブジェクトに展開コピーする
    ...options,   
    headers: {
      [csrfHeader]: csrfToken,
      // options.headersが存在する場合はそれも展開コピーする
      ...options.headers,
    },
  };

  const response = await fetch(url, config);

  // response.ok チェックの共通化
  if (!response.ok) {
    const errorDetail = await response.json().catch(() => ({}));
    throw new Error(errorDetail.message || `通信エラーが発生しました (${response.status})`);
  }

  return response.status === 204 ? null : response.json();
}