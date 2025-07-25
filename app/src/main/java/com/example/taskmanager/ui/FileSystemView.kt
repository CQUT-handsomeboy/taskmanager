package com.example.taskmanager.ui

import android.util.Log
import androidx.collection.FloatList
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.Adapters
import com.example.taskmanager.R
import com.example.taskmanager.TaskManagerBinder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FileSystemView() {
    val fileSystemUsageState = remember {
        mutableStateListOf<Adapters.DiskPartition>()
    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val diskPartitionColors =
        context.resources.getIntArray(R.array.disk_partition_colors).map { Color(it) }
    val diskHeaderWeights = listOf<Float>(
        166f, 266f, 199f, 199f, 166f, 166f,
    ).map { it / 1000f }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val fileSystemUsage = TaskManagerBinder.getFileSystemUsage()
            fileSystemUsageState.clear()
            fileSystemUsageState.addAll(fileSystemUsage)
            delay(500)
        }
    }

    Column {
        DiskPartitionsTableHeader(diskHeaderWeights)
        LazyColumn {
            fileSystemUsageState.mapIndexed { index, diskPartition ->
                item {
                    DiskPartitionItem(
                        diskPartition,
                        diskPartitionColors[index % diskPartitionColors.size],
                        diskHeaderWeights
                    )
                }
            }
        }
    }
}

@Composable
fun DiskPartitionsTableHeader(diskHeaderWeights: List<Float>) {
    HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color(0xFFE8E9EB))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        val context = LocalContext.current

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(24.dp)
        ) {
            Text(
                text = context.getString(R.string.used),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(diskHeaderWeights[0]),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            VerticalDivider(
                modifier = Modifier.height(18.dp), color = Color(0x0D000000)
            )
            Text(
                text = context.getString(R.string.catalogue),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(diskHeaderWeights[1]),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            VerticalDivider(
                modifier = Modifier.height(18.dp), color = Color(0x0D000000)
            )
            Text(
                text = context.getString(R.string.device),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(diskHeaderWeights[2]),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            VerticalDivider(
                modifier = Modifier.height(18.dp), color = Color(0x0D000000)
            )
            Text(
                text = context.getString(R.string.type),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(diskHeaderWeights[3]),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            VerticalDivider(
                modifier = Modifier.height(18.dp), color = Color(0x0D000000)
            )
            Text(
                text = context.getString(R.string.total_storage),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(diskHeaderWeights[4]),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            VerticalDivider(
                modifier = Modifier.height(18.dp), color = Color(0x0D000000)
            )
            Text(
                text = context.getString(R.string.available_storage),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(diskHeaderWeights[5]),
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
    HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Color(0xFFE8E9EB))
}

@Composable
fun DiskPartitionItem(
    diskPartition: Adapters.DiskPartition, color: Color, diskHeaderWeights: List<Float>
) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clickable(onClick = {}),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val m = listOf<String>(
            "${diskPartition.used}B",
            diskPartition.catalogue,
            diskPartition.device,
            diskPartition.type,
            diskPartition.storage,
            diskPartition.available
        )
        diskHeaderWeights.forEachIndexed { index, taskItemWeight ->
            Row(
                modifier = Modifier
                    .weight(taskItemWeight)
                    .padding(start = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = m[index].toString(),
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (index == 0) {
                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(22.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xffEBEBEB))
                    ) {
                        Box(
                            modifier = Modifier
                                .width(180.dp * (diskPartition.percent / 100f))
                                .fillMaxHeight()
                                .background(color)
                        )
                        Text(
                            "${diskPartition.percent / 100f}%",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
