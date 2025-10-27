import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.xaori.schedule.R
import ru.xaori.schedule.presentation.state.SnackbarType
import ru.xaori.schedule.presentation.state.SnackbarUiState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CustomSnackbarHostWithState(
    uiState: SnackbarUiState,
    modifier: Modifier = Modifier,
    onAction: () -> Unit = {},
    onHide: () -> Unit = {}
) {
    AnimatedVisibility(
        visible = uiState.isVisible, enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec()
        ) + fadeIn(
            animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec()
        ), exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec()
        ) + fadeOut(
            animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec()
        ), modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom).asPaddingValues()
            )
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    if (dragAmount > 5) {
                        onHide()
                    }
                }
            }) {
        val (icon, color) = when (uiState.type) {
            SnackbarType.SUCCESS -> R.drawable.ic_ok to MaterialTheme.colorScheme.primary
            SnackbarType.ERROR -> R.drawable.ic_error to MaterialTheme.colorScheme.error
        }

        Box(
            modifier = Modifier
                .shadow(
                    2.dp,
                    shape = RoundedCornerShape(12.dp),
                )
                .background(
                    color = MaterialTheme.colorScheme.surfaceBright,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(icon),
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp
                    )
                }
                uiState.actionLabel?.let { label ->
                    TextButton(onClick = onAction) {
                        Text(label, color = Color.White)
                    }
                }
            }
        }
    }
}
