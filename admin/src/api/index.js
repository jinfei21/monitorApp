import axios from 'axios'
import services from './services'

axios.defaults.timeout = 5000;
// axios.defaults.baseURL = 'http://127.0.0.1:1200/';
axios.defaults.baseURL = process.env.NODE_ENV !== 'production' ? '' : '';
axios.defaults.headers.common['Authorization'] = "31313113";
axios.defaults.headers.post['Content-Type'] = 'application/json';
axios.defaults.headers.put['Content-Type'] = 'application/json';



export const api = services;


