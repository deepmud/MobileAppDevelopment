package uk.ac.tees.mad.E4621366.mobileappdevelopment.component


import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.Cell
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.HeaderCell
import uk.ac.tees.mad.E4621366.mobileappdevelopment.common.IndicatorRow

private val ColIndicator = 140.dp
private val ColCountry   = 80.dp
private val ColYear      = 80.dp
private val ColValue     = 120.dp




@Composable
fun IndicatorHeader(hScroll: ScrollState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(hScroll)
            .background(Color(0xFFccccccc))
            .padding(vertical = 8.dp)
    ) {


        HeaderCell("Id", ColIndicator)
        HeaderCell("Country", ColCountry)
        HeaderCell("Year", ColYear)
        HeaderCell("Value", ColIndicator)
        HeaderCell("Age", ColIndicator)
        HeaderCell("Sex", ColCountry)
        HeaderCell("Type", ColIndicator)

    }
}




@Composable
fun IndicatorTableRow(row: IndicatorRow, hScroll: ScrollState) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(hScroll)
            .padding(vertical = 6.dp)
    ) {

        Cell(row.Id,ColIndicator)
        Cell(row.SpatialDim, ColCountry)
        Cell(row.TimeDim?.toString(),ColYear)
        Cell(row.Value ?: row.NumericValue?.toString(), ColIndicator)
        Cell(row.Dim1,ColIndicator)
        Cell(row.Dim3,ColCountry)
        Cell(row.Dim2,ColIndicator)


    }

    Divider()
}
