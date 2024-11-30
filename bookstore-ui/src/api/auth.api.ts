import { User } from "@/types";
import httpClient from "./http-client";

export const login = async (
  username: string,
  password: string,
): Promise<string> => {
  const response = await httpClient.post("/auth/login", { username, password });
  return response.data.access_token;
};

export const register = async (
  email: string,
  username: string,
  password: string,
): Promise<void> => {
  await httpClient.post("/auth/register", { email, username, password });
};

export const getProfile = async (): Promise<User> => {
  const response = await httpClient.get("/users/me");
  return response.data;
};
