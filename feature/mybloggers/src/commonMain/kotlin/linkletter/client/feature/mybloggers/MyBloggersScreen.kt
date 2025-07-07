package linkletter.client.feature.mybloggers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MyBloggersScreen(
    modifier: Modifier = Modifier,
    viewModel: MyBloggersViewModel = koinViewModel(),
) {
    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(8.dp),
    ) {
        Text(text = "MyBloggers")
    }
}
