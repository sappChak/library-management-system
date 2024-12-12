import axios from 'axios';

const httpClient = axios.create({
  baseURL: 'http://localhost:8080/api',
});

httpClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default httpClient;
