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
        });
        router.push("/login");
    } catch (error: any) {
        errorMessage.value = error.message;
    } finally {
        isSubmitting.value = false;
    }
};
</script>

<style scoped>
/* Register Container */
.register-container {
    width: 400px;
    margin: 0 auto;
    padding: 30px;
    background-color: #fff;
    /* White background for the container */
    color: #000;
    /* Black text for readability */
    border-radius: 12px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    /* Soft shadow for depth */
    font-family: "Arial", sans-serif;
    text-align: center;
    /* Center-align text */
    position: absolute;
    top: 50%;
    /* Center vertically */
    left: 50%;
    /* Center horizontally */
    transform: translate(-50%, -50%);
    /* Adjust for centering */
}

/* Header */
h1 {
    margin-bottom: 20px;
    font-size: 26px;
    color: #000;
    /* Black text for header */
}

/* Form Group */
.form-group {
    margin-bottom: 20px;
    text-align: left;
}

/* Labels */
label {
    display: block;
    margin-bottom: 8px;
    font-weight: bold;
    color: #333;
    /* Dark gray color for labels */
}

/* Input Fields */
input {
    width: 100%;
    padding: 12px;
    font-size: 16px;
    border: 1px solid #ccc;
    /* Light gray border */
    border-radius: 6px;
    background-color: #fafafa;
    /* Light gray background for inputs */
    color: #000;
    /* Black text inside input fields */
    transition: all 0.3s ease;
}

/* Focused Input Fields */
input:focus {
    border-color: #000;
    /* Black border on focus */
    background-color: #fff;
    /* White background on focus */
    outline: none;
    box-shadow: 0 0 4px rgba(0, 0, 0, 0.4);
    /* Black shadow for focus */
}

/* Submit Button */
button {
    width: 100%;
    padding: 12px;
    font-size: 16px;
    font-weight: bold;
    color: #fff;
    background-color: #000;
    /* Black background */
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: all 0.3s ease;
}

button:hover {
    background-color: #333;
    /* Dark gray on hover */
}

button:disabled {
    background-color: #ccc;
    /* Light gray for disabled button */
    cursor: not-allowed;
}

/* Error Message */
.error-message {
    margin-top: 15px;
    font-size: 14px;
    color: #e74c3c;
    /* Red color for error messages */
}

/* Footer Links - Center the link and text */
p {
    margin-top: 20px;
    font-size: 18px;
    color: #333;
    /* Dark gray for footer text */
    font-weight: semi-bold;
}

p a {
    color: #000;
    /* Black links */
    text-decoration: none;
    font-weight: bold;
    font-size: 18px;
}

p a:hover {
    text-decoration: underline;
}

/* Center the footer text */
.register-container p {
    text-align: center;
    /* Center-align the text and the link */
    margin-top: 30px;
    /* Add space between form and the footer */
}

/* Add some space for responsiveness */
@media (max-width: 500px) {
    .register-container {
        width: 90%;
        padding: 20px;
    }

    h1 {
        font-size: 20px;
    }

    button {
        font-size: 14px;
    }

    p {
        font-size: 16px;
    }
}
</style>
