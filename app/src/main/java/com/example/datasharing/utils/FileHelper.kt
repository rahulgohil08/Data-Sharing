package com.example.datasharing.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


fun getFile(context: Context, documentUri: Uri, mimeType: String = ".pdf"): File {
    val inputStream = context.contentResolver?.openInputStream(documentUri)
    var file: File

    inputStream.use { input ->
        file = File(context.cacheDir, System.currentTimeMillis().toString() + mimeType)
        inputStream?.copyTo(file.outputStream())
    }
    return file
}


fun getIntentForFile(filePath: String, context: Context): Intent {
//    https://stackoverflow.com/a/62045721
    val intent = Intent()

    val uri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        File(filePath)
    )
    intent.action = Intent.ACTION_VIEW
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.setDataAndType(uri, "application/pdf")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    return intent
}