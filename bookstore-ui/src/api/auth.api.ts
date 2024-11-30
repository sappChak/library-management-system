import { LoginCredentials, RegisterCredentials, User } from "@/types";
import httpClient from "./http-client";

export const login = async (credentials: LoginCredentials): Promise<string> => {
  const response = await httpClient.post("/auth/login", credentials);
  return response.data.access_token;
};

export const register = async (
  credentials: RegisterCredentials,
): Promise<void> => {
  await httpClient.post("/auth/register", credentials);
};

export const getUserProfile = async (): Promise<User> => {
  const response = await httpClient.get("/users/me");
  return response.data;
};
