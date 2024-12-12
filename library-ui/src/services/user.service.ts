import httpClient from './http-client.service';

export const fetchUsers = async (): Promise<UserResponse[]> => {
  try {
    const response = await httpClient.get('/users');
    return response.data as UserResponse[];
  } catch (error) {
    console.error('Error fetching users:', error);
    throw new Error('Failed to fetch users');
  }
};

export const fetchNumberOfUsers = async (): Promise<number> => {
  try {
    const response = await httpClient.get('/users/count');
    return response.data as number;
  } catch (error) {
    console.error('Error fetching number of users:', error);
    throw new Error('Failed to fetch number of users');
  }
};

export const addUser = async (user: CreateUserRequest): Promise<void> => {
  try {
    await httpClient.post('/users', user);
  } catch (error) {
    console.error('Error adding user:', error);
    throw new Error('Failed to add user');
  }
};

export const updateUser = async (
  userId: number,
  data: UpdateUserRequest,
): Promise<UserResponse> => {
  try {
    const response = await httpClient.put(`/users/${userId}`, data);
    console.log(response.data);
    return response.data as UserResponse;
  } catch (error) {
    console.error(`Error updating user with ID ${userId}:`, error);
    throw new Error(`Failed to update user with ID ${userId}`);
  }
};

export const deleteUserById = async (id: number): Promise<void> => {
  try {
    await httpClient.delete(`/users/${id}`);
  } catch (error) {
    console.error(`Error deleting user with ID ${id}:`, error);
    throw new Error(`Failed to delete user with ID ${id}`);
  }
};
