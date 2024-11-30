<template>
    <div class="register-container">
        <h1>Create Account</h1>
        <form @submit.prevent="handleRegister">
            <div class="form-group">
                <label for="email">Email</label>
                <input v-model="email" type="email" id="email" placeholder="Enter your email" required />
            </div>
            <div class="form-group">
                <label for="username">Username</label>
                <input v-model="username" type="text" id="username" placeholder="Choose a username" required />
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input v-model="password" type="password" id="password" placeholder="Enter your password" required />
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password</label>
                <input v-model="confirmPassword" type="password" id="confirmPassword"
                    placeholder="Confirm your password" required />
            </div>
            <button type="submit" :disabled="isSubmitting">Register</button>
        </form>

        <div v-if="errorMessage" class="error-message">
            <p>{{ errorMessage }}</p>
        </div>

        <p>Already have an account? <router-link to="/login">Login</router-link></p>
    </div>
</template>

<script setup lang="ts">
import { useAuthStore } from "@/store/auth.store";
import { ref } from "vue";
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

const email = ref("");
const username = ref("");
const password = ref("");
const confirmPassword = ref("");
const isSubmitting = ref(false);
const errorMessage = ref("");

const handleRegister = async () => {
    isSubmitting.value = true;
    errorMessage.value = "";

    try {
        await authStore.register({
            email: email.value,
            username: username.value,
            password: password.value,
            confirmPassword: confirmPassword.value
        }
        );
        router.push("/login");
    } catch (error: any) {
        errorMessage.value = error.message;
    } finally {
        isSubmitting.value = false;
    }
};
</script>

<style scoped></style>
