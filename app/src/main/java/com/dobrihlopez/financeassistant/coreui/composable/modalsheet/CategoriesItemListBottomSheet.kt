package com.dobrihlopez.financeassistant.coreui.composable.modalsheet

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.dobrihlopez.financeassistant.core.model.category.Category
import com.dobrihlopez.financeassistant.coreui.composable.item.CategoriesItem
import com.dobrihlopez.financeassistant.coreui.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesItemListBottomSheet(
    items: List<Category>,
    initiallySelectedCategory: Category? = null,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onItemSelected: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = MaterialTheme.spacing

    ModalBottomSheet(
        sheetState = sheetState,
        modifier = modifier,
        onDismissRequest = onDismissRequest,
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(spacing.small)
                    .clip(MaterialTheme.shapes.medium),
        ) {
            items(items = items, key = { it.id }) { category ->
                CategoriesItem(category = category, onClick = {
                    onItemSelected(category)
                }, isSelected = category == initiallySelectedCategory)
            }
        }
    }
}
