package rs.peles.presentation.util

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object FileCreator {

    /**
     * Create image file for file provider
     * @param context - context
     * @return - file
     * @throws IOException - ex
     */
    @Throws(IOException::class)
    fun createImageFile(context: Context): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName: String = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) // save private for app, does no require any permissions
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        Log.e("FILE_RPOVIDER", "Putanja ka fajlu: " + image.absolutePath)
        return image
    }


}