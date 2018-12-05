import axios from 'axios'
import qs from 'qs'
import lodash from 'lodash'

const fetch = (options) => {
  let {
    method = 'get',
    data,
    fetchType,
    url,
  } = options


  switch (method.toLowerCase()) {
    case 'get':
      return axios.get(`${url}${!lodash.isEmpty(data) ? `?${qs.stringify(data)}` : ''}`);
    case 'delete':
      return axios.delete(url,{"headers":{"jwt-token":localStorage.getItem("jwt-token")}} );
    case 'head':
      return axios.head(url, {"headers":{"jwt-token":localStorage.getItem("jwt-token")}});
    case 'post':
      return axios.post(url, data,{"headers":{"jwt-token":localStorage.getItem("jwt-token")}});
    case 'put':
      return axios.put(url, data);
    case 'patch':
      return axios.patch(url, data,{"headers":{"jwt-token":localStorage.getItem("jwt-token")}});
    default:
      return axios(options)
  }
}

export default function request (options) {

  return fetch(options).then((response) => {
    const { statusText, status } = response;
    let data = response.data;

    return {
      success: true,
      message: statusText,
      status,
      ...data,
    }
  }).catch((error) => {
    const { response } = error

    let message
    let status
    if (response) {
      status = response.status
      const { data, statusText } = response
      message = data.message || statusText
    } else {
      status = 600
      message = 'Network Error'
    }
    return { success: false, status, message }
  })
}
