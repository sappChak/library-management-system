<template>
    <div>
        <h2>Register</h2>
        <form @submit.prevent="handleRegister">
            <input v-model="email" type="email" placeholder="Email" />
            <input v-model="username" type="text" placeholder="Username" />
            <input v-model="password" type="password" placeholder="Password" />
            <input v-model="confirmPassword" type="password" placeholder="Confirm Password" />
            <button type="submit">Register</button>
        </form>
    </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from "@/store/auth.store";

export default {
    setup() {
        const email = ref('');
        const username = ref('');
        const password = ref('');
        const confirmPassword = ref('');
        const authStore = useAuthStore();
        const router = useRouter();

        const handleRegister = async () => {
            try {
                await authStore.register(email.value, username.value, password.value);
                router.push('/login');
            } catch (error: any) {
                console.error('Registration failed', error.response.data.message);
            }
        };

        return { email, username, password, confirmPassword, handleRegister };
    },
};
</script>
