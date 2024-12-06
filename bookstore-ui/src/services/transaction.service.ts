import { Transaction } from '@/types/transaction';
import httpClient from './http-client.service';

export const fetchTransactions = async (): Promise<Transaction[]> => {
  const response = await httpClient.get('/transactions');
  const transactions = await response.data;
  return transactions;
};

export const fetchMyRecentTransactions = async (): Promise<Transaction[]> => {
  const response = await httpClient.get('/transactions/me');
  const transactions = await response.data;
  return transactions;
};

export const fetchNumberOfActiveBorrows = async (): Promise<number> => {
  const response = await httpClient.get('/transactions/active/count');
  const count = await response.data;
  return count;
};

export const fetchNumberOfReturns = async (): Promise<number> => {
  const response = await httpClient.get('/transactions/returned/count');
  const count = await response.data;
  return count;
};
