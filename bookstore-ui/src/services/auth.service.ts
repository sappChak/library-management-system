import { LoginCredentials, RegisterCredentials } from '@/types';
import httpClient from './http-client.service';

export const login = async (credentials: LoginCredentials): Promise<string> => {
  const response = await httpClient.post('/auth/login', credentials);
  return response.data.accessToken;
};

export const register = async (
  credentials: RegisterCredentials,
): Promise<void> => {
  const response = await httpClient.post('/auth/register', credentials);
  return response.data.accessToken;
};

export const getUserProfile = async (): Promise<UserResponse> => {
  const response = await httpClient.get('/users/me');
  return response.data;
};
