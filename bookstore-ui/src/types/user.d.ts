type CreateUserRequest = {
  username: string;
  email: string;
  password: string;
};

type UpdateUserRequest = {
  username?: string;
  email?: string;
  password?: string;
};

interface UserResponse {
  id: number;
  username: string;
  email: string;
  roles: string[];
}

interface ApiResponse<T> {
  data: T;
  status: number;
  message: string;
}

interface UserProps {
  userId: number;
}

interface UserData {
  user: Ref<UserResponse | null>;
  isLoading: Ref<boolean>;
}

interface UserMethods {
  fetchUser(): Promise<void>;
}
