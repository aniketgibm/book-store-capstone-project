package com.bookstore.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onContinueAsGuest: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val loginState by viewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(loginState) {
        if (loginState is AuthState.Success) {
            viewModel.resetLoginState()
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        // Logo / Title
        Icon(
            imageVector = Icons.AutoMirrored.Filled.MenuBook,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "BookStore",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Your world of books",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Sign In",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = loginState is AuthState.Error
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = loginState is AuthState.Error
        )

        if (loginState is AuthState.Error) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = (loginState as AuthState.Error).message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Login button
        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            enabled = loginState !is AuthState.Loading
        ) {
            if (loginState is AuthState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Sign In", style = MaterialTheme.typography.titleMedium)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Continue as Guest
        OutlinedButton(
            onClick = {
                viewModel.continueAsGuest()
                onContinueAsGuest()
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Continue as Guest")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Register link
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account?", style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = onNavigateToRegister) {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hint for demo
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Demo Credentials", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(4.dp))
                Text("alice@bookstore.com / password123", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("bob@bookstore.com / password123", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
