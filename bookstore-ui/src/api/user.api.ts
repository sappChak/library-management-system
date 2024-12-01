import { User } from "@/types";
import httpClient from "./http-client";

interface CreateUserRequest {
  username: string;
  email: string;
  password: string;
}

export const fetchUsers = async (): Promise<User[]> => {
  const response = await httpClient.get("/users");
  const users = await response.data;
  return users;
};

export const fetchNumberOfUsers = async (): Promise<number> => {
  const response = await httpClient.get("/users/count");
  const count = await response.data;
  return count;
};

export const addUser = async (user: CreateUserRequest): Promise<void> => {
  await httpClient.post("/users", user);
};
