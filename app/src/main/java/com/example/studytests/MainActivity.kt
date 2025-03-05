package com.example.studytests

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.studytests.components.button.DSButton
import com.example.studytests.components.button.DSButtonData
import com.example.studytests.components.button.styles.ButtonType
import com.example.studytests.components.button.styles.DSButtonStyleDefaults
import com.example.studytests.components.button.styles.data.DSButtonColors
import com.example.studytests.components.shimmer.shimmer
import com.example.studytests.ui.theme.StudyTestsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Column {
                    ExampleDSButtons()
                }
            }
        }
    }

    @Composable
    private fun ExampleDSButtons() {
        DSButton(
            config = DSButtonData(
                text = "Enviar",
                type = ButtonType.Primary,
                onClick = { println("Botão Primário clicado!") }
            )
        )

        DSButton(
            config = DSButtonData(
                text = "Cancelar",
                type = ButtonType.Secondary,
                onClick = { println("Botão Secundário clicado!") }
            )
        )

        DSButton(
            config = DSButtonData(
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar") },
                type = ButtonType.Icon,
                onClick = { println("Botão Ícone clicado!") }
            )
        )

        DSButton(
            config = DSButtonData(
                text = "Salvar",
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Send,
                        contentDescription = "Salvar"
                    )
                },
                type = ButtonType.Action,
                onClick = { println("Botão Action clicado!") }
            )
        )

        DSButton(
            config = DSButtonData(
                text = "Próximo",
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Avançar"
                    )
                },
                type = ButtonType.Navigation,
                onClick = { println("Botão Navigation clicado!") }
            )
        )

        DSButton(
            config = DSButtonData(
                text = "Confirmar",
                type = ButtonType.Sealed,
                onClick = { println("Botão Sealed clicado!") }
            )
        )

        DSButton(
            config = DSButtonData(
                type = ButtonType.Piled,
                onClick = { println("Botão Piled clicado!") }
            )
        )


        val customTheme = DSButtonStyleDefaults.Default.copy(
            colors = DSButtonStyleDefaults.Default.colors.copy(
                primary = DSButtonColors(
                    background = Color.Red, // Muda apenas o fundo do botão primário
                    content = Color.White
                )
            )
        )
        DSButton(
            config = DSButtonData(
                text = "Alerta",
                type = ButtonType.Primary,
                onClick = { println("Botão Primário Customizado clicado!") }
            ),
            style = customTheme
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.shimmer()
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudyTestsTheme {
        Greeting("Android")
    }
}