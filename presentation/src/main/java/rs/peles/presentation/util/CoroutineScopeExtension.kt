package rs.peles.presentation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <R> CoroutineScope.executeAsyncTask(
    onPreExecute: () -> Unit,
    doInBackground: () -> R,
    onPostExecute: (R) -> Unit
) = launch {
    onPreExecute() // on main thread
    val result = withContext(Dispatchers.IO) {
        doInBackground() // on IO thread
    }
    onPostExecute(result) // on main thread
}